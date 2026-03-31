package com.NetProject.service;

import com.NetProject.dao.AccountDAO;
import com.NetProject.dao.ComputerDAO;
import com.NetProject.dao.InvoiceDAO; // Khai báo thêm
import com.NetProject.dao.SessionLogDAO;
import com.NetProject.dto.ComputerDTO;
import com.NetProject.entity.Account;
import com.NetProject.entity.Computer;
import com.NetProject.entity.Invoice; // Khai báo thêm
import com.NetProject.entity.SessionLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComputerService {

    private final ComputerDAO computerDAO;
    private final SessionLogDAO sessionLogDAO;
    private final AccountDAO accountDAO;
    private final InvoiceDAO invoiceDAO; // 1. Khai báo InvoiceDAO

    public ComputerService() {
        this.computerDAO = new ComputerDAO();
        this.sessionLogDAO = new SessionLogDAO();
        this.accountDAO = new AccountDAO();
        this.invoiceDAO = new InvoiceDAO(); // 2. Khởi tạo InvoiceDAO
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
            log.setStartTime(new Date());
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
     * Hàm xử lý logic Đóng Máy và Tính Tổng Tiền (Máy + Dịch vụ)
     */
    public String closeComputer(String computerId) {
        try {
            Computer pc = computerDAO.findById(computerId);
            if (pc == null || !pc.getStatus().equalsIgnoreCase("In Use")) {
                return "Lỗi: Máy không hoạt động!";
            }

            // ==========================================
            // PHẦN 1: TÍNH TIỀN GIỜ CHƠI
            // ==========================================
            SessionLog log = sessionLogDAO.getActiveSessionByComputer(computerId);
            if (log == null) return "Lỗi: Không tìm thấy phiên chơi!";

            Date now = new Date();
            log.setEndTime(now);

            long diffInMillies = now.getTime() - log.getStartTime().getTime();
            if (diffInMillies < 60000) diffInMillies = 60000;
            float hoursPlayed = (float) diffInMillies / (1000 * 60 * 60);

            float pricePerHour = pc.getZone().getHourlyRate();
            float totalFee = hoursPlayed * pricePerHour; // Đây là biến totalFee bị thiếu lúc nãy

            // ==========================================
            // PHẦN 2: TÍNH TIỀN DỊCH VỤ (F&B)
            // ==========================================
            List<Invoice> unpaidInvoices = invoiceDAO.getUnpaidInvoicesByComputer(computerId);
            float totalServiceFee = 0;

            for (Invoice inv : unpaidInvoices) {
                totalServiceFee += inv.getTotalAmount();
                inv.setStatus("Đã giao"); // Đã thanh toán xong thì chốt đơn
                invoiceDAO.update(inv);
            }

            // ==========================================
            // PHẦN 3: GỘP BILL VÀ GIẢI PHÓNG MÁY
            // ==========================================
            float finalTotal = totalFee + totalServiceFee;
            log.setDeductedAmount(finalTotal);
            sessionLogDAO.update(log);

            pc.setStatus("Available");
            computerDAO.update(pc);

            // Trả về Hóa đơn tổng hợp
            return String.format("HÓA ĐƠN TỔNG HỢP\n" +
                    "Thời gian chơi: %.2f giờ\n" +
                    "Tiền máy: %,.0f VNĐ\n" +
                    "Tiền dịch vụ: %,.0f VNĐ\n" +
                    "------------------\n" +
                    "TỔNG CỘNG KHÁCH TRẢ: %,.0f VNĐ", hoursPlayed, totalFee, totalServiceFee, finalTotal);

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống khi đóng máy!";
        }
    }
}