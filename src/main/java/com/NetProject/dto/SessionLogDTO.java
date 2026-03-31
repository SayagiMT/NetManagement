package com.NetProject.dto;

import java.util.Date;

public class SessionLogDTO {
    private String sessionId;
    private Date startTime;
    private Date endTime;
    private Float deductedAmount;
    private String computerId;
    private String accountId;


    public SessionLogDTO() {
    }


    public SessionLogDTO(String sessionId, Date startTime, Date endTime, Float deductedAmount, String computerId, String accountId) {
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deductedAmount = deductedAmount;
        this.computerId = computerId;
        this.accountId = accountId;

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Float getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Float deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}