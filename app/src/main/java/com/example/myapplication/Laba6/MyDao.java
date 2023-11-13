package com.example.myapplication.Laba6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {
    @Query("SELECT * FROM your_table_name")
    List<MyEntity> getAll();

    @Query("SELECT * FROM your_table_name WHERE x = :id")
    MyEntity getById(double id);

    @Query("DELETE from your_table_name WHERE x = :id")
    int deleteById(double id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyEntity entity);

    @Update
    void update(MyEntity myEntity);

    @Delete
    void delete(MyEntity myEntity);
}
