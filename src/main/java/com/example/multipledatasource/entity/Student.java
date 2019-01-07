package com.example.multipledatasource.entity;

import lombok.Data;

/**
 * @author liuweibo
 * @date 2019/1/2
 */
@Data
public class Student {
    private Long id;
    private String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }
}
