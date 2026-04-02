package com.NetProject.dao;

import com.NetProject.entity.Customer;

public class CustomerDAO extends GenericDAO<Customer, String> {
    public CustomerDAO() {
        super(Customer.class);
    }
}