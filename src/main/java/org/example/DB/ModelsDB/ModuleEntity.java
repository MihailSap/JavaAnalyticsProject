package org.example.DB.ModelsDB;

import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Module")
public class ModuleEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name = "points_count")
    private int pointsCount;

    @OneToMany(mappedBy = "moduleEntityOwner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<TaskEntity> tasksForModule;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity studentEntity;

    public ModuleEntity() {}

    public ModuleEntity(String title, int pointsCount) {
        this.title = title;
        this.pointsCount = pointsCount;
    }

    public void addTaskEntity(TaskEntity taskEntity){
        if (this.tasksForModule == null) {
            this.tasksForModule = new ArrayList<>();
        }
        this.tasksForModule.add(taskEntity);
        taskEntity.setModuleEntityOwner(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public List<TaskEntity> getTasksForModule() {
        return tasksForModule;
    }

    public void setTasksForModule(List<TaskEntity> tasksForModule) {
        this.tasksForModule = tasksForModule;
    }
}
