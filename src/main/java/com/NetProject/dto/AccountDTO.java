package com.NetProject.dto;

public class AccountDTO {
    private String accountId;
    private String username;
    private Float balance;
    private String role;

    public AccountDTO() {
    }

    public AccountDTO(String accountId, String username, Float balance, String role) {
        this.accountId = accountId;
        this.username = username;
        this.balance = balance;
        this.role = role;
    }

    // Getters và Setters
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Float getBalance() { return balance; }
    public void setBalance(Float balance) { this.balance = balance; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}