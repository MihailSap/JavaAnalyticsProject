package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var file = "C:\\Users\\msape\\Desktop\\basicprogramming_2.csv";
        var values = Parser.readCSVFile(file);
        printStudents(values);
    }

    public static void printStudents(List<String[]> values) {
        var students = Parser.parseStudents(values);
        for(var student : students) {
            System.out.println(student.toString());
        }
    }
}