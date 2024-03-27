package com.example.lapa3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        int windowSize = 700;

        Label lFalseAlarm = new Label("False alarm");
        TextField tfFalseAlarm = new TextField();
        HBox hbFalseAlarm = new HBox(10, lFalseAlarm, tfFalseAlarm);


        Label lMissDetection = new Label("Miss detection");
        TextField tfMissDetection = new TextField();
        HBox hbMissDetection = new HBox(10, lMissDetection, tfMissDetection);

        Label lTotalError = new Label("Total error");
        TextField tfTotalError = new TextField();
        HBox hbTotalError = new HBox(10, lTotalError, tfTotalError);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Xm");
        yAxis.setLabel("p");

        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Плотности распределения случайной величины");
        lineChart.setPrefSize(windowSize,windowSize-100);

        XYChart.Series<Number,Number> seriesFirstValue = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesSecondValue = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesCoincidenceValue = new XYChart.Series<>();

        Logic logic = new Logic();
        logic.createPoints(seriesFirstValue, seriesSecondValue);
        int[] positions = logic.searchCenter(seriesCoincidenceValue);

        tfFalseAlarm.setText(String.valueOf(logic.findFalseAlarm(positions[0])));
        tfMissDetection.setText(String.valueOf(logic.findMissDetection(positions[1])));
        tfTotalError.setText(String.valueOf(logic.findFalseAlarm(positions[0])+logic.findMissDetection(positions[1])));

        lineChart.getData().addAll(seriesFirstValue, seriesSecondValue, seriesCoincidenceValue);

        for (XYChart.Series<Number, Number> s : lineChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                d.getNode().setVisible(false);
            }
        }


        VBox root = new VBox(5, lineChart, hbFalseAlarm, hbMissDetection, hbTotalError);

        stage.setScene(new Scene(root, windowSize,windowSize));
        stage.setTitle("lapa3");
        stage.show();
    }
}

