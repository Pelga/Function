package com.example.myapplication.Laba6;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private static App instance;

    private MyDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, MyDatabase.class, "database").allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public MyDatabase getDatabase() {
        return database;
    }
}

