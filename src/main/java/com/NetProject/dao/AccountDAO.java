package com.NetProject.dao;

import com.NetProject.entity.Account;

public class AccountDAO extends GenericDAO<Account, String> {

    public AccountDAO() {
        super(Account.class);
    }

    // Hàm kiểm tra đăng nhập tận dụng executeQuery
    public Account checkLogin(String username, String password) {
        return executeQuery(session ->
                session.createQuery("FROM Account a WHERE a.username = :user AND a.password = :pass", Account.class)
                        .setParameter("user", username)
                        .setParameter("pass", password)
                        .uniqueResult()
        );
    }
}