package com.NetProject.service;

import com.NetProject.dao.DepositTransactionDAO;
import com.NetProject.dao.SessionLogDAO;
import com.NetProject.dto.RevenueDTO;
import com.NetProject.entity.DepositTransaction;
import com.NetProject.entity.SessionLog;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportService {
    private DepositTransactionDAO depositDAO = new DepositTransactionDAO();
    private SessionLogDAO sessionDAO = new SessionLogDAO();

    public List<RevenueDTO> getDailyRevenue() {
        Map<String, Float> depositMap = new HashMap<>();
        Map<String, Float> casualMap = new HashMap<>();

        java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 1. Tính tiền nạp (Kiểm tra an toàn List null)
            List<DepositTransaction> deposits = depositDAO.findAll();
            if (deposits != null) {
                for (DepositTransaction d : deposits) {
                    if (d.getDepositTime() != null && d.getAmount() != null) {
                        String dateKey = d.getDepositTime().format(dtfTime);
                        depositMap.put(dateKey, depositMap.getOrDefault(dateKey, 0f) + d.getAmount());
                    }
                }
            }

            // 2. Tính tiền vãng lai (Kiểm tra an toàn List null và Account null)
            List<SessionLog> sessions = sessionDAO.findAll();
            if (sessions != null) {
                for (SessionLog s : sessions) {
                    if (s.getEndTime() != null && s.getAccount() != null && s.getAccount().getRole() != null) {
                        if (!s.getAccount().getRole().equalsIgnoreCase("Member")) {
                            String dateKey = sdfDate.format(s.getEndTime());
                            float amount = s.getDeductedAmount() != null ? s.getDeductedAmount() : 0f;
                            casualMap.put(dateKey, casualMap.getOrDefault(dateKey, 0f) + amount);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi truy xuất dữ liệu báo cáo!");
        }

        // 3. Gộp danh sách
        List<RevenueDTO> report = new ArrayList<>();
        Set<String> allDates = new TreeSet<>(Collections.reverseOrder());
        allDates.addAll(depositMap.keySet());
        allDates.addAll(casualMap.keySet());

        for (String dateStr : allDates) {
            try {
                Date d = sdfDate.parse(dateStr);
                report.add(new RevenueDTO(d,
                        depositMap.getOrDefault(dateStr, 0f),
                        casualMap.getOrDefault(dateStr, 0f)));
            } catch (Exception e) { e.printStackTrace(); }
        }
        return report;
    }
}