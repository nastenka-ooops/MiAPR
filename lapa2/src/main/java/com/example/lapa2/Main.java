package com.example.lapa2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    Timer timer = new Timer();
    @Override
    public void stop() throws Exception {
        timer.cancel();
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("lapa2");

        Group root = new Group();
        Canvas canvas = new Canvas(700, 700);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root, 700,700));
        stage.show();

        Frame frame = new Frame();
        Logic logic = new Logic();
        ArrayList<Point> centers = new ArrayList<>();

        ArrayList<ClusterSystem> clusterSystem = logic.createSystem(centers);
        logic.findSecondCenter(centers, clusterSystem);
        logic.boundPointsToCenter(centers, clusterSystem);
        frame.drawPoints(gc, centers,clusterSystem);

        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                if (logic.findOtherCenter(centers, clusterSystem )){
                    logic.boundPointsToCenter(centers, clusterSystem);
                    gc.clearRect(0,0,700,700);
                    frame.drawPoints(gc, centers, clusterSystem);
                }
                else {
                    logic.resetCenters(centers, clusterSystem);
                    gc.clearRect(0,0,700,700);
                    frame.drawPoints(gc, centers, clusterSystem);
                }
            }
        };

        timer.schedule(timerTask, 500, 500);

    }
}
