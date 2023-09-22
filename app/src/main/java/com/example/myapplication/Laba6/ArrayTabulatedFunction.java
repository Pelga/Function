package com.example.myapplication.Laba6;

import java.io.Serializable;

public class ArrayTabulatedFunction implements Serializable {

    private FunctionPoint[] arrayFPX;
    double leftX;
    double rightX;

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

    public FunctionPoint getPoint(int index) {
        //  if (index > arrayFPX.length - 1 || index < 0) {
        //    throw new FunctionPointIndexOutOfBoundsException();
        //}
        return arrayFPX[index];
    }

    public int getPointsCount() {
        return arrayFPX.length;
    }

    public void deletePoint(int index) {
        if (index == arrayFPX.length - 1) {
            arrayFPX[index] = null;
        } else {
            System.arraycopy(arrayFPX, index + 1, arrayFPX, index, arrayFPX.length - 1 - index);
            arrayFPX[arrayFPX.length - 1] = null;
        }
        if (arrayFPX.length < 3) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        String str = "{ ";
        int pointsCount = arrayFPX.length;
        for (int i = 0; i < pointsCount; i++) {
            if (i == pointsCount - 1) {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString();
                }
            } else {
                if (arrayFPX[i] != null) {
                    str = str + arrayFPX[i].toString() + ", ";
                }
            }
        }
        return str + " }";
    }
}
