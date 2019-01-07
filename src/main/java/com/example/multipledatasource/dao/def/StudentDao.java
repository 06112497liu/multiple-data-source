package com.example.multipledatasource.dao.def;

import com.example.multipledatasource.entity.Student;

import java.util.List;

/**
 * @author liuweibo
 * @date 2019/1/2
 */
public interface StudentDao {

    List<Student> get();

    void add(Student student);
}
