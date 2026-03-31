package com.NetProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

// Thực thể: KHACHHANG
@Entity
public class Customer {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private Account account;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String phoneNumber, Account account) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    // Mã khách hàng: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Tên khách hàng: String (Thuộc tính mô tả)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Số điện thoại: String (Thuộc tính mô tả)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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