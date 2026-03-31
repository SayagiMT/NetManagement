package com.NetProject.dto;



import java.time.LocalDateTime;


public class InvoiceDTO {
    private String invoiceId;
    private LocalDateTime createdAt;
    private Float totalAmount;
    private String status;
    private String accountId;
    private String computerId;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String invoiceId, LocalDateTime createdAt, Float totalAmount, String status, String accountId, String computerId) {
        this.invoiceId = invoiceId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.accountId = accountId;
        this.computerId = computerId;
    }



    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getAccountId() {
        return accountId;
    }

    public void setAccount(String accountId) {
        this.accountId = accountId;
    }



    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }
}