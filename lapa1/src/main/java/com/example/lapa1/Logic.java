package com.example.lapa1;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    int numberOfCenters=8;
    int numberOfPoints = 10000;
    List<Point> centers = new ArrayList<>();
    List<ClusterSystem> system = new ArrayList<>();
    public ArrayList<ClusterSystem> createSystem(){
        Random random = new Random();

        for (int i = 0; i < numberOfPoints; i++) {
            ClusterSystem clusterPoint = new ClusterSystem();
            Point point = new Point(random.nextInt(700)+1, random.nextInt(700)+1);
            clusterPoint.setPoint(point);
            system.add(clusterPoint);
        }

        for (int i = 0; i < numberOfCenters; i++) {
            centers.add(system.get(i).getPoint());
        }

        for (int i = 0; i < numberOfPoints; i++) {
            Point center = centers.get(random.nextInt(numberOfCenters));
            system.get(i).setCenter(new Point(center.getX(), center.getY()));
        }
        return (ArrayList<ClusterSystem>) system;
    }

    public void boundPointsToCenter(List<Point> centers, List<ClusterSystem> system){
        int point1, point2, point3, point4;
        double[] distance = new double[numberOfCenters];

        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < numberOfCenters; j++) {
                point1 = centers.get(j).getX();
                point2 = centers.get(j).getY();
                point3 = system.get(i).getPoint().getX();
                point4 = system.get(i).getPoint().getY();
                distance[j]= Math.sqrt(Math.pow((point1-point3), 2)+Math.pow((point2-point4),2));
            }
            int min = 0;
            for (int j = 1; j <numberOfCenters ; j++) {
                if (distance[j]<distance[min]){
                    min = j;
                }
            }
            system.get(i).getCenter().setX(centers.get(min).getX());
            system.get(i).getCenter().setY(centers.get(min).getY());
        }
    }

    public List<Point> getCenters() {
        return centers;
    }

    public void setCenters(List<Point> centers) {
        this.centers = centers;
    }

    public void resetCenters(List<Point> centers, List<ClusterSystem> system) {
        boolean equal = true;
        while (equal) {
            boundPointsToCenter(centers, system);
            equal = false;
            for (Point center :
                    centers) {
                long cmp1 = Integer.MAX_VALUE;
                Point newCenter = new Point(center.getX(), center.getY());
                Point oldCenter = new Point(center.getX(), center.getY());
                for (ClusterSystem clusterSystem :
                        system) {
                    if (clusterSystem.getCenter().equals(oldCenter)) {
                        long cmp2 = 0;
                        for (ClusterSystem clusterSystem2 :
                                system) {
                            if (clusterSystem2.getCenter().equals(oldCenter)) {
                                int point1 = clusterSystem.getPoint().getX();
                                int point2 = clusterSystem.getPoint().getY();
                                int point3 = clusterSystem2.getPoint().getX();
                                int point4 = clusterSystem2.getPoint().getY();
                                cmp2 += (long) (Math.pow((point1 - point3), 2) + Math.pow((point2 - point4), 2));
                            }
                        }
                        if (cmp2 < cmp1) {
                            cmp1 = cmp2;
                            newCenter.setX(clusterSystem.getPoint().getX());
                            newCenter.setY(clusterSystem.getPoint().getY());
                        }
                    }
                }
                center.setX(newCenter.getX());
                center.setY(newCenter.getY());
                if (center.equals(oldCenter)){
                    equal=true;
                }
                for (ClusterSystem clusterSystem :
                        system) {
                    if (clusterSystem.getCenter().equals(oldCenter)){
                        clusterSystem.getCenter().setX(newCenter.getX());
                        clusterSystem.getCenter().setY(newCenter.getY());
                    }
                }
            }
        }
    }
}
