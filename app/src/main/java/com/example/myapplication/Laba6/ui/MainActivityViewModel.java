package com.example.myapplication.Laba6.ui;

import static com.example.myapplication.Laba6.domain.Constants.ARRAY;
import static com.example.myapplication.Laba6.domain.Constants.NULL;
import static com.example.myapplication.Laba6.domain.MyString.toStr;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Laba6.data.TabulatedFunctionRepository;
import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.domain.TabulatedFunctionUseCase;
import com.example.myapplication.R;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<ArrayTabulatedFunction> arrayTabulatedFunctionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> makeErrorToastLiveData = new MutableLiveData<>();
    private final MutableLiveData<View> closeKeyboardLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> visibilityLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> closeCardViewLiveData = new MutableLiveData<>();
    private final TabulatedFunctionRepository myRepository = new TabulatedFunctionRepository();
    private final TabulatedFunctionUseCase useCase = new TabulatedFunctionUseCase(myRepository);

    public LiveData<ArrayTabulatedFunction> getArrayTabulatedFunctionLiveData() {
        return arrayTabulatedFunctionLiveData;
    }

    public LiveData<Integer> getMakeErrorToastLiveData() {
        return makeErrorToastLiveData;
    }

    public MutableLiveData<View> getCloseKeyboardLiveData() {
        return closeKeyboardLiveData;
    }

    public MutableLiveData<Integer> getVisibilityLiveData() {
        return visibilityLiveData;
    }

    public MutableLiveData<Boolean> isCloseCardView() {
        return closeCardViewLiveData;
    }

    public void generatedArrayFunction(double x, double y, int p) {
        arrayTabulatedFunctionLiveData.setValue(new ArrayTabulatedFunction(x, y, p));
    }

    public void closeKeyboardAndMakeInvisible(View view, Integer inter) {
        closeKeyboardLiveData.setValue(view);
        visibilityLiveData.setValue(inter);
    }

    public void materialButtonGeneratePressed(View view, Integer inter, boolean closeCardView) {
        closeKeyboardAndMakeInvisible(view, inter);
        closeCardViewLiveData.setValue(closeCardView);
        createTabulatedFunctionByRequest();
    }

    public void materialButtonDownloadPressed(View view, Integer inter, boolean closeCardView) {
        closeKeyboardAndMakeInvisible(view, inter);
        closeCardViewLiveData.setValue(closeCardView);
        createTabulatedFunctionByDatabase();
    }

    public void materialButtonCreatePressed(EditText r, EditText l, EditText p, View view, Integer integer) {
        if (toStr(r).trim().equals(NULL) || toStr(l).equals(NULL) || toStr(p).equals(ARRAY)) {
            createErrorToast();
        } else {
            closeKeyboardAndMakeInvisible(view, integer);
            closeCardViewLiveData.setValue(true);
            double left = Double.parseDouble(toStr(l));
            double right = Double.parseDouble(toStr(r));
            int pointsCount = Integer.parseInt(toStr(p));
            generatedArrayFunction(left, right, pointsCount);
        }
    }

    public void createErrorToast() {
        makeErrorToastLiveData.setValue(R.string.error);
    }

    public void createFailureToast() {
        makeErrorToastLiveData.setValue(R.string.failure);
    }

    public void createTabulatedFunctionByRequest() {
        useCase.getArrayTabulatedFunction(new TabulatedFunctionUseCase.TabulatedFunctionUseCaseCallback() {
            @Override
            public void onSuccess(ArrayTabulatedFunction arrayTabulatedFunction) {
                arrayTabulatedFunctionLiveData.setValue(arrayTabulatedFunction);
            }

            @Override
            public void onFailure(Throwable throwable) {
                createFailureToast();
            }
        });
    }

    public void createTabulatedFunctionByDatabase() {
        arrayTabulatedFunctionLiveData.setValue(useCase.getArrayTabulatedFunctionDataBase());
    }
}

