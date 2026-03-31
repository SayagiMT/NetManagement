package com.NetProject.entity;

import jakarta.persistence.*;

import java.util.Date;

// Xây dựng lớp đối tượng nhật ký sử dụng (Thực thể: NHATKYSUDUNG)
@Entity
public class SessionLog {
    private String sessionId;
    private Date startTime;
    private Date endTime;
    private Float deductedAmount;
    private Computer computer;
    private Account account;

    public SessionLog() {
    }

    public SessionLog(String sessionId, Date startTime, Date endTime, Float deductedAmount, Computer computer, Account account) {
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deductedAmount = deductedAmount;
        this.computer = computer;
        this.account = account;
    }

    // Mã phiên: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // Thời gian vào: Date (Thuộc tính mô tả)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    // Thời gian ra: Date (Thuộc tính mô tả)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    // Tiền trừ: float (Thuộc tính mô tả)
    public Float getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Float deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    // Mã máy <tham chiếu đến đối tượng máy tính>: (Khóa ngoại - Foreign Key)
    @ManyToOne
    @JoinColumn(name = "computerId")
    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    // Mã tài khoản <tham chiếu đến đối tượng tài khoản>: (Khóa ngoại - Foreign Key)
    // Quan hệ N-1 với Máy tính và Tài khoản
    @ManyToOne
    @JoinColumn(name = "accountId")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}