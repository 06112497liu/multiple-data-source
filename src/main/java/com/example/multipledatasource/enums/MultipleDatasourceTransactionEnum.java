package com.example.multipledatasource.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.multipledatasource.constant.DatasourceConstant.READ_TRANSACTION_MANAGER;
import static com.example.multipledatasource.constant.DatasourceConstant.WRITE_TRANSACTION_MANAGER;

/**
 * @author liuweibo
 * @date 2019/1/8
 */
@Getter
@AllArgsConstructor
public enum MultipleDatasourceTransactionEnum {

    READ(READ_TRANSACTION_MANAGER, Boolean.TRUE),
    WRITE(WRITE_TRANSACTION_MANAGER, Boolean.FALSE);

    String transactionManager;

    Boolean readOnly;

}
