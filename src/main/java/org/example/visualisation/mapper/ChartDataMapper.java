package org.example.visualisation.mapper;

import org.example.Models.Student;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.*;
import java.util.stream.Collectors;

public class ChartDataMapper {

    public static PieDataset createStudentByGroupDataset(ArrayList<Student> students){
        var studentsCountByGroups = students.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getBirthdayMonth,
                                HashMap::new,
                                Collectors.counting()
                        )
                );
        DefaultPieDataset dataset = new DefaultPieDataset();
        studentsCountByGroups.forEach(dataset::setValue);
        return dataset;
    }

    public static CategoryDataset createPointsDataset(ArrayList<Student> students) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        var monthsAvgMap = calculateAveragePointsByMonth(students);
        monthsAvgMap.forEach((k, v) -> dataset.setValue(v, "followersCount", k));
        return dataset;
    }

    public static CategoryDataset createPointsModulesDataset(ArrayList<Student> students) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        var avgPointsPerModule = calculateAveragePointsPerModule(students);
        avgPointsPerModule.forEach((k, v) -> dataset.setValue(v, "followersCount", k));
        return dataset;
    }

//    public static Map<String, Double> calculateAveragePointsByMonth(ArrayList<Student> students) {
//        return students.stream()
//                .filter(student -> student.getBirthdayMonth() != null)
//                .collect(Collectors.groupingBy(
//                        Student::getBirthdayMonth,
//                        Collectors.averagingInt(Student::getPointsCount)
//                ));
//    }

    public static Map<String, Double> calculateAveragePointsByMonth(ArrayList<Student> students) {
        return students.stream()
                .filter(student -> student.getBirthdayMonth() != null)
                .collect(Collectors.groupingBy(
                        Student::getBirthdayMonth,
                        Collectors.averagingInt(Student::getPointsCount)
                ))
                .entrySet()
                .stream()
                .filter(entry -> !"Нет данных".equals(entry.getKey()))
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static Map<String, Double> calculateAveragePointsPerModule(ArrayList<Student> students) {
        if (students == null || students.isEmpty()) {
            return new LinkedHashMap<>();
        }

        var moduleOrder = Arrays.asList(
                "Первое знакомство с C#", "Ошибки", "Ветвления", "Циклы", "Массивы",
                "Коллекции, строки, файлы", "Тестирование", "Сложность алгоритмов",
                "Рекурсивные алгоритмы", "Поиск и сортировка", "Практикум",
                "Основы ООП", "Наследование", "Целостность данных", "Структуры"
        );

        Map<String, Double> totalPointsByModule = new HashMap<>();
        Map<String, Integer> moduleCounts = new HashMap<>();

        for (Student student : students) {
            for (var module : student.getModulesForStudent()) {
                var moduleTitle = module.getTitle();
                totalPointsByModule.put(moduleTitle, totalPointsByModule.getOrDefault(moduleTitle, 0.0) + module.getPoints());
                moduleCounts.put(moduleTitle, moduleCounts.getOrDefault(moduleTitle, 0) + 1);
            }
        }

        Map<String, Double> averagePointsPerModule = new LinkedHashMap<>();

        for (String moduleTitle : moduleOrder) {
            if (totalPointsByModule.containsKey(moduleTitle)) {
                var totalPoints = totalPointsByModule.get(moduleTitle);
                var count = moduleCounts.get(moduleTitle);
                averagePointsPerModule.put(moduleTitle, totalPoints / count);
            }
        }

        return averagePointsPerModule;
    }
}