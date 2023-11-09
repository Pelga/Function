package com.example.myapplication.Laba6;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyEntity.class}, version = 1)

public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}

