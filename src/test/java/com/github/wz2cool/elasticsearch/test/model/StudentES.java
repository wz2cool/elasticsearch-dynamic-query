package com.github.wz2cool.elasticsearch.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "test_student", type = "testStudent")
public class StudentES {
    @Transient
    private float score;
    @Id
    private Long id;
    private String name;
    private Integer age;
    @Transient
    private String nameHit;
    @Field(type = FieldType.Object)
    private ClassroomES classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNameHit() {
        return nameHit;
    }

    public void setNameHit(String nameHit) {
        this.nameHit = nameHit;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ClassroomES getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomES classroom) {
        this.classroom = classroom;
    }
}
