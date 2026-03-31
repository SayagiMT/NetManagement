package com.NetProject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Thực thể: GIAODICHNAPTIEN
@Entity
public class DepositTransaction {
    private String transactionId;
    private Float amount;
    private LocalDateTime depositTime;
    private Account account;
    private Employee employee;

    public DepositTransaction() {
    }

    public DepositTransaction(String transactionId, Float amount, LocalDateTime depositTime, Account account, Employee employee) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.depositTime = depositTime;
        this.account = account;
        this.employee = employee;
    }

    // Mã giao dịch: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Số tiền nạp: float (Thuộc tính mô tả)
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    // Thời gian nạp: Date (Thuộc tính mô tả)
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(LocalDateTime depositTime) {
        this.depositTime = depositTime;
    }

    // Mã tài khoản <tham chiếu đến đối tượng tài khoản>: (Khóa ngoại - Foreign Key)
    // Quan hệ N-1 với Tài khoản (Tài khoản được nạp)
    @ManyToOne
    @JoinColumn(name = "accountId")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Mã nhân viên <tham chiếu đến đối tượng nhân viên>: (Khóa ngoại - Foreign Key)
    // Quan hệ N-1 với Nhân viên (Người thu tiền)
    @ManyToOne
    @JoinColumn(name = "employeeId")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}