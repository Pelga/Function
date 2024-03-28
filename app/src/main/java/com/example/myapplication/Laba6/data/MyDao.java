package com.example.myapplication.Laba6.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Laba6.domain.entity.MyEntity;

import java.util.List;

//bd
@Dao
public interface MyDao {
    @Query("SELECT * FROM your_table_name")
    List<MyEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyEntity entity);

    @Update
    void update(MyEntity myEntity);

    @Delete
    void delete(MyEntity myEntity);
}
