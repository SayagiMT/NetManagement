package com.NetProject.controller;

import com.NetProject.dto.RevenueDTO;
import com.NetProject.service.ReportService;
import com.NetProject.service.ReportServiceImp;
import com.NetProject.view.frmReport;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportController {
    public ReportController(frmReport view) {
        ReportService service = new ReportServiceImp();
        List<RevenueDTO> data = service.getDailyRevenue();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        float grandTotal = 0;

        // 2. Duyệt qua sổ sách của từng ngày để in lên bảng
        for (RevenueDTO r : data) {
            view.getModel().addRow(new Object[]{
                    sdf.format(r.getReportDate()),
                    String.format("%,.0f", r.getDepositRevenue()),
                    String.format("%,.0f", r.getCasualRevenue()),
                    String.format("%,.0f", r.getTotal())
            });
            grandTotal += r.getTotal();
        }

        view.getLblTotalRevenue().setText("TỔNG DOANH THU HỆ THỐNG: " + String.format("%,.0f", grandTotal) + " VNĐ");
    }
}