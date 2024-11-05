package org.example.Models;

import java.util.ArrayList;

public class Course {
    private ArrayList<Module> modules;
    private ArrayList<Student> students;

    public Course(
            // ArrayList<Module> modules,
            ArrayList<Student> students){
        // this.modules = modules;
        this.students = students;
    }

    public ArrayList<Module> getModules(){
        return modules;
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void setModules(ArrayList<Module> modules){
        this.modules = modules;
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }
}
