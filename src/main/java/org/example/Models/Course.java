package org.example.Models;

import java.util.ArrayList;

public class Course {
    private ArrayList<Module> modules;
    private ArrayList<Student> students;

    public ArrayList<Module> getModules(){
        return modules;
    }

    public ArrayList<Student> getStudents(){
        return students;
    }
}
