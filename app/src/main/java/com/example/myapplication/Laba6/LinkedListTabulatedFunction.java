package com.example.myapplication.Laba6;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements Serializable {
    FunctionNode head = new FunctionNode();
    int lastIndex = -1;
    double leftX;
    double rightX;

    double[] values;

    public LinkedListTabulatedFunction(FunctionPoint[] array) {
        int pointsCount = array.length;
        FunctionNode buffer = head;
        for (int i = 0; i < pointsCount; i++) {
            buffer.item.setX(array[i].getX());
            buffer.item.setY(array[i].getY());
            buffer.next = new FunctionNode();
            buffer = buffer.next;
            if (i != pointsCount - 1 && array[i].getX() > array[i + 1].getX()) {
                throw new IllegalArgumentException();
            }
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException();
        }
    }


    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX && pointsCount < 2) {
            throw new IllegalArgumentException();
        }
        this.leftX = leftX;
        this.rightX = rightX;
        FunctionNode allLength = head;
        int i = 0;
        double del = (rightX - leftX) / pointsCount;
        while (i < pointsCount) {
            allLength.item = new FunctionPoint();
            allLength.next = new FunctionNode(allLength, new FunctionPoint(), null, i);
            if (i == 0) {
                allLength.item.setX(leftX);
            } else {
                allLength.item.setX(allLength.prev.item.getX() + del);
            }
            i++;
            allLength = allLength.next;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX && values.length < 2) {
            throw new IllegalArgumentException();
        }
        this.leftX = leftX;
        this.rightX = rightX;
        this.values = values;
        head = new FunctionNode();
        FunctionNode allLength = head;
        int i = 0;
        int pointsCount = values.length;
        double del = (rightX - leftX) / pointsCount;
        while (i < pointsCount) {
            allLength.item = new FunctionPoint();
            if (i == 0) {
                allLength.item.setX(leftX);
            } else {
                allLength.item.setX(del + allLength.prev.item.getX());
            }
            allLength.item.setY(values[i]);
            allLength.next = new FunctionNode();
            allLength.next.prev = allLength;
            allLength = allLength.next;
            i++;
        }
    }


  /*  public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {
        @Override
        public TabulatedFunction<FunctionPoint> createTabulatedFunction(FunctionPoint[] array) {
            return new LinkedListTabulatedFunction(array);
        }

        @Override
        public TabulatedFunction<FunctionPoint> createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction<FunctionPoint> createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }
    }
*/


    public double getLeftDomainBorder() {
        return leftX;
    }


    public double getRightDomainBorder() {
        return rightX;
    }


    public double[] returnNumb(double x) {
        FunctionNode allLength = head;
        while (allLength.next != null) {
            if (x < allLength.item.getX() && x > allLength.prev.item.getX()) {
                return new double[]{allLength.item.getX(), allLength.prev.item.getX(), allLength.item.getY(), allLength.prev.item.getY()};
            }
            allLength = allLength.next;
        }
        return null;
    }

    public double getFunctionValue(double x) {
        if (x >= leftX && x <= rightX) {
            double[] array = returnNumb(x);
            double x1 = array[1];
            double y2 = array[2];
            double y1 = array[3];
            double x2 = array[0];
            return (((x - x1) * (y2 - y1) / (x2 - x1))) + y1;
        } else {
            return Double.NaN;
        }
    }

    public int getPointsCount() {
        FunctionNode allLength = head;
        int i = 0;
        while (allLength.next != null) {
            i++;
            allLength = allLength.next;
        }
        return i;
    }

    public FunctionPoint getPoint(int index) {
        FunctionNode allLength = head;
        int i = 0;
      /*  if (index > getPointsCount() - 1  index < 0) {
            throw new FunctionPointIndexOutOfBoundsException();
        }*/

        while (i < index) {
            i++;
            allLength = allLength.next;
        }
        return allLength.item;
    }


  /*  public void setPoint(int index, FunctionPoint point) {
     /*   if (index > getPointsCount() - 1  index < 0  point.getX() < leftX  point.getX() > rightX) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (index == 0 && point.getX() <= getPoint(1).getX() && point.getX() >= leftX
        index == getPointsCount() - 1 && point.getX() >= getPoint(index - 1).getX() && point.getX() <= rightX
        index >= 1 && index <= getPointsCount() - 2 && point.getX() >= getPoint(index - 1).getX() && point.getX() <= getPoint(index + 1).getX()) {
            this.getPoint(index).setX(point.getX());
        }
    } */


    public double getPointX(int index) {
        return getPoint(index).getX();
    }

 /*   public void setPointX(int index, double x) {
        if (index > getPointsCount() - 1  index < 0  x > rightX  x < leftX) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (index == 0 && x <= getPoint(1).getX() && x >= leftX  index == getPointsCount() - 1 && x >= getPoint(index - 1).getX() && x <= rightX
        index >= 1 && index <= getPointsCount() - 2 && x >= getPoint(index - 1).getX() && x <= getPoint(index + 1).getX()) {
            this.getPoint(index).setX(x);
        } else {
            throw new InappropriateFunctionPointException();
        }
    }*/


    public double getPointY(int index) {
        return getPoint(index).getY();
    }

    public void addPoint(FunctionPoint point) {
        int i = 0;
        FunctionNode allLength = head;
        while (allLength.item.getX() <= point.getX()) {
            i++;
            allLength = allLength.next;
        }
        addNodeByIndex(i, point);
    }

    /*******************************************************************************************/
    private static class FunctionNode implements Serializable {
        FunctionPoint item;
        FunctionNode next;
        FunctionNode prev;

        int index;

        public FunctionNode() {
            this.index = 0;
            this.item = new FunctionPoint();
            this.next = null;
            this.prev = null;
        }

        ;

        public FunctionNode(FunctionNode prev, FunctionPoint item, FunctionNode next, int index) {
            this.index = index;
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    /*******************************************************************************************/


    public FunctionNode getNodeByIndex(int index) {
        FunctionNode currentElement = head.next;
        while (index != currentElement.index) {
            currentElement = currentElement.next;
        }
        return currentElement;
    }

    public FunctionNode addNodeToTail(FunctionPoint functionPoint) {
        lastIndex = lastIndex + 1;
        FunctionNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.item = functionPoint;
        tail.index = lastIndex;
        return tail;
    }

    public FunctionNode addNodeByIndex(int index, FunctionPoint functionPoint) {
        FunctionNode prevIndex;
        if (index == 0) {
            prevIndex = head;
        } else {
            prevIndex = getNodeByIndex(index - 1);
        }
        FunctionNode newIndex = getNodeByIndex(index);
        FunctionNode newObject = new FunctionNode(prevIndex, functionPoint, newIndex, index);
        newIndex.prev = newObject;
        prevIndex.next = newObject;
        FunctionNode ObjeckForNext = newIndex;
        while (ObjeckForNext.next != null) {
            ObjeckForNext.index = ObjeckForNext.index + 1;
            ObjeckForNext = ObjeckForNext.next;
        }
        return newObject;
    }

    public void deletePoint(int index) {
        FunctionNode prevIndex;
        if (index == 0) {
            prevIndex = head;
        } else {
            prevIndex = getNodeByIndex(index - 1);
        }

        FunctionNode deletedNoted = getNodeByIndex(index);
        if (deletedNoted.next == null) {
            prevIndex.next = null;
        } else {
            FunctionNode nextIndex = getNodeByIndex(index + 1);
            prevIndex.next = nextIndex;
            nextIndex.prev = prevIndex;
            deletedNoted.next = null;
            FunctionNode objectForIndex = prevIndex;
            while (objectForIndex.next != null) {
                objectForIndex.index = objectForIndex.index - 1;
                objectForIndex = objectForIndex.next;
            }
        }
        deletedNoted.prev = null;
    }


    @Override
    public String toString() {
        String str = "{";
        FunctionNode tail = head;
        while (tail.next != null) {
            if (tail.next.next != null) {
                str = str + tail.item.toString() + ", ";
                tail = tail.next;
            } else {
                str = str + tail.item.toString();
                tail = tail.next;
            }
        }
        tail.item = new FunctionPoint();
        return str + "}";
    }

 /*   @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass() && o.getClass() != ArrayTabulatedFunction.class) {
            return false;
        }
        TabulatedFunction c = (TabulatedFunction) o;
        FunctionNode tail = head;
        int i = 0;
        if (Double.compare(leftX, c.getLeftDomainBorder()) == 0 && Double.compare(rightX, c.getRightDomainBorder()) == 0) {
            while (tail.next != null) {
                if (!tail.item.equals(c.getPoint(i))) {
                    return false;
                }
                tail = tail.next;
                i++;
            }
            return true;
        } else {
            return false;
        }
    }*/

    @Override
    public int hashCode() {
        int result = 1;
        FunctionNode tail = head;
        while (tail.next != null) {
            if (tail.item.hashCode() != 0) {
                result = result + tail.item.hashCode();
            }
            tail = tail.next;
        }
        if (leftX != 0) {
            result = result + (int) Double.doubleToLongBits(leftX);
        } else {
            result = result + 55555;
        }
        if (rightX != 0) {
            result = result + (int) Double.doubleToLongBits(rightX);
        } else {
            result = result + 55555;
        }
        return result;
    }


    @Override
    public Object clone() {
        FunctionNode tail = head;
        int i = 0;
        FunctionPoint[] array = new FunctionPoint[getPointsCount()];
        if (leftX == 0 && rightX == 0) {
            while (tail.next != null) {
                array[i] = tail.item;
                tail = tail.next;
                i++;
            }
            return new LinkedListTabulatedFunction(array);
        }
        if (values == null) {
            return new LinkedListTabulatedFunction(leftX, rightX, getPointsCount());
        } else {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }
    }
}
