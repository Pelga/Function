package com.example.myapplication.Laba6;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "your_table_name")
public class MyEntity {
    @PrimaryKey()
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public MyEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyEntity() {
    }
}