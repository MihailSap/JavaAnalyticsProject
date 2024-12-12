package org.example;

import com.opencsv.exceptions.CsvException;
import org.example.Models.Module;
import org.example.Models.*;
import com.opencsv.*;
import java.util.*;
import java.io.*;

public class Parser {
    public static List<String[]> readCSVFile(String file){
        var parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (var reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file)))
                .withCSVParser(parser)
                .build()) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Student> parseStudents(List<String[]> values) throws IOException {
        var students = new ArrayList<Student>();
        for(int i = 3; i < values.size(); i++){
            var name = values.get(i)[0];
            var group = values.get(i)[1];
            var pointsCount = Integer.parseInt(values.get(i)[2])
                    + Integer.parseInt(values.get(i)[3])
                    + Integer.parseInt(values.get(i)[4]);
            var modulesForStudent = parseModules(values, i);
            var student = new Student(name, group, pointsCount, modulesForStudent);
            students.add(student);
        }
        return students;
    }

    public static ArrayList<Module> parseModules(List<String[]> values, int indexStudent){
        var tasks = new ArrayList<Task>();
        var modules = new ArrayList<Module>();
        var len = values.getFirst().length;
        var titleModule = values.getFirst()[7];
        for(int i = 7; i < len; i++){
            var line = values.get(1)[i];
            if (!Objects.equals(values.getFirst()[i], "")){
                var module = new org.example.Models.Module(titleModule, tasks);
                modules.add(module);
                tasks = new ArrayList<Task>();
                titleModule = values.getFirst()[i];
            }
            if (line.contains("Упр:")){
                var title = line.replace("Упр: ", "");
                var type = TasksTypes.exercize;
                var maxPointsCount = Integer.parseInt(values.get(indexStudent)[i]);
                var task = new Task(title, type, maxPointsCount);
                tasks.add(task);
            } else if (line.contains("ДЗ:")){
                var title = line.replace("ДЗ: ", "");
                var type = TasksTypes.practice;
                var maxPointsCount = Integer.parseInt(values.get(indexStudent)[i]);
                var task = new Task(title, type, maxPointsCount);
                tasks.add(task);
            }
        }
        modules.removeFirst();
        return modules;
    }
}