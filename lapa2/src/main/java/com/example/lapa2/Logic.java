package com.example.lapa2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    int numberOfPoints = 10000;

    List<ClusterSystem> system = new ArrayList<>();
    public ArrayList<ClusterSystem> createSystem(List<Point> centers){
        Random random = new Random();

        for (int i = 0; i < numberOfPoints; i++) {
            ClusterSystem clusterPoint = new ClusterSystem();
            Point point = new Point(random.nextInt(700)+1, random.nextInt(700)+1);
            clusterPoint.setPoint(point);
            system.add(clusterPoint);
        }

        centers.add(system.get(random.nextInt(numberOfPoints)).getPoint());
        for (int i = 0; i < numberOfPoints; i++) {
            system.get(i).setCenter(new Point(centers.get(0).getX(), centers.get(0).getY()));
        }
        return (ArrayList<ClusterSystem>) system;
    }

    public void findSecondCenter (List<Point> centers, List<ClusterSystem> system){
        double distToCenter;
        double maxDistToCenter = 0;
        Point newCenter = null;

        for (int i = 0; i < numberOfPoints; i++) {
            int point1 = centers.get(0).getX();
            int point2 = centers.get(0).getY();
            int point3 = system.get(i).getPoint().getX();
            int point4 = system.get(i).getPoint().getY();
            distToCenter= Math.sqrt(Math.pow((point1-point3), 2)+Math.pow((point2-point4),2));
            if (distToCenter>=maxDistToCenter){
                maxDistToCenter=distToCenter;
                newCenter = system.get(i).getPoint();
            }
        }
        centers.add(newCenter);

    }

    public boolean findOtherCenter (List<Point> centers, List<ClusterSystem> system){
        double distToCenter;
        double maxDistToCenter = 0;
        Point newCenter = null;

        for (int i = 0; i < numberOfPoints; i++) {
            if (!centers.contains(system.get(i).getPoint())) {
                for (int j = 0; j < centers.size(); j++) {
                    if (system.get(i).getCenter().equals(centers.get(j))) {
                        int point1 = centers.get(j).getX();
                        int point2 = centers.get(j).getY();
                        int point3 = system.get(i).getPoint().getX();
                        int point4 = system.get(i).getPoint().getY();
                        distToCenter = Math.sqrt(Math.pow((point1 - point3), 2) + Math.pow((point2 - point4), 2));
                        if (distToCenter >= maxDistToCenter) {
                            maxDistToCenter = distToCenter;
                            newCenter = system.get(i).getPoint();
                        }
                    }
                }
            }
        }
        if (maxDistToCenter >= calculateDistanceAverage(centers)/2){
            centers.add(newCenter);
            return true;
        }

        return false;

    }

    private double calculateDistanceAverage(List<Point> centers) {
        double totalDistance = 0;
        int numDistances = 0;

        for (int i = 0; i < centers.size(); i++) {
            Point p1 = new Point(centers.get(i).getX(), centers.get(i).getY());
            for (int j = i + 1; j < centers.size(); j++) {
                Point p2 = new Point(centers.get(j).getX(), centers.get(j).getY());
                double dist= Math.sqrt(Math.pow((p2.getX()- p1.getX()), 2)+Math.pow((p1.getY()) - p2.getY(),2));
                totalDistance +=dist;
                numDistances++;
            }
        }
        if (numDistances == 0){
            return 0;
        }
        return totalDistance/numDistances;
    }

    public void boundPointsToCenter(List<Point> centers, List<ClusterSystem> system){
        int point1, point2, point3, point4;
        double[] distance = new double[centers.size()];

        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < centers.size(); j++) {
                point1 = centers.get(j).getX();
                point2 = centers.get(j).getY();
                point3 = system.get(i).getPoint().getX();
                point4 = system.get(i).getPoint().getY();
                distance[j]= Math.sqrt(Math.pow((point1-point3), 2)+Math.pow((point2-point4),2));
            }
            int min = 0;
            for (int j = 1; j <centers.size() ; j++) {
                if (distance[j]<distance[min]){
                    min = j;
                }
            }
            system.get(i).getCenter().setX(centers.get(min).getX());
            system.get(i).getCenter().setY(centers.get(min).getY());
        }
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
