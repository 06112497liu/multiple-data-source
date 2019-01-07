package com.example.multipledatasource.aspect;

import com.example.multipledatasource.annotation.MultipleTransactional;
import com.example.multipledatasource.utils.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

/**
 * @author liuweibo
 * @date 2019/1/7
 */
@Component
@Aspect
public class MultipleAspect {

    @Pointcut("@annotation(transactional)")
    public void pointCut(MultipleTransactional transactional) {

    }

    @Around("pointCut(transactional)")
    public Object arround(ProceedingJoinPoint joinPoint, MultipleTransactional transactional) throws Throwable {

        Stack<DataSourceTransactionManager> managerStack = new Stack<>();
        Stack<TransactionStatus> statusStack = new Stack<>();
        // 开启事务
        this.openTransaction(transactional, managerStack, statusStack);
        try {
            Object obj = joinPoint.proceed();
            this.commitTransaction(managerStack, statusStack);
            return obj;
        } catch (Throwable e) {
            this.rollBack(managerStack, statusStack);
            throw e;
        }
    }

    /**
     * 开启事务
     *
     * @param transactional
     * @param managerStack
     * @param statusStack
     */
    private void openTransaction(MultipleTransactional transactional,
                                 Stack<DataSourceTransactionManager> managerStack,
                                 Stack<TransactionStatus> statusStack) {
        String[] managerBeanNames = transactional.value();
        for (String managerBeanName : managerBeanNames) {
            DataSourceTransactionManager manager = SpringUtil.getBean(managerBeanName, DataSourceTransactionManager.class);
            TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());
            managerStack.push(manager);
            statusStack.push(status);
        }
    }

    /**
     * 提交事务
     *
     * @param managerStack
     * @param statusStack
     */
    private void commitTransaction(Stack<DataSourceTransactionManager> managerStack,
                                   Stack<TransactionStatus> statusStack) {
        while (!managerStack.isEmpty()) {
            managerStack.pop().commit(statusStack.pop());
        }
    }

    /**
     * 回滚事务
     *
     * @param managerStack
     * @param statusStack
     */
    private void rollBack(Stack<DataSourceTransactionManager> managerStack,
                          Stack<TransactionStatus> statusStack) {
        while (!managerStack.isEmpty()) {
            managerStack.pop().rollback(statusStack.pop());
        }
    }


}
