package org.example.DB.ModelsDB;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Module")
public class ModuleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "points_count")
    private int pointsCount;

    @OneToMany(mappedBy = "moduleEntityOwner", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<TaskEntity> tasksForModule = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity studentEntity;

    public ModuleEntity() {}

    public ModuleEntity(String title, int pointsCount) {
        this.title = title;
        this.pointsCount = pointsCount;
    }

    public void addTaskEntity(TaskEntity taskEntity) {
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

    public Set<TaskEntity> getTasksForModule() {
        return tasksForModule;
    }

    public void setTasksForModule(Set<TaskEntity> tasksForModule) {
        this.tasksForModule = tasksForModule;
    }
}
