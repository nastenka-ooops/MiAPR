package com.example.lapa6;

import java.util.*;

public class Logic {
    public static int tableSize;
    public double[][] distanceTable;
    public ArrayList<Point> minimums;
    Random random = new Random();
    public Logic(int tableSize) {
        Logic.tableSize = tableSize;
        this.distanceTable = new double[tableSize][tableSize];
        this.minimums = new ArrayList<>();
    }

    public void initializeDistanceTable(){
        /*for (int i = 0; i < tableSize; i++) {
            for (int j = i+1; j < tableSize; j++) {
                distanceTable[i][j]= random.nextInt(5)+1;
                distanceTable[j][i] = distanceTable[i][j];
            }
        }*/
        distanceTable = new double[][]{{0, 5, 0.5,2},{5,0,1,0.6},{0.5,1,0,2.5},{2,0.6,2.5,0}};

    }

    private Point findMin(){
        double minValue = distanceTable[0][1];
        int minX = 0;
        int minY = 1;
        for (int i = 0; i < tableSize; i++) {
            for (int j = i+1; j < tableSize; j++) {
                if (distanceTable[i][j]<minValue){
                    minValue = distanceTable[i][j];
                    minX=i;
                    minY=j;
                }
            }
        }
        return new Point(minX, minY, minValue);
    }


    public ArrayList<Point> findMinimums(){
        ArrayList<Point> list = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            for (int j = i+1; j < tableSize; j++) {
                list.add(new Point(i,j,distanceTable[i][j]));
            }
        }
        list.sort(Comparator.comparing(Point::getValue));
        double minDistance = list.get(0).getValue();
        Set<Integer> newSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue()==minDistance){
                if (!newSet.contains(list.get(i).getX()) || !newSet.contains(list.get(i).getY())){
                    newSet.add(list.get(i).getX());
                    newSet.add(list.get(i).getY());
                } else{
                    list.remove(i);
                    i--;
                }
            } else {
                if(newSet.contains(list.get(i).getX()) || newSet.contains(list.get(i).getY())){
                    minDistance=list.get(i).getValue();
                    newSet.add(list.get(i).getX());
                    newSet.add(list.get(i).getY());
                }
            }
        }

        return forDrawing(list);
    }
    private ArrayList<Point> forDrawing(ArrayList<Point> list){
        ArrayList<Point> draw = new ArrayList<>();
        double mind = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            if (mind<list.get(i).getValue()){
                mind = list.get(i).getValue();
                if (!set.contains(list.get(i).getX()) || !set.contains(list.get(i).getY())){
                    draw.add(new Point(0,0,mind));
                    if (!set.contains(list.get(i).getX())){
                        draw.get(draw.size()-1).setX(list.get(i).getX());
                        set.add(list.get(i).getX());
                    }
                    if (!set.contains(list.get(i).getY())){
                        draw.get(draw.size()-1).setY(list.get(i).getY());
                        set.add(list.get(i).getY());
                    }
                }
            } else {
                if (!set.contains(list.get(i).getX())){
                    draw.get(draw.size()-1).setX(list.get(i).getX());
                    set.add(list.get(i).getX());
                }
                if (!set.contains(list.get(i).getY())){
                    draw.get(draw.size()-1).setY(list.get(i).getY());
                    set.add(list.get(i).getY());
                }
            }
        }
        return draw;
    }
}
