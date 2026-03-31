package com.NetProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Thực thể: TAIKHOAN
@Entity
public class Account {
    private String accountId;
    private String username;
    private String password;
    private Float balance;
    private String role;

    public Account() {
    }

    public Account(String accountId, String username, String password, Float balance, String role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    // Mã tài khoản: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    // Tên đăng nhập: String (Thuộc tính mô tả)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Mật khẩu: String (Thuộc tính mô tả)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Số dư: float (Thuộc tính mô tả)
    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    // Vai trò: String <Admin, NhanVien, HoiVien> (Thuộc tính mô tả)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}