package org.example.Models;

import com.vk.api.sdk.objects.base.City;

import java.util.ArrayList;

public class Student {
    private final String name;
    private final String group;
    private final int pointsCount;
    private final ArrayList<Module> modulesForStudent;
    private String birthdayMonth;
    private String city;

    public Student(String name, String group, int pointsCount, ArrayList<Module> modulesForStudent, String birthday){
        this.name = name;
        this.group = group;
        this.pointsCount = pointsCount;
        this.modulesForStudent = modulesForStudent;
        this.birthdayMonth = birthday;
    }
    public Student(String name, String group, int pointsCount, ArrayList<Module> modulesForStudent){
        this.name = name;
        this.group = group;
        this.pointsCount = pointsCount;
        this.modulesForStudent = modulesForStudent;
    }

    public ArrayList<Module> getModulesForStudent() {
        return modulesForStudent;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth){
        this.birthdayMonth = birthdayMonth;
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
                        Студент: %s
                        Группа:%s
                        Всего баллов:%s
                        Месяц рождения: %s
                        """,
                name, group, pointsCount, birthdayMonth, modulesForStudent);
    }


    public String setCity(City userIdByFullName) {
        return city;
    }
}
