package com.example.myapplication.Laba6.domain;

import com.example.myapplication.Laba6.data.MyRepository;
import com.example.myapplication.Laba6.data.retrofit.MyRepositoryCallback;

import java.util.ArrayList;

public class MyUseCase {
    private final MyRepository myRepository;

    public MyUseCase(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public void getArrayTabulatedFunction(MyUseCaseCallback myUseCaseCallback) {
        //1)при вызове чужого класса результат получаем в callback в аргументах callback
        //2)тк мы получаем результат в callback, мы не можем использовать return, тк callback работает ассинхронно
        //2.1)все callback работаю ассинхронно и ни в каком нельзя использовать ретерн
        //3)делаем метод void и передаем результат с помощью нового callback

        myRepository.createTabulatedFunctionByRequest(new MyRepositoryCallback() {
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

    public interface MyUseCaseCallback {
        void onSuccess(ArrayTabulatedFunction arrayTabulatedFunction);

        void onFailure(Throwable throwable);
    }

    public void getButtonDatabasePressed(ArrayList<FunctionPoint> list){
        myRepository.buttonDatabasePressed(list);
    }
}



