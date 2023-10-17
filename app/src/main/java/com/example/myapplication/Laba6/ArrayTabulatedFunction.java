package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.CLOSE;
import static com.example.myapplication.Constants.COMMA;
import static com.example.myapplication.Constants.OPEN;

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


    public void addPoint(FunctionPoint point) {
        FunctionPoint[] array2 = new FunctionPoint[arrayFPX.length + 1];
        System.arraycopy(arrayFPX, 0, array2, 0, arrayFPX.length);
        boolean winner = false;
        boolean win = false;
        int index = 0;
        for (int i = 0; i < arrayFPX.length; i++) {
            if (arrayFPX[i].getX() > point.getX() && point.getX() > leftX) {
                index = i;
                win = true;
                break;
            }
            if (arrayFPX.length - 1 == i && point.getX() < rightX && point.getX() > leftX) {
                winner = true;
                break;
            }
        }
        if (win) {
            System.arraycopy(array2, index, array2, index + 1, array2.length - 1 - index);
            array2[index] = point;
            arrayFPX = array2;
        }
        if (winner) {
            array2[array2.length - 1] = point;
            arrayFPX = array2;
        }
    }

    public ArrayTabulatedFunction createTabulatedFunction(FunctionPoint[] array) {
        return new ArrayTabulatedFunction(array);
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
