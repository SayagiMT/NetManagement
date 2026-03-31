package com.NetProject.dto;



import java.time.LocalDateTime;


public class DepositTransactionDTO {
    private String transactionId;
    private Float amount;
    private LocalDateTime depositTime;
    private String accountId;
    private String employeeId;

    public DepositTransactionDTO() {
    }

    public DepositTransactionDTO(String transactionId, Float amount, LocalDateTime depositTime, String accountId, String employeeId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.depositTime = depositTime;
        this.accountId = accountId;
        this.employeeId = employeeId;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }


    public LocalDateTime getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(LocalDateTime depositTime) {
        this.depositTime = depositTime;
    }


    public String getAccount() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }



    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}