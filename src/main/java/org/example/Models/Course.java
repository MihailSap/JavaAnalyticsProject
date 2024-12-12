package org.example.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
//    private ArrayList<Module> modules;
//    private ArrayList<Student> students;
    private HashMap<Student, ArrayList<Module>> studentsToModules;

    public Course(HashMap<Student, ArrayList<Module>> moduleMap){
        this.studentsToModules = moduleMap;
    }

    public HashMap<Student, ArrayList<Module>> getStudentsToModules(){
        return studentsToModules;
    }

    public void setStudentsToModules(Student student, ArrayList<Module> module){
        studentsToModules.put(student, module);
    }

//    public String toString(){
//    }


//    public ArrayList<Module> getModules(){
//        return modules;
//    }
//
//    public ArrayList<Student> getStudents(){
//        return students;
//    }
//
//    public void setModules(ArrayList<Module> modules){
//        this.modules = modules;
//    }
//
//    public void setStudents(ArrayList<Student> students){
//        this.students = students;
//    }
}
