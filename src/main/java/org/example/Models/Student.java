package org.example.Models;

public class Student {
    private final String name;
    private final String surname;
    private final String group;
    private final int pointsCount;

    public Student(String name, String surname, String group, int pointsCount){
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.pointsCount = pointsCount;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getGroup(){
        return group;
    }

    public int getPointsCount(){
        return pointsCount;
    }
}
