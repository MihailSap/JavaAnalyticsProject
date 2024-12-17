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
import java.util.stream.Collectors;

public class StudentsFromDBMapper {

    private static final SessionFactory sessionFactory;

    // Singleton
    static {
        sessionFactory = new Configuration()
                .addAnnotatedClass(StudentEntity.class)
                .addAnnotatedClass(ModuleEntity.class)
                .addAnnotatedClass(TaskEntity.class)
                .buildSessionFactory();
    }

    public static ArrayList<Student> getStudentsFromEntitys() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var studentEntities = session.createQuery("""
                            SELECT DISTINCT s FROM StudentEntity s
                            LEFT JOIN FETCH s.modulesForStudent m
                            LEFT JOIN FETCH m.tasksForModule t
                            """, StudentEntity.class).getResultList();

            var students = studentEntities.stream()
                    .map(StudentsFromDBMapper::mapToStudent)
                    .collect(Collectors.toCollection(ArrayList::new));

            session.getTransaction().commit();
            return students;
        }
    }

    private static Student mapToStudent(StudentEntity studentEntity) {
        var modules = studentEntity.getModulesForStudent().stream()
                .map(StudentsFromDBMapper::mapToModule)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Student(
                studentEntity.getName(),
                studentEntity.getGroup(),
                studentEntity.getPointsCount(),
                modules,
                studentEntity.getBirthdayMonth()
        );
    }

    private static Module mapToModule(ModuleEntity moduleEntity) {
        var tasks = moduleEntity.getTasksForModule().stream()
                .map(StudentsFromDBMapper::mapToTask)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Module(
                moduleEntity.getTitle(),
                tasks
        );
    }

    private static Task mapToTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getTitle(),
                taskEntity.getTaskType(),
                taskEntity.getPointsCount()
        );
    }
}
