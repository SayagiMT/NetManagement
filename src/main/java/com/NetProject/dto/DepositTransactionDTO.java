package com.NetProject.dto;

import java.util.Date;

public class DepositTransactionDTO {
    private String transactionId;
    private Float amount;
    private Date depositDate;
    private String accountId;   // Chỉ lưu ID thay vì nguyên cục Account Entity
    private String username;    // Lưu thêm tên để dễ hiển thị lên bảng

    public DepositTransactionDTO(String transactionId, Float amount, Date depositDate, String accountId, String username) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.depositDate = depositDate;
        this.accountId = accountId;
        this.username = username;
    }

    // Các hàm Getters
    public String getTransactionId() { return transactionId; }
    public Float getAmount() { return amount; }
    public Date getDepositDate() { return depositDate; }
    public String getAccountId() { return accountId; }
    public String getUsername() { return username; }
}