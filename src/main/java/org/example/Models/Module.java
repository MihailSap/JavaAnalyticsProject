package org.example.Models;

import java.util.ArrayList;

public class Module {
    private final String title;
    private ArrayList<Task> tasks;
    
    public Module(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }
}
