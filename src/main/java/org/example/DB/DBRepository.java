package org.example.DB;

import org.example.DB.ModelsDB.ModuleEntity;
import org.example.DB.ModelsDB.StudentEntity;
import org.example.DB.ModelsDB.TaskEntity;
import org.example.Parser;
import org.example.Person;
import org.example.vkApi.VkRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class DBRepository {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(StudentEntity.class)
                .addAnnotatedClass(ModuleEntity.class)
                .addAnnotatedClass(TaskEntity.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        var file = "C:\\Users\\msape\\Desktop\\basicprogramming_2.csv";
        var values = Parser.readCSVFile(file);
        var students = Parser.parseStudents(values);
        var vk = new VkRepository();

        try{
            session.beginTransaction();
//            var a = checkHibernateWork();
//            session.save(a);


            //var counter = 0;
            for (var student : students) {
//                if (counter == 10){
//                    break;
//                }
                var studentEntity = new StudentEntity(
                        student.getName(),
                        student.getGroup(),
                        student.getPointsCount(),
                        vk.getStudentBirthMonth(student.getName()),
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

                session.save(studentEntity);
//                counter++;
            }

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    public static StudentEntity checkHibernateWork(){
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
        return studentEntity;
    }
}
