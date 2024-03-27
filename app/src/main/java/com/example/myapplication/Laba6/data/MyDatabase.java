package com.example.myapplication.Laba6.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Laba6.domain.entity.MyEntity;

@Database(entities = {MyEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}

