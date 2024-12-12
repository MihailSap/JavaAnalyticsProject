package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.example.vkApi.VkRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException {
//        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
//        // Для работы с Hibernate
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        // На нём можно делать save, update, get и т.д.
//        Session session = sessionFactory.getCurrentSession();
//
//        try{
//            session.beginTransaction();
//            Person person = new Person("TestPerson", 50);
//            session.save(person);
//            session.getTransaction().commit();
//        } finally {
//            sessionFactory.close();
//        }



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