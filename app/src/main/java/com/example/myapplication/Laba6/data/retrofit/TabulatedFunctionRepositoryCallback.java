package com.example.myapplication.Laba6.data.retrofit;

import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;

public interface TabulatedFunctionRepositoryCallback {

    void onSuccess(ArrayTabulatedFunction arrayTabulatedFunction);

    void onFailure(Throwable throwable);
}