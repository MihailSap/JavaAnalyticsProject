package org.example.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
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
}
