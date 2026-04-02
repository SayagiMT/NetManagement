package com.NetProject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Thực thể: HOADON
@Entity
public class Invoice {
    private String invoiceId;
    private LocalDateTime createdAt;
    private Float totalAmount;
    private String status;
    private Account account;
    private Computer computer;

    public Invoice() {
    }

    public Invoice(String invoiceId, LocalDateTime createdAt, Float totalAmount, String status, Account account, Computer computer) {
        this.invoiceId = invoiceId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.account = account;
        this.computer = computer;
    }

    // Mã hóa đơn: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    // Thời gian lập: LocalDateTime (Thuộc tính mô tả)
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Tổng tiền: float (Thuộc tính mô tả)
    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Trạng thái: String <Chưa giao, Đã giao> (Thuộc tính mô tả)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Mã tài khoản <tham chiếu đến đối tượng tài khoản>: (Khóa ngoại - Foreign Key)
    // Quan hệ N-1 với Tài khoản
    @ManyToOne
    @JoinColumn(name = "accountId")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Mã máy <tham chiếu đến đối tượng máy tính>: (Khóa ngoại - Foreign Key) ]
    // Quan hệ N-1 với Máy tính
    @ManyToOne
    @JoinColumn(name = "computerId")
    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}