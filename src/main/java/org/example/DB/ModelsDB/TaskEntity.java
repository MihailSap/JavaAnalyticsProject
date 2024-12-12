package org.example.DB.ModelsDB;

import javax.persistence.*;

@Entity
@Table(name = "Task")
public class TaskEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private ModuleEntity moduleEntityOwner;

    @Column(name="title")
    private String title;

    @Column(name="task_type")
    private String taskType;

    @Column(name = "points_count")
    private int pointsCount;

    public TaskEntity() {}

    public TaskEntity(String title, String taskType, int pointsCount) {
        this.title = title;
        this.taskType = taskType;
        this.pointsCount = pointsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public ModuleEntity getModuleEntityOwner() {
        return moduleEntityOwner;
    }

    public void setModuleEntityOwner(ModuleEntity moduleEntityOwner) {
        this.moduleEntityOwner = moduleEntityOwner;
    }

    public void setTaskEntity(ModuleEntity moduleEntity) {

    }
}
