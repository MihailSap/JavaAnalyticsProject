package org.example.DB;

import org.example.DB.ModelsDB.ModuleEntity;
import org.example.DB.ModelsDB.StudentEntity;
import org.example.DB.ModelsDB.TaskEntity;
import org.example.Models.Module;
import org.example.Models.Student;
import org.example.Models.Task;
import org.example.Parser;
//import org.example.vkApi.VkRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBRepository {
    private static final int THREAD_COUNT = 50; // Количество потоков для обработки

    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(StudentEntity.class)
                .addAnnotatedClass(ModuleEntity.class)
                .addAnnotatedClass(TaskEntity.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try {
            saveStudentsToDBParallel(sessionFactory);
        } finally {
            sessionFactory.close();
        }
    }

    private static void saveStudentsToDBParallel(SessionFactory sessionFactory) throws IOException {
        var file = "C:\\Users\\msape\\Desktop\\basicprogramming_2.csv";
        var values = Parser.readCSVFile(file);
        var students = Parser.parseStudents(values);
        //var vk = new VkRepository();

        // Создаем пул потоков
        var executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        // Запускаем задачи в пуле
        for (Student student : students) {
            executorService.submit(() -> {
                saveStudent(student/*, vk*/, sessionFactory);
            });
        }

        // Останавливаем пул потоков
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Ждем завершения всех потоков
        }

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("Все студенты сохранены в БД.");
    }

    private static void saveStudent(Student student/*, VkRepository vk*/, SessionFactory sessionFactory) {
        // Создаем отдельную сессию для каждого потока
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var studentEntity = getStudentEntityToSave(student/*, vk*/);
            session.save(studentEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении студента: " + student.getName());
            e.printStackTrace();
        }
    }

    public static StudentEntity getStudentEntityToSave(Student student/*, VkRepository vk*/) {
        var studentEntity = new StudentEntity(
                student.getName(),
                student.getGroup(),
                student.getPointsCount(),
                //vk.getStudentBirthMonth(student.getName()),
                "Нет данных",
                "Нет данных"
        );
        var modulesForStudent = student.getModulesForStudent();
        for (var module : modulesForStudent) {
            var moduleEntity = new ModuleEntity(module.getTitle(), module.getPoints());
            var tasksForModule = module.getTasks();
            for (var task : tasksForModule) {
                var taskEntity = new TaskEntity(task.getTitle(), "practice", task.getPointsCount());
                moduleEntity.addTaskEntity(taskEntity);
            }
            studentEntity.addModuleEntity(moduleEntity);
        }

        return studentEntity;
    }

    public static void checkSaveDataHibernate(Session session){
        var studentEntity = new StudentEntity("Иванов Иван", "АТ-03", 400, "май", "Саратов");

        var moduleEntity1 = new ModuleEntity("Основы ООП", 200);
        var moduleEntity2 = new ModuleEntity("Наследование", 200);

        var taskEntity1 = new TaskEntity("ООП1", "Задание", 100);
        var taskEntity2 = new TaskEntity("ООП2", "Практика", 100);
        var taskEntity3 = new TaskEntity("Наследование1", "Задание", 100);
        var taskEntity4 = new TaskEntity("Наследование2", "Практика", 100);

        moduleEntity1.addTaskEntity(taskEntity1);
        moduleEntity1.addTaskEntity(taskEntity3);
        moduleEntity2.addTaskEntity(taskEntity2);
        moduleEntity2.addTaskEntity(taskEntity4);

        studentEntity.addModuleEntity(moduleEntity1);
        studentEntity.addModuleEntity(moduleEntity2);
        session.save(studentEntity);
    }
}
