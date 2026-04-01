package com.NetProject.dao;

import com.NetProject.entity.Customer;

public class MemberDAO extends GenericDAO<Customer, String> {
    public MemberDAO() {
        super(Customer.class);
    }
}