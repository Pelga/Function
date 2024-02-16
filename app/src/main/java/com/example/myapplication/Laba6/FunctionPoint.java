package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.CLOSE_ANOTHER;
import static com.example.myapplication.Constants.OPEN_ANOTHER;
import static com.example.myapplication.Constants.SEMICOLON;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    FunctionPoint(FunctionPoint point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public FunctionPoint() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public String toString() {
        String str = OPEN_ANOTHER + getX() + SEMICOLON + getY() + CLOSE_ANOTHER;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        FunctionPoint c = (FunctionPoint) o;
        return Double.compare(x, c.x) == 0
                && Double.compare(y, c.y) == 0;
    }

    @Override
    public int hashCode() {
        if (x != 0 && y != 0) {
            long bits = Double.doubleToLongBits(x);
            long bitsY = Double.doubleToLongBits(y);
            return (int) (bits ^ (bits >>> 32)) + (int) (bitsY ^ (bitsY >>> 32));
        } else {
            return 6666666;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new FunctionPoint(x, y);
    }
}

