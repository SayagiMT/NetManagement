package com.NetProject.dao;

import com.NetProject.entity.Employee;

public class EmployeeDAO extends GenericDAO<Employee, String> {
    public EmployeeDAO() {
        super(Employee.class);
    }
}