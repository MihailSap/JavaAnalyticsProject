package org.example.Models;

import java.util.ArrayList;

public class Module {
    private final String title;
    private final ArrayList<Task> tasks;
    private final int points;
    
    public Module(String title, ArrayList<Task> tasks){
        this.title = title;
        this.tasks = tasks;
        points = getPoints();
    }

    public Module(String title, ArrayList<Task> tasks, int points){
        this.title = title;
        this.tasks = tasks;
        this.points = points;
    }

    public String getTitle(){
        return title;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public String toString(){
        return String.format(""" 
                        
                        Модуль: %s;
                        Баллы: %s
                        Задания: %s
                        """, title, getPoints(), tasks);
    }

    public int getPoints() {
        return tasks.stream()
                .mapToInt(Task::getPointsCount)
                .sum();
    }
}
