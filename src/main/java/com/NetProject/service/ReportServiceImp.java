package com.NetProject.service;

import com.NetProject.dao.DepositTransactionDAO;
import com.NetProject.dao.InvoiceDAO;
import com.NetProject.dao.SessionLogDAO;
import com.NetProject.dto.RevenueDTO;
import com.NetProject.entity.DepositTransaction;
import com.NetProject.entity.SessionLog;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportServiceImp implements ReportService {
    private DepositTransactionDAO depositDAO = new DepositTransactionDAO();
    private SessionLogDAO sessionDAO = new SessionLogDAO();
    private InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    public List<RevenueDTO> getDailyRevenue() {
        Map<String, Float> depositMap = new HashMap<>();
        Map<String, Float> casualMap = new HashMap<>();

        java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 1. TÍNH TIỀN TỪ BẢNG GIAO DỊCH
            List<DepositTransaction> transactions = depositDAO.findAll();
            if (transactions != null) {
                for (DepositTransaction t : transactions) {
                    if (t.getDepositTime() != null && t.getAmount() != null) {
                        String dateKey = t.getDepositTime().format(dtfTime);

                        // Lọc bằng ID: Nếu là Nạp tiền chủ động (DEP_)
                        if (t.getTransactionId().startsWith("DEP_")) {
                            depositMap.put(dateKey, depositMap.getOrDefault(dateKey, 0f) + t.getAmount());
                        }
                        // Lọc bằng ID: Nếu là Tiền mặt khách hội viên đắp vào lúc thiếu (CASH_)
                        else if (t.getTransactionId().startsWith("CASH_")) {
                            casualMap.put(dateKey, casualMap.getOrDefault(dateKey, 0f) + t.getAmount());
                        }
                    }
                }
            }

            // 2. TÍNH TIỀN MẶT CỦA KHÁCH VÃNG LAI NGỒI MÁY
            List<SessionLog> sessions = sessionDAO.findAll();
            if (sessions != null) {
                for (SessionLog s : sessions) {
                    if (s.getEndTime() != null && s.getAccount() != null && s.getAccount().getRole() != null) {
                        if (!s.getAccount().getRole().equalsIgnoreCase("Member")) {
                            String dateKey = s.getEndTime().format(dtfTime);
                            float amount = s.getDeductedAmount() != null ? s.getDeductedAmount() : 0f;
                            casualMap.put(dateKey, casualMap.getOrDefault(dateKey, 0f) + amount);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. GỘP DANH SÁCH BÁO CÁO
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