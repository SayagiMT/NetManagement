package com.NetProject.dto;

import java.util.Date;

public class RevenueDTO {
    private Date reportDate;
    private float depositRevenue;  // Tiền nạp hội viên
    private float casualRevenue;   // Tiền khách vãng lai trả (giờ + dịch vụ)
    private float total;           // Tổng cộng

    public RevenueDTO(Date reportDate, float depositRevenue, float casualRevenue) {
        this.reportDate = reportDate;
        this.depositRevenue = depositRevenue;
        this.casualRevenue = casualRevenue;
        this.total = depositRevenue + casualRevenue;
    }

    // Getters
    public Date getReportDate() { return reportDate; }
    public float getDepositRevenue() { return depositRevenue; }
    public float getCasualRevenue() { return casualRevenue; }
    public float getTotal() { return total; }
}