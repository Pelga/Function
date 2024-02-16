package com.example.myapplication.Laba6;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.R;

import java.util.ArrayList;

//ui
public class MyFragmentViewModel extends ViewModel {
    private final MutableLiveData<Integer> makeToastLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayTabulatedFunction> arrayTabulatedFunctionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> openDialogFragmentLiveData = new MutableLiveData<>();
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
        MyDatabase database = App.getInstance().getDatabase();
        MyDao dao = database.myDao();
        ArrayList<FunctionPoint> tabList = list;
        for (int i = 0; i < tabList.size(); i++) {
            MyEntity entity = new MyEntity(tabList.get(i).getX(), tabList.get(i).getY());
            dao.insert(entity);
        }
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




