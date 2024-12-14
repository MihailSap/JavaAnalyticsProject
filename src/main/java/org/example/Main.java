package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.example.DB.Mapper.StudentsFromDBMapper;
import org.example.Models.Student;
import org.example.visualisation.drawer.BarChartDrawer;
import org.example.visualisation.drawer.LineChartDrawer;
import org.example.visualisation.drawer.PieChartDrawer;
import org.example.vkApi.VkRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException {
        var students = StudentsFromDBMapper.getStudentsFromEntitys();
//        Map<String, Double> averagePointsPerModule = calculateAveragePointsPerModule(students);
//
//        // Вывод результатов
//        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
////        for(var student : students) {
////            System.out.println(student);
////        }
//        for (Map.Entry<String, Double> entry : averagePointsPerModule.entrySet()) {
//            System.out.printf("Средний балл для модуля \"%s\": %.2f%n", entry.getKey(), entry.getValue());
//        }



        //List<Student> students = Arrays.asList(
//                new Student("Alice", "Group1", 85, new ArrayList<>(), "January"),
//                new Student("Bob", "Group1", 90, new ArrayList<>(), "January"),
//                new Student("Charlie", "Group2", 70, new ArrayList<>(), "February"),
//                new Student("David", "Group2", 75, new ArrayList<>(), "February"),
//                new Student("Eve", "Group3", 95, new ArrayList<>(), "March")
//        );

        // Вычисляем средние баллы по месяцам
//        Map<String, Double> averagePointsByMonth = calculateAveragePointsByMonth(students);

        // Выводим результаты
//        averagePointsByMonth.forEach((month, avgPoints) ->
//                System.out.println("Месяц: " + month + ", Средние баллы: " + avgPoints));


        // Примерно 2 минуты запускается
        // setVisible - заставляем PieChart показаться
        // new PieChartDrawer("ГЛАВНОЕ MAIN название графика", students).setVisible(true);
        // new BarChartDrawer("ГЛАВНОЕ MAIN название графика", students).setVisible(true);
        // new LineChartDrawer("ГЛАВНОЕ MAIN название графика", students).setVisible(true);
    }



    public static Map<String, Double> calculateAveragePointsByMonth(List<Student> students) {
        // Группируем студентов по месяцу рождения и считаем средние баллы
        return students.stream()
                .filter(student -> student.getBirthdayMonth() != null) // Игнорируем студентов без указанного месяца рождения
                .collect(Collectors.groupingBy(
                        Student::getBirthdayMonth, // Группировка по месяцу рождения
                        Collectors.averagingInt(Student::getPointsCount) // Среднее значение баллов
                ));
    }

    public void checkDBMapperWork(){
        var a = StudentsFromDBMapper.getStudentsFromEntitys();
        for (var student : a){
            System.out.println(student);
        }
    }

    public void checkVkApiWork() throws IOException {
        var file = "C:\\Users\\msape\\Desktop\\basicprogramming_2.csv";
        var values = Parser.readCSVFile(file);
        var students = Parser.parseStudents(values);
        var vk = new VkRepository();
        ArrayList<String> months = new ArrayList<>();

        var countNoData = 0;
        var countData = 0;

        for(var student : students) {
            var month = vk.getStudentBirthMonth(student.getName());
            if (month.equals("Нет данных")){
                countNoData++;
            }
            else{
                countData++;
            }
            months.add(month);
            student.setBirthdayMonth(month);
            System.out.println(student.getName());
            System.out.println(month);
        }
        System.out.println("Всего обработано операций: " + months.size());
        System.out.println("Найдено данных: " + countData);
        System.out.println("Не найдено данных: " + countNoData);
    }
}