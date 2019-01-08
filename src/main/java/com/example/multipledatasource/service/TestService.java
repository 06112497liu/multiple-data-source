package com.example.multipledatasource.service;

import com.example.multipledatasource.annotation.MultipleTransactional;
import com.example.multipledatasource.constant.DatasourceConstant;
import com.example.multipledatasource.dao.read.StudentDao;
import com.example.multipledatasource.dao.write.PersonDao;
import com.example.multipledatasource.entity.Person;
import com.example.multipledatasource.entity.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuweibo
 * @date 2019/1/7
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestService {

    @Autowired
    PersonDao personDao;

    @Autowired
    StudentDao studentDao;

    /**
     * 多数据源情况下，该事务注解需要指定事务管理器。否则，由于不知道使用的事务管理器，实际情况是没走事务的。
     *
     * @param e
     */
    @MultipleTransactional(
        value = {
            DatasourceConstant.READ_TRANSACTION_MANAGER,
            DatasourceConstant.WRITE_TRANSACTION_MANAGER
        }
    )
    public void transactionTest(int e) {
        this.studentDao.add(new Student("戴尔"));
        if (e == 2) {
            throw new RuntimeException("回滚戴尔");
        }
        this.personDao.add(new Person("阿里云"));
        if (e == 1) {
            throw new RuntimeException("回滚阿里云");
        }
    }

    @Transactional(transactionManager = "secondTransactionManager")
    public void addPerson() {
        this.personDao.add(new Person("阿里云"));
        throw new RuntimeException("回滚阿里云");
    }

}
