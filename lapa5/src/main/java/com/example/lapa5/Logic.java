package com.example.lapa5;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Random;

public class Logic {
    public static final int pointCount = 100;
    public ArrayList<PotentialObject> points = new ArrayList<>();
    public static int coefficientCount = 4;
    public static double[] basicCoefficient = {1,4,4,16};
    public static double[] previousCoefficient = {0,0,0,0};
    public static double[] currentCoefficient = {0,0,0,0};

    Random random = new Random();
    public ArrayList<PotentialObject> objects = new ArrayList<>();
    private void initializeObjects(){
        objects.add(new PotentialObject(1,-1,0));
        objects.add(new PotentialObject(1,1,1));
        objects.add(new PotentialObject(2,2,0));
        objects.add(new PotentialObject(2,1,-2));
    }

    public double[] calculateCoefficients() {
        initializeObjects();
        boolean isAdjustmentNeed = true;
        int i = 0;
        int sign =  1;
        while (isAdjustmentNeed) {
            previousCoefficient = currentCoefficient.clone();
            currentCoefficient[0] = basicCoefficient[0];
            currentCoefficient[1] = basicCoefficient[1] * objects.get(i).getX();
            currentCoefficient[2] = basicCoefficient[2] * objects.get(i).getY();
            currentCoefficient[3] = basicCoefficient[3] * objects.get(i).getY() * objects.get(i).getY();
            for (int j = 0; j < coefficientCount; j++) {
                currentCoefficient[j]=previousCoefficient[j]+currentCoefficient[j]*sign;
            }
            double potential = currentCoefficient[0]+currentCoefficient[1] * objects.get(i+1).getX()+
                    currentCoefficient[2] * objects.get(i+1).getY() + currentCoefficient[3] *
                    objects.get(i+1).getX()*objects.get(i+1).getY();
            if (potential<=0 && objects.get(i+1).getClassNumber()==1){
                sign=1;
            } else {
                sign=-1;
            }
            if (objects.get(i+1).getClassNumber()==1){
                isAdjustmentNeed = potential<0;
            } else {
                isAdjustmentNeed = potential>0;
            }
            i++;
            if (i==objects.size()-1){
                i=0;
            }
        }
        return currentCoefficient;
    }

    private void generatePoints(double[] coefficients){
        for (int i = 0; i < pointCount; i++) {
            points.add(new PotentialObject(0, random.nextDouble(10)-5, random.nextDouble(10)-5));
        }
        for (int i = 0; i < pointCount; i++) {
            double potential = coefficients[0] + coefficients[1]*points.get(i).getX() + coefficients[2]*points.get(i).getY()+
                    coefficients[3]*points.get(i).getX()*points.get(i).getY();
            if (potential>0){
                points.get(i).setClassNumber(1);
            } else {
                points.get(i).setClassNumber(2);
            }
        }
    }
    public void distributeClasses(double[] coefficients, XYChart.Series<Number,Number> seriesFirstClassValue, XYChart.Series<Number,Number> seriesSecondClassValue){
        generatePoints(coefficients);
        for (int i = 0; i < pointCount; i++) {
            if (points.get(i).getClassNumber()==1){
                seriesFirstClassValue.getData().add(new XYChart.Data<>(points.get(i).getX(), points.get(i).getY()));
            } else {
                seriesSecondClassValue.getData().add(new XYChart.Data<>(points.get(i).getX(), points.get(i).getY()));
            }
        }
    }
}
