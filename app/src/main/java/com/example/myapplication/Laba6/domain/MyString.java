package com.example.myapplication.Laba6.domain;

import android.widget.EditText;

public abstract class MyString {
    public static String toStr(EditText editText) {
        return editText.getText().toString();
    }
}
