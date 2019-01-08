package com.example.multipledatasource.annotation;

import com.example.multipledatasource.enums.MultipleDatasourceTransactionEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * @author liuweibo
 * @date 2019/1/7
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultipleTransactional {

    @AliasFor("transactionManager")
    MultipleDatasourceTransactionEnum[] value() default {MultipleDatasourceTransactionEnum.READ};

    @AliasFor("value")
    MultipleDatasourceTransactionEnum[] transactionManager() default {MultipleDatasourceTransactionEnum.READ};

    Propagation propagation() default Propagation.REQUIRED;

    Isolation isolation() default Isolation.DEFAULT;

    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    Class<? extends Throwable>[] rollbackFor() default {};

    String[] rollbackForClassName() default {};

    Class<? extends Throwable>[] noRollbackFor() default {};

    String[] noRollbackForClassName() default {};
}
