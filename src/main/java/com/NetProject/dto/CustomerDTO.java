package com.NetProject.dto;

public class CustomerDTO {
    private String accountId;
    private String username;
    private String password;
    private Float balance;

    public CustomerDTO(String accountId, String username, String password, Float balance) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Float getBalance() { return balance; }
}