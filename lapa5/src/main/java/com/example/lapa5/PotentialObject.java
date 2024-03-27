package com.example.lapa5;

import java.util.ArrayList;
import java.util.List;

public class PotentialObject {
    private int classNumber;
    private double x;
    private double y;

    public PotentialObject(int classNumber, double x, double y) {
        this.classNumber = classNumber;
        this.x = x;
        this.y = y;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
