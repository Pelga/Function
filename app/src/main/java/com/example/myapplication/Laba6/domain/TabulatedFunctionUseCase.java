package com.example.myapplication.Laba6.domain;

import com.example.myapplication.Laba6.data.TabulatedFunctionRepository;
import com.example.myapplication.Laba6.data.retrofit.TabulatedFunctionRepositoryCallback;

import java.util.ArrayList;

public class TabulatedFunctionUseCase {
    private final TabulatedFunctionRepository myRepository;

    public TabulatedFunctionUseCase(TabulatedFunctionRepository myRepository) {
        this.myRepository = myRepository;
    }

    public void getArrayTabulatedFunction(TabulatedFunctionUseCaseCallback myUseCaseCallback) {

        myRepository.createTabulatedFunctionByRequest(new TabulatedFunctionRepositoryCallback() {
            @Override
            public void onSuccess(ArrayTabulatedFunction arrayTabulatedFunction) {
                myUseCaseCallback.onSuccess(arrayTabulatedFunction);
            }

            @Override
            public void onFailure(Throwable throwable) {
                myUseCaseCallback.onFailure(throwable);
            }
        });
    }

    public ArrayTabulatedFunction getArrayTabulatedFunctionDataBase() {
        return myRepository.createTabulatedFunctionByDatabase();
    }

    public interface TabulatedFunctionUseCaseCallback {
        void onSuccess(ArrayTabulatedFunction arrayTabulatedFunction);

        void onFailure(Throwable throwable);
    }

    public void getButtonDatabasePressed(ArrayList<FunctionPoint> list) {
        myRepository.buttonDatabasePressed(list);
    }
}



