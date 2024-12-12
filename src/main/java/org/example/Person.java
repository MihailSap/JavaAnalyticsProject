package org.example;

import javax.persistence.*;

@Entity
@Table(name="Person")
public class Person {
    @Id // Так помечаем первичные ключи
    @Column(name="id") // Название соответствующего поля из таблицы
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Означает, что postgres сам генерирует уникальные id. Следовательно Hibernate вообще не трогает эту колонку
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    public Person() {} // Для всех Entity нужен пустой конструктор

    // А также обычный конструктор, с геттерами и сеттерами
    public Person(String name, int age) {
        this.id = id; // Значение не передаём, т.к. Postgres генерирует сам
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
