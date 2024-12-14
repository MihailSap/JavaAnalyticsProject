package org.example.Models;

public class Task {
    private final String title;
    private final String type;
    private final int pointsCount;

    public Task(String title, String type, int maxPointsCount){
        this.title = title;
        this.type = type;
        this.pointsCount = maxPointsCount;
    }

    public String getTitle(){
        return title;
    }

    public String getType(){
        return type;
    }

    public int getPointsCount(){
        return pointsCount;
    }

    public String toString(){
        return String.format("""
                Задание: %s Тип: %s Баллы: %d
                """ , title, type, pointsCount);
    }
}
