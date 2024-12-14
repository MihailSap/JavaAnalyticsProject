package org.example.DB.Mapper;

import org.example.DB.ModelsDB.ModuleEntity;
import org.example.DB.ModelsDB.StudentEntity;
import org.example.DB.ModelsDB.TaskEntity;
import org.example.Models.Module;
import org.example.Models.Student;
import org.example.Models.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;

public class StudentsFromDBMapper {

    public static ArrayList<Student> getStudentsFromEntitys(){
        Configuration configuration = new Configuration()
                .addAnnotatedClass(StudentEntity.class)
                .addAnnotatedClass(ModuleEntity.class)
                .addAnnotatedClass(TaskEntity.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try{
            session.beginTransaction();
            List<StudentEntity> studentEntitys = session.createQuery("from StudentEntity")
                    .setMaxResults(20)
                    .getResultList();
            ArrayList<Student> oldStudents = new ArrayList<>();
            for(StudentEntity studentEntity : studentEntitys){
                ArrayList<Module> modulesForStudent = getModulesFromEntitys(studentEntity);
                var newStudent = new Student(
                        studentEntity.getName(),
                        studentEntity.getGroup(),
                        studentEntity.getPointsCount(),
                        modulesForStudent,
                        studentEntity.getBirthdayMonth()
                );
                oldStudents.add(newStudent);
            }
            session.getTransaction().commit();
            return oldStudents;
        } finally {
            sessionFactory.close();
        }
    }

    public static ArrayList<Module> getModulesFromEntitys(StudentEntity studentEntity){
        var moduleEntitys = studentEntity.getModulesForStudent();
        ArrayList<Module> modulesForStudent = new ArrayList<>();
        for(ModuleEntity moduleEntity : moduleEntitys){
            ArrayList<Task> tasksForModule = getTasksFromEntitys(moduleEntity);
            var newModule = new Module(
                    moduleEntity.getTitle(),
                    tasksForModule
            );
            modulesForStudent.add(newModule);
        }
        return modulesForStudent;
    }

    public static ArrayList<Task> getTasksFromEntitys(ModuleEntity moduleEntity){
        var taskEntitys = moduleEntity.getTasksForModule();
        ArrayList<Task> tasksForModule = new ArrayList<>();
        for(TaskEntity taskEntity : taskEntitys){
            var newTask = new Task(
                    taskEntity.getTitle(),
                    taskEntity.getTaskType(),
                    taskEntity.getPointsCount()
            );
            tasksForModule.add(newTask);
        }
        return tasksForModule;
    }
}
