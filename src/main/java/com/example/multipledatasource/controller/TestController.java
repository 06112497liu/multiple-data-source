package com.example.multipledatasource.controller;

import com.example.multipledatasource.dao.read.StudentDao;
import com.example.multipledatasource.dao.write.PersonDao;
import com.example.multipledatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweibo
 * @date 2019/1/2
 */
@RestController
public class TestController {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TestService testService;

    @RequestMapping("test")
    public String test() {
        System.out.println(this.personDao.get());
        System.out.println(this.studentDao.get());
        return "test";
    }

    @GetMapping("/transaction/test/{e}")
    public String transactionTest(@PathVariable int e) {
        this.testService.transactionTest(e);
        return "操作成功";
    }

    @GetMapping("/transaction/aliyun/test")
    public String aliyunTest() {
        this.testService.addPerson();
        return "操作成功";
    }
}
