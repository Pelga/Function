package com.example.myapplication.Laba6;

import com.google.gson.annotations.SerializedName;

public class MyDateNumbers {
    @SerializedName("numbers")
    private FunctionPoint[] numbers;

    public MyDateNumbers(FunctionPoint[] numbers) {
        this.numbers = numbers;
    }

    public FunctionPoint[] getNumbers() {
        return numbers;
    }

    public void setNumbers(FunctionPoint[] numbers) {
        this.numbers = numbers;
    }
}
