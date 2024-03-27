package com.example.lapa3;

import javafx.scene.chart.XYChart;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.floor;

public class Logic {
    private static final int numberOfPoints = 500;
    private static final int length = 600;
    private static final int offset = 100;

    private static final double priorProbabilitiesFirst = 0.6;
    private static final double priorProbabilitiesSecond = 1-priorProbabilitiesFirst;
    double[] xPointsOfFirstValue = new double[numberOfPoints];
    double[] xPointsOfSecondValue = new double[numberOfPoints];
    double[] yPointsOfFirstValue = new double[numberOfPoints];
    double[] yPointsOfSecondValue = new double[numberOfPoints];

    double expectedValueFirst=0;
    double expectedValueSecond=0;

    double standardDeviationFirst=0;
    double standardDeviationSecond=0;

    Random random = new Random();

    public void createPoints(XYChart.Series<Number,Number> seriesFirstValue, XYChart.Series<Number,Number> seriesSecondValue){

        for (int i = 0; i < numberOfPoints; i++) {
            xPointsOfFirstValue[i] = random.nextDouble(length-offset)+offset;
            xPointsOfSecondValue[i] = random.nextDouble(length-offset);
            expectedValueFirst += xPointsOfFirstValue[i];
            expectedValueSecond += xPointsOfSecondValue[i];
        }
        expectedValueFirst /=numberOfPoints;
        expectedValueSecond /=numberOfPoints;

        Arrays.sort(xPointsOfFirstValue);
        Arrays.sort(xPointsOfSecondValue);

        for (int i = 0; i < numberOfPoints; i++) {
            standardDeviationFirst += Math.pow((xPointsOfFirstValue[i] - expectedValueFirst),2);
            standardDeviationSecond += Math.pow((xPointsOfSecondValue[i] - expectedValueSecond),2);
        }
        standardDeviationFirst = Math.sqrt(standardDeviationFirst/numberOfPoints);
        standardDeviationSecond = Math.sqrt(standardDeviationSecond/numberOfPoints);

        for (int i = 0; i < numberOfPoints; i++) {
            double factor = 1/(standardDeviationFirst*Math.sqrt(2*Math.PI));
            double power = -0.5*Math.pow(((xPointsOfFirstValue[i]-expectedValueFirst)/standardDeviationFirst),2);
            yPointsOfFirstValue[i]=factor*Math.exp(power)*priorProbabilitiesFirst;

            factor = 1/(standardDeviationSecond*Math.sqrt(2*Math.PI));
            power = -0.5*Math.pow(((xPointsOfSecondValue[i]-expectedValueSecond)/standardDeviationSecond),2);
            yPointsOfSecondValue[i]=factor*Math.exp(power)*priorProbabilitiesSecond;
        }

        for (int i = 0; i < numberOfPoints; i++) {
            seriesFirstValue.getData().add(new XYChart.Data<>(xPointsOfFirstValue[i], yPointsOfFirstValue[i]));
            seriesSecondValue.getData().add(new XYChart.Data<>(xPointsOfSecondValue[i], yPointsOfSecondValue[i]));
        }
    }

    public int[] searchCenter(XYChart.Series<Number,Number> series) {
        double value = 0;
        int coincidence = 0;
        int[] result = new int[2];

        for (int i = 0; i < numberOfPoints; i++) {
            if (xPointsOfSecondValue[i] >= offset) {
                int j = 0;
                while (floor(xPointsOfFirstValue[j]) <= floor(xPointsOfSecondValue[i])) {
                    if (floor(xPointsOfFirstValue[j]) == floor(xPointsOfSecondValue[i])) {
                        if (floor(yPointsOfFirstValue[j] * 10000) == floor(yPointsOfSecondValue[i] * 10000)) {
                            if (coincidence==7) {
                                break;
                            }
                            coincidence++;
                            result[0] = j;
                            result[1] = i;
                            value = xPointsOfFirstValue[j];
                            System.out.println();
                        }
                    }
                    j++;
                }
            }
        }
        for (double i = 0; i <= 0.002; i+=0.0005) {
            series.getData().add(new XYChart.Data<>(value, i));
        }
        return result;
    }

    public double findFalseAlarm(int value){
        double result=0;
        for (int i = 0; i < value; i++) {
            result+=yPointsOfFirstValue[i];
        }
        return result;
    }

    public double findMissDetection(int value){
        double result=0;
        for (int i = value; i < yPointsOfSecondValue.length; i++) {
            result+=yPointsOfSecondValue[i];
        }
        return result;
    }
}
