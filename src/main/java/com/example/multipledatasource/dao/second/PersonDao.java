package com.example.multipledatasource.dao.second;

import com.example.multipledatasource.entity.Person;

import java.util.List;

/**
 * @author liuweibo
 * @date 2019/1/2
 */
public interface PersonDao {
    List<Person> get();

    void add(Person person);
}
