package com.example.lapa1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("lapa1");

        Group root = new Group();
        Canvas canvas = new Canvas(700, 700);

        Scene scene = new Scene(root, 700,700);
        stage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.show();

        Frame frame = new Frame();
        Logic logic = new Logic();
        ArrayList<ClusterSystem> clusterSystem = logic.createSystem();
        List<Point> centers = logic.getCenters();

        logic.boundPointsToCenter(centers, clusterSystem);
        frame.drawPoints(gc, centers, clusterSystem);
        Timer timer = new Timer();
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                logic.resetCenters(centers, clusterSystem);
                gc.clearRect(0,0,700,700);
                frame.drawPoints(gc, centers, clusterSystem);
            }
        };

        timer.schedule(timerTask, 100, 500);


    }
}
