package com.github.wz2cool.elasticsearch.test.model;

import org.springframework.data.annotation.Id;

/**
 * @author Frank
 **/
public class MyClassroomES {

    @Id
    private Long id;
    private String name;

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
}
