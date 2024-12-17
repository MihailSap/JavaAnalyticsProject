package org.example.DB.ModelsDB;

import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Student")
public class StudentEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="study_group")
    private String group;

    @Column(name = "points_count")
    private int pointsCount;

    @Column(name = "birthday_month")
    private String birthdayMonth;

    @Column(name = "city")
    private String city;

    // Заменяем List на Set
    @OneToMany(mappedBy = "studentEntity", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<ModuleEntity> modulesForStudent = new HashSet<>();

    public StudentEntity() {}

    public StudentEntity(String name, String group, int pointsCount, String birthdayMonth, String city) {
        this.name = name;
        this.group = group;
        this.pointsCount = pointsCount;
        this.birthdayMonth = birthdayMonth;
        this.city = city;
    }

    public void addModuleEntity(ModuleEntity moduleEntity){
        this.modulesForStudent.add(moduleEntity);
        moduleEntity.setStudentEntity(this);
    }

    // Геттер для Set
    public Set<ModuleEntity> getModulesForStudent() {
        return modulesForStudent;
    }

    // Сеттер для Set
    public void setModulesForStudent(Set<ModuleEntity> modulesForStudent) {
        this.modulesForStudent = modulesForStudent;
    }

    // Остальные геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
