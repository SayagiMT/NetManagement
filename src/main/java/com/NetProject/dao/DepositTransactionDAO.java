package com.NetProject.dao;

import com.NetProject.entity.DepositTransaction;

public class DepositTransactionDAO extends GenericDAO<DepositTransaction, String> {
    public DepositTransactionDAO() {
        super(DepositTransaction.class);
    }
}