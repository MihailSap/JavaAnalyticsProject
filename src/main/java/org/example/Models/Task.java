package org.example.Models;

import java.util.HashMap;

public class Task {
    private final String title;
    private final TasksTypes type;
    private final int maxPointsCount;
//     private HashMap<Student, Integer> pointsByStudents;

    public Task(String title, TasksTypes type, int maxPointsCount){
        this.title = title;
        this.type = type;
        this.maxPointsCount = maxPointsCount;
    }

    public String getTitle(){
        return title;
    }

    public TasksTypes getType(){
        return type;
    }

    public int getMaxPointsCount(){
        return maxPointsCount;
    }

    public String toString(){
        // Убрать перенос в строках 30-31, чтобы при выводе не было отступа
        return String.format("""
                TITLE: %s
                TYPE: %s
                MAX POINTS: %d
                """, title, type, maxPointsCount);
    }

//    public HashMap<Student, Integer> getPointsByStudents(){
//        return pointsByStudents;
//    }
}
