package org.example.Models;

import java.util.ArrayList;

public class Module {
    private final String title;
    private final ArrayList<Task> tasks;
    
    public Module(String title, ArrayList<Task> tasks){
        this.title = title;
        this.tasks = tasks;
    }

    public String getTitle(){
        return title;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public String toString(){
        return String.format(""" 
                        TITLE: %s;
                        POINTS_COUNT: %s
                        TASKS: %s
                        """, title, getPoints(), tasks);
    }

    public int getPoints(){
        var sum = 0;
        for(var task : tasks){
            sum += task.getMaxPointsCount();
        }
        return sum;
    }
}
