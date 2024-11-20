package org.example.Models;

import java.util.ArrayList;

public class Student {
    private final String name;
    private final String group;
    private final int pointsCount;
    private final ArrayList<Module> modulesForStudent;
    private String city;

    public Student(String name, String group, int pointsCount, ArrayList<Module> modulesForStudent, String city){
        this.name = name;
        this.group = group;
        this.pointsCount = pointsCount;
        this.modulesForStudent = modulesForStudent;
        this.city = city;
    }

    public Student(String name, String group, int pointsCount, ArrayList<Module> modulesForStudent){
        this.name = name;
        this.group = group;
        this.pointsCount = pointsCount;
        this.modulesForStudent = modulesForStudent;
    }

    public String getName(){
        return name;
    }

    public String getGroup(){
        return group;
    }

    public int getPointsCount(){
        return pointsCount;
    }

    public String toString(){
        return String.format("""
                        NAME: %s
                        GROUP:%s
                        POINTS:%s
                        MODULES_FOR_STUDENT:%s
                        """,
                name, group, pointsCount, modulesForStudent);
    }
}
