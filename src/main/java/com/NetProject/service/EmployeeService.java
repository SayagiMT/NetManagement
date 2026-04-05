package com.NetProject.service;

import com.NetProject.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    boolean addEmployee(String realName, String username, String password, String role);

    boolean deleteEmployee(String empId);
}
