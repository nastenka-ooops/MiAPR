package com.example.lapa5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        int windowSize = 700;

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);

        lineChart.setPrefSize(windowSize,windowSize);
        lineChart.setLegendVisible(false);


        XYChart.Series<Number,Number> seriesDecisionFunctionLessZero = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesDecisionFunctionMoreZero = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesFirstClassValue = new XYChart.Series<>();
        XYChart.Series<Number,Number> seriesSecondClassValue = new XYChart.Series<>();

        Logic logic = new Logic();
        double [] coefficients = logic.calculateCoefficients();

        for (double x = -5; x <=-0.24; x += 0.1) {
            double y = -(coefficients[1]*x + coefficients[0])/(coefficients[3]*x+coefficients[2]);
            seriesDecisionFunctionLessZero.getData().add(new XYChart.Data<>(x, y));
        }

        lineChart.getData().add(seriesDecisionFunctionLessZero);

        for (double x =-0.2; x <=5; x += 0.1) {
            double y = -(coefficients[1]*x + coefficients[0])/(coefficients[3]*x+coefficients[2]);
            seriesDecisionFunctionMoreZero.getData().add(new XYChart.Data<>(x, y));
        }
        lineChart.getData().add(seriesDecisionFunctionMoreZero);

        for (XYChart.Series<Number, Number> s : lineChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                d.getNode().setVisible(false);
            }
            s.getNode().setStyle("-fx-stroke: red");
        }

        logic.distributeClasses(coefficients, seriesFirstClassValue, seriesSecondClassValue);
        lineChart.getData().add(seriesFirstClassValue);
        seriesFirstClassValue.getNode().setStyle("-fx-stroke-width: 0px;");
        lineChart.getData().add(seriesSecondClassValue);
        seriesSecondClassValue.getNode().setStyle("-fx-stroke-width: 0px;");

        VBox root = new VBox(lineChart);

        stage.setScene(new Scene(root, windowSize,windowSize));
        stage.setTitle("lapa5");
        stage.show();
    }
}
