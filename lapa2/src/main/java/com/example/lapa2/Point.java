package com.example.lapa2;

public class Point {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this){
            return true;
        }
        if (this.getClass()!=obj.getClass()){
            return false;
        }
        if (obj==null){
            return false;
        }
        Point point = (Point) obj;
        if (this.getX()==point.getX() && this.getY()==point.getY())
            return true;
        else
            return false;
    }
}
