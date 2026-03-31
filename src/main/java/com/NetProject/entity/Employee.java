package com.NetProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

// Thực thể: NHANVIEN
@Entity
public class Employee {
    private String employeeId;
    private String employeeName;
    private String shift;
    private Account account;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, String shift, Account account) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.shift = shift;
        this.account = account;
    }

    // Mã nhân viên: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    // Tên nhân viên: String (Thuộc tính mô tả)
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    // Ca làm việc: String (Thuộc tính mô tả)
    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    // Mã tài khoản <tham chiếu đến đối tượng tài khoản>: (Khóa ngoại - Foreign Key)
    // Quan hệ 1-1 với Tài khoản
    @OneToOne
    @JoinColumn(name = "accountId")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}