package com.example.multipledatasource.entity;

import lombok.Data;

/**
 * @author liuweibo
 * @date 2019/1/2
 */
@Data
public class Person {
    private Long id;
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
}
