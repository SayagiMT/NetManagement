package com.NetProject.service;

import com.NetProject.dao.AccountDAO;
import com.NetProject.dao.ComputerDAO;
import com.NetProject.dao.InvoiceDAO;
import com.NetProject.dao.SessionLogDAO;
import com.NetProject.dto.ComputerDTO;
import com.NetProject.entity.Account;
import com.NetProject.entity.Computer;
import com.NetProject.entity.Invoice;
import com.NetProject.entity.SessionLog;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class ComputerService {

    private final ComputerDAO computerDAO;
    private final SessionLogDAO sessionLogDAO;
    private final AccountDAO accountDAO;
    private final InvoiceDAO invoiceDAO;

    public ComputerService() {
        this.computerDAO = new ComputerDAO();
        this.sessionLogDAO = new SessionLogDAO();
        this.accountDAO = new AccountDAO();
        this.invoiceDAO = new InvoiceDAO();
    }

    /**
     * Lấy toàn bộ danh sách máy tính để hiển thị lên màn hình chính
     */
    public List<ComputerDTO> getAllComputersForDisplay() {
        List<Computer> listPCs = computerDAO.findAll();
        List<ComputerDTO> listDTOs = new ArrayList<>();

        if (listPCs != null) {
            for (Computer pc : listPCs) {
                listDTOs.add(new ComputerDTO(
                        pc.getComputerId(),
                        pc.getComputerName(),
                        pc.getStatus(),
                        pc.getZone().getZoneName(),
                        pc.getZone().getHourlyRate()
                ));
            }
        }
        return listDTOs;
    }

    /**
     * Hàm xử lý logic Mở Máy tính
     */
    public boolean openComputer(String computerId, String accountId) {
        try {
            Computer pc = computerDAO.findById(computerId);
            Account acc = accountDAO.findById(accountId);

            if (pc == null || acc == null || !pc.getStatus().equalsIgnoreCase("Available")) {
                return false;
            }

            pc.setStatus("In Use");
            computerDAO.update(pc);

            SessionLog log = new SessionLog();
            log.setSessionId("SES_" + System.currentTimeMillis());
            log.setStartTime(LocalDateTime.now());
            log.setComputer(pc);
            log.setAccount(acc);
            log.setDeductedAmount(0f);

            sessionLogDAO.create(log);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Hàm xử lý logic Đóng Máy và Tính Tổng Tiền (Có hiển thị Thu ngân)
     */
    public String closeComputer(String computerId, String cashierName) {
        try {
            Computer pc = computerDAO.findById(computerId);
            if (pc == null || !pc.getStatus().equalsIgnoreCase("In Use")) {
                return "Lỗi: Máy không hoạt động!";
            }

            SessionLog log = sessionLogDAO.getActiveSessionByComputer(computerId);
            if (log == null) return "Lỗi: Không tìm thấy phiên chơi!";

            // PHẦN 1: TÍNH TIỀN GIỜ CHƠI
            LocalDateTime now = LocalDateTime.now();
            log.setEndTime(now);

            // Dùng java.time.Duration để lấy khoảng cách giữa 2 mốc thời gian (tính bằng Phút)
            long minutesPlayed = java.time.Duration.between(log.getStartTime(), now).toMinutes();

            // Nếu khách vừa ngồi vào chưa được 1 phút (ví dụ lỡ tay bật) -> tính tối thiểu 1 phút
            if (minutesPlayed < 1) minutesPlayed = 1;

            // Đổi số phút ra số Giờ (chia 60.0f để ra số thập phân)
            float hoursPlayed = (float) minutesPlayed / 60.0f;

            float pricePerHour = pc.getZone().getHourlyRate();
            float totalFee = hoursPlayed * pricePerHour;

            // PHẦN 2: TÍNH TIỀN DỊCH VỤ
            List<Invoice> unpaidInvoices = invoiceDAO.getUnpaidInvoicesByComputer(computerId);
            float totalServiceFee = 0;

            for (Invoice inv : unpaidInvoices) {
                totalServiceFee += inv.getTotalAmount();
                inv.setStatus("Đã giao");
                invoiceDAO.update(inv);
            }

            // PHẦN 3: GỘP BILL VÀ GIẢI PHÓNG MÁY
            float finalTotal = totalFee + totalServiceFee;
            log.setDeductedAmount(finalTotal);
            sessionLogDAO.update(log);

            pc.setStatus("Available");
            computerDAO.update(pc);


            // PHẦN 4: IN HÓA ĐƠN CÓ TÊN THU NGÂN
            Account player = log.getAccount();

            if (player != null && player.getRole().equalsIgnoreCase("Member")) {
                float currentBalance = player.getBalance() != null ? player.getBalance() : 0f;
                float newBalance = currentBalance - finalTotal;

                if (newBalance >= 0) {
                    player.setBalance(newBalance);
                    accountDAO.update(player);

                    return String.format("HÓA ĐƠN (HỘI VIÊN)\n" +
                                    "Thu ngân: %s\n" +
                                    "Tài khoản: %s\n" +
                                    "Thời gian chơi: %.2f giờ\n" +
                                    "Tiền máy: %,.0f VNĐ\n" +
                                    "Tiền dịch vụ: %,.0f VNĐ\n" +
                                    "------------------\n" +
                                    "TỔNG CỘNG: %,.0f VNĐ\n" +
                                    "(Đã trừ thẳng vào số dư tài khoản)\n" +
                                    "Số dư còn lại: %,.0f VNĐ",
                            cashierName, player.getUsername(), hoursPlayed, totalFee, totalServiceFee, finalTotal, newBalance);
                } else {
                    // Hội viên thiếu tiền -> Lưu vết số tiền mặt thu thêm
                    float cashNeeded = Math.abs(newBalance);

                    com.NetProject.entity.DepositTransaction cashReceipt = new com.NetProject.entity.DepositTransaction();
                    cashReceipt.setTransactionId("CASH_" + System.currentTimeMillis());
                    cashReceipt.setAmount(cashNeeded);
                    cashReceipt.setDepositTime(java.time.LocalDateTime.now());
                    cashReceipt.setAccount(player);

                    // Ghi xuống DB
                    new com.NetProject.dao.DepositTransactionDAO().create(cashReceipt);

                    // Ép ví về 0đ
                    player.setBalance(0f);
                    accountDAO.update(player);

                    return String.format("HÓA ĐƠN\n" +
                                    "Thu ngân: %s\n" +
                                    "Tài khoản: %s\n" +
                                    "Thời gian chơi: %.2f giờ\n" +
                                    "Tiền máy: %,.0f VNĐ\n" +
                                    "Tiền dịch vụ: %,.0f VNĐ\n" +
                                    "------------------\n" +
                                    "TỔNG CỘNG: %,.0f VNĐ\n" +
                                    "Số dư trong ví chỉ có: %,.0f VNĐ\n" +
                                    "Thu thêm tiền mặt: %,.0f VNĐ",
                            cashierName, player.getUsername(), hoursPlayed, totalFee, totalServiceFee, finalTotal, currentBalance, cashNeeded);
                }
            } else {
                return String.format("HÓA ĐƠN\n" +
                                "Thu ngân: %s\n" +
                                "Thời gian chơi: %.2f giờ\n" +
                                "Tiền máy: %,.0f VNĐ\n" +
                                "Tiền dịch vụ: %,.0f VNĐ\n" +
                                "------------------\n" +
                                "TỔNG CỘNG KHÁCH TRẢ TIỀN MẶT: %,.0f VNĐ",
                        cashierName, hoursPlayed, totalFee, totalServiceFee, finalTotal);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống khi đóng máy!";
        }
    }
}