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
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        checkVkApiWork();
        // checkDBMapperWork();
    }

    public static void checkVkApiWork() throws IOException, ClientException, ApiException {
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

    public static void checkDBMapperWork(){
        var students = StudentsFromDBMapper.getStudentsFromEntitys();
        for (var student : students){
            System.out.println(student);
        }
    }
}