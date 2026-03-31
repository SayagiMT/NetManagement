package com.NetProject.dto;



public class EmployeeDTO {
    private String employeeId;
    private String employeeName;
    private String shift;
    private String accountId;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String employeeId, String employeeName, String shift, String accountId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.shift = shift;
        this.accountId = accountId;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccount(String accountId) {
        this.accountId = accountId;
    }
}