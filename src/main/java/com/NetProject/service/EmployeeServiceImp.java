package com.NetProject.service;

import com.NetProject.dao.AccountDAO;
import com.NetProject.dao.EmployeeDAO;
import com.NetProject.entity.Account;
import com.NetProject.entity.Employee;

import java.util.List;

public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeDAO empDAO = new EmployeeDAO();
    private final AccountDAO accDAO = new AccountDAO();

    @Override
    public List<Employee> getAllEmployees() {
        return empDAO.findAll();
    }

    @Override
    public boolean addEmployee(String realName, String username, String password, String role) {
        try {
            // 1. Tạo Tài khoản (Account) trước
            Account acc = new Account();
            String accId = "ACC_" + (System.currentTimeMillis() % 100000);
            acc.setAccountId(accId);
            acc.setUsername(username);
            acc.setPassword(password);
            acc.setRole(role);
            accDAO.create(acc);

            // 2. Tạo Hồ sơ Nhân viên (Employee) và nối với Account
            Employee emp = new Employee();
            emp.setEmployeeId("EMP_" + (System.currentTimeMillis() % 100000));
            emp.setEmployeeName(realName);
            emp.setAccount(acc);
            empDAO.create(emp);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(String empId) {
        try {
            Employee emp = empDAO.findById(empId);
            if (emp != null) {
                // Lấy Account ID để xóa luôn tài khoản sau khi xóa nhân viên
                String accId = emp.getAccount().getAccountId();
                empDAO.delete(emp);

                Account acc = accDAO.findById(accId);
                if (acc != null) {
                    accDAO.delete(acc);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}