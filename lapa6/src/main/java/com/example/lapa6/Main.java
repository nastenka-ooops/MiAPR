package com.example.lapa6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static final int windowSize = 700;
    @Override
    public void start(Stage stage){
        Logic logic = new Logic(4);
        logic.initializeDistanceTable();
        ArrayList<Point> unions = logic.findMinimums();

        final NumberAxis xAxisMin = new NumberAxis();
        final NumberAxis yAxisMin = new NumberAxis();

        xAxisMin.setLabel("X");
        yAxisMin.setLabel("Y");

        final LineChart<Number,Number> lineChartMinimum = new LineChart<>(xAxisMin,yAxisMin);

        lineChartMinimum.setTitle("Иерархическое дерево, построенное по критерию минимума");
        lineChartMinimum.setPrefHeight(200);

        XYChart.Series<Number,Number> seriesFirstUnionMinimum = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesSecondUnionMinimum = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesThirdUnionMinimum = new XYChart.Series<>();

        seriesFirstUnionMinimum.getData().add(new XYChart.Data<>(1,0));
        seriesFirstUnionMinimum.getData().add(new XYChart.Data<>(1,unions.get(0).getValue()));
        seriesFirstUnionMinimum.getData().add(new XYChart.Data<>(2,unions.get(0).getValue()));
        seriesFirstUnionMinimum.getData().add(new XYChart.Data<>(2,0));

        lineChartMinimum.getData().add(seriesFirstUnionMinimum);

        seriesSecondUnionMinimum.getData().add(new XYChart.Data<>(3,0));
        seriesSecondUnionMinimum.getData().add(new XYChart.Data<>(3,unions.get(1).getValue()));
        seriesSecondUnionMinimum.getData().add(new XYChart.Data<>(4,unions.get(1).getValue()));
        seriesSecondUnionMinimum.getData().add(new XYChart.Data<>(4,0));

        lineChartMinimum.getData().add(seriesSecondUnionMinimum);

        seriesThirdUnionMinimum.getData().add(new XYChart.Data<>(1.5,unions.get(0).getValue()));
        seriesThirdUnionMinimum.getData().add(new XYChart.Data<>(1.5,1));
        seriesThirdUnionMinimum.getData().add(new XYChart.Data<>(3.5,1));
        seriesThirdUnionMinimum.getData().add(new XYChart.Data<>(3.5,unions.get(1).getValue()));

        lineChartMinimum.getData().add(seriesThirdUnionMinimum);

        seriesFirstUnionMinimum.setName("a=x"+unions.get(0).getX()+",x"+unions.get(0).getY());
        seriesSecondUnionMinimum.setName("b=x"+unions.get(1).getX()+",x"+unions.get(1).getY());
        seriesThirdUnionMinimum.setName("c=a,b");

        for (int i = 0; i < Logic.tableSize; i++) {
            for (int j = 0; j < Logic.tableSize; j++) {
                if (logic.distanceTable[i][j]!=0){
                logic.distanceTable[i][j]=1/logic.distanceTable[i][j];
                }
            }
        }
        unions = logic.findMinimums();

        final NumberAxis xAxisMax = new NumberAxis();
        final NumberAxis yAxisMax = new NumberAxis();

        xAxisMax.setLabel("X");
        yAxisMax.setLabel("Y");

        final LineChart<Number,Number> lineChartMaximum = new LineChart<>(xAxisMax,yAxisMax);


        lineChartMaximum.setTitle("Иерархическое дерево, построенное по критерию максимума");

        XYChart.Series<Number,Number> seriesFirstUnionMaximum = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesSecondUnionMaximum = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesThirdUnionMaximum = new XYChart.Series<>();

        seriesFirstUnionMaximum.getData().add(new XYChart.Data<>(1,0));
        seriesFirstUnionMaximum.getData().add(new XYChart.Data<>(1,unions.get(0).getValue()));
        seriesFirstUnionMaximum.getData().add(new XYChart.Data<>(2,unions.get(0).getValue()));
        seriesFirstUnionMaximum.getData().add(new XYChart.Data<>(2,0));

        lineChartMaximum.getData().add(seriesFirstUnionMaximum);

        seriesSecondUnionMaximum.getData().add(new XYChart.Data<>(3,0));
        seriesSecondUnionMaximum.getData().add(new XYChart.Data<>(3,unions.get(1).getValue()));
        seriesSecondUnionMaximum.getData().add(new XYChart.Data<>(4,unions.get(1).getValue()));
        seriesSecondUnionMaximum.getData().add(new XYChart.Data<>(4,0));

        lineChartMaximum.getData().add(seriesSecondUnionMaximum);

        seriesThirdUnionMaximum.getData().add(new XYChart.Data<>(1.5,unions.get(0).getValue()));
        seriesThirdUnionMaximum.getData().add(new XYChart.Data<>(1.5,0.5));
        seriesThirdUnionMaximum.getData().add(new XYChart.Data<>(3.5,0.5));
        seriesThirdUnionMaximum.getData().add(new XYChart.Data<>(3.5,unions.get(1).getValue()));

        lineChartMaximum.getData().add(seriesThirdUnionMaximum);

        seriesFirstUnionMaximum.setName("a=x"+unions.get(0).getX()+",x"+unions.get(0).getY());
        seriesSecondUnionMaximum.setName("b=x"+unions.get(1).getX()+",x"+unions.get(1).getY());
        seriesThirdUnionMaximum.setName("c=a,b");


        HBox root = new HBox(lineChartMinimum, lineChartMaximum);

        stage.setScene(new Scene(root));
        stage.setTitle("lapa6");
        stage.show();
    }
}
