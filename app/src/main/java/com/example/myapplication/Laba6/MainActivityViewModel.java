package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.ARRAY;
import static com.example.myapplication.Constants.NULL;
import static com.example.myapplication.Laba6.MyString.toStr;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Laba6.data.api.MyAPI;
import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<ArrayTabulatedFunction> arrayTabulatedFunctionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> makeErrorToastLiveData = new MutableLiveData<>();
    private final MutableLiveData<View> closeKeyboardLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> visibilityLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> closeCardViewLiveData = new MutableLiveData<>();//
    private MyDateNumbers data;
    private static final String BASE_URL = "https://run.mocky.io/";

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

    private FunctionPoint[] toFunctionArray(List<MyEntity> entity) {
        FunctionPoint[] array = new FunctionPoint[entity.size()];
        for (int i = 0; i < entity.size(); i++) {
            array[i] = toFunctionPoint(entity.get(i));
        }
        return array;
    }

    private FunctionPoint toFunctionPoint(MyEntity entity) {
        FunctionPoint fp = new FunctionPoint();
        fp.setX(entity.getX());
        fp.setY(entity.getY());
        return fp;
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

    public void createTabulatedFunctionByRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPI apiService = retrofit.create(MyAPI.class);
        Call<MyDateNumbers> call = apiService.getMyData();
        call.enqueue(new Callback<MyDateNumbers>() {
            @Override
            public void onResponse(Call<MyDateNumbers> call, Response<MyDateNumbers> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    assert data != null;
                    arrayTabulatedFunctionLiveData.setValue(new ArrayTabulatedFunction(data.getNumbers()));//сделать чистую
                    // архитектуру с data
                }
            }

            @Override
            public void onFailure(Call<MyDateNumbers> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void createTabulatedFunctionByDatabase() {
        MyDatabase db = App.getInstance().getDatabase();
        MyDao dao = db.myDao();
        List<MyEntity> entity = dao.getAll();
        arrayTabulatedFunctionLiveData.setValue(new ArrayTabulatedFunction(toFunctionArray(entity)));//сделать чистую
        // архитектуру с entity
        for (int i = 0; i < entity.size(); i++) {
            dao.delete(entity.get(i));
        }
    }
}