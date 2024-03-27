package com.example.myapplication.Laba6.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Laba6.data.MyRepository;
import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.domain.FunctionPoint;
import com.example.myapplication.Laba6.domain.MyUseCase;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MyFragmentViewModel extends ViewModel {
    private final MutableLiveData<Integer> makeToastLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayTabulatedFunction> arrayTabulatedFunctionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> openDialogFragmentLiveData = new MutableLiveData<>();
    private final MyRepository myRepository = new MyRepository();
    private final MyUseCase useCase = new MyUseCase(myRepository);


    public LiveData<Integer> getMakeToastLiveData() {
        return makeToastLiveData;
    }

    public MutableLiveData<Boolean> getOpenDialogFragmentLiveData() {
        return openDialogFragmentLiveData;
    }

    public MutableLiveData<ArrayTabulatedFunction> getArrayTabulatedFunctionMutableLiveData() {
        return arrayTabulatedFunctionMutableLiveData;
    }

    public void setArrayTabulatedFunction(ArrayTabulatedFunction arrayTabulatedFunction) {
        arrayTabulatedFunctionMutableLiveData.setValue(arrayTabulatedFunction);
    }

    public void createToast() {
        makeToastLiveData.setValue(R.string.saved);
    }

    public void buttonDatabasePressed(ArrayList<FunctionPoint> list) {
        useCase.getButtonDatabasePressed(list);
        createToast();
    }

    public void addButtonPressed(boolean bool) {
        openDialogFragmentLiveData.setValue(bool);
    }

    public void positiveButtonPressed(double x, double y) {
        FunctionPoint functionPoint = new FunctionPoint(x, y);
        ArrayTabulatedFunction array = arrayTabulatedFunctionMutableLiveData.getValue();
        assert array != null;
        FunctionPoint[] functionPoints = new FunctionPoint[array.getPointsCount() + 1];
        for (int i = 0; i <= array.getPointsCount() - 1; i++) {
            functionPoints[i] = array.getPoint(i);
        }
        functionPoints[functionPoints.length - 1] = functionPoint;
        array = new ArrayTabulatedFunction(functionPoints);
        arrayTabulatedFunctionMutableLiveData.setValue(array);
    }
}






