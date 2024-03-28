package com.example.myapplication.Laba6.domain;

import static com.example.myapplication.Laba6.domain.Constants.CLOSE;
import static com.example.myapplication.Laba6.domain.Constants.COMMA;
import static com.example.myapplication.Laba6.domain.Constants.OPEN;

import java.io.Serializable;

public class ArrayTabulatedFunction implements Serializable {

    private FunctionPoint[] arrayFPX;
    private double leftX;
    private double rightX;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX && pointsCount < 2) {
            throw new IllegalArgumentException();
        }
        this.leftX = leftX;
        this.rightX = rightX;
        this.arrayFPX = new FunctionPoint[pointsCount];
        double del = (rightX - leftX) / pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            arrayFPX[i] = new FunctionPoint();
            if (i == 0) {
                arrayFPX[i].setX(leftX);
            } else {
                arrayFPX[i].setX(del + arrayFPX[i - 1].getX());
            }
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] arrayFPX) {
        this.arrayFPX = arrayFPX;
    }

    public FunctionPoint getPoint(int index) {
        return arrayFPX[index];
    }

    public int getPointsCount() {
        return arrayFPX.length;
    }

    @Override
    public String toString() {
        String str = OPEN;
        int pointsCount = arrayFPX.length;
        for (int i = 0; i < pointsCount; i++) {
            if (i == pointsCount - 1) {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString();
                }
            } else {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString() + COMMA;
                }
            }
        }
        return str + CLOSE;
    }
}
