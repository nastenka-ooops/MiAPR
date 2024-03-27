package com.example.lapa2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Frame{

    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.PURPLE, Color.ORANGE, Color.YELLOW, Color.FUCHSIA, Color.BLACK, Color.BROWN, Color.CORAL, Color.AZURE, Color.FIREBRICK, Color.PEACHPUFF, Color.GAINSBORO, Color.LIGHTSALMON};
    private Map<Point, Color> map = new HashMap<>();
    public void drawPoints(GraphicsContext gc, List<Point> centers, List<ClusterSystem> system){

        for (int i = 0; i < centers.size(); i++) {
            Color c = colors[i];
            map.put(centers.get(i), c);
        }
        for (ClusterSystem clusterSystem :
                system) {
            for (Point center :
                    centers) {
                if (clusterSystem.getCenter().equals(center)) {
                    gc.setFill(map.get(center));
                    break;
                }
            }
                if (clusterSystem.getPoint().equals(clusterSystem.getCenter())) {
                    gc.fillOval(clusterSystem.getPoint().getX(), clusterSystem.getPoint().getY(), 20, 20);
                } else {
                    gc.fillOval(clusterSystem.getPoint().getX(), clusterSystem.getPoint().getY(), 5, 5);
                }
            }
        }
}
