package com.example.myapplication.Laba6.data.api;

import com.example.myapplication.Laba6.domain.MyDateNumbers;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPI {
    @GET("/v3/d37e0991-00de-434c-a06e-6d0f200e5a86")
    Call<MyDateNumbers> getMyData();
}
