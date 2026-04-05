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

public class ComputerServiceImp implements ComputerService {

    private final ComputerDAO computerDAO;
    private final SessionLogDAO sessionLogDAO;
    private final AccountDAO accountDAO;
    private final InvoiceDAO invoiceDAO;

    public ComputerServiceImp() {
        this.computerDAO = new ComputerDAO();
        this.sessionLogDAO = new SessionLogDAO();
        this.accountDAO = new AccountDAO();
        this.invoiceDAO = new InvoiceDAO();
    }

    /**
     * Lấy toàn bộ danh sách máy tính để hiển thị lên màn hình chính
     */
    @Override
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
    @Override
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
    @Override
    public String closeComputer(String computerId, String cashierName) {
        try {
            Computer pc = computerDAO.findById(computerId);
            if (pc == null || !pc.getStatus().equalsIgnoreCase("In Use")) {
                return "Lỗi: Máy không hoạt động!";
            }

            SessionLog log = sessionLogDAO.getActiveSessionByComputer(computerId);
            if (log == null) return "Lỗi: Không tìm thấy phiên chơi!";

            // PHẦN 1: TÍNH TIỀN THEO PHIÊN
            LocalDateTime now = LocalDateTime.now();
            log.setEndTime(now);

            long secondsPlayed = java.time.Duration.between(log.getStartTime(), now).getSeconds();

            int blockMinutes = 30;
            double blockSeconds = blockMinutes * 60.0;

            long blocksPlayed = (long) Math.ceil(secondsPlayed / blockSeconds);
            if (blocksPlayed < 1) blocksPlayed = 1;

            float pricePerHour = pc.getZone().getHourlyRate();
            float pricePerBlock = pricePerHour * (blockMinutes / 60.0f);

            float totalFee = blocksPlayed * pricePerBlock;

            long displayMinutes = (long) Math.ceil(secondsPlayed / 60.0);
            if (displayMinutes < 1) displayMinutes = 1;

            // PHẦN 2: TÍNH TIỀN DỊCH VỤ
            List<Invoice> unpaidInvoices = invoiceDAO.getUnpaidInvoicesByComputer(computerId);
            float totalServiceFee = 0;

            for (Invoice inv : unpaidInvoices) {
                totalServiceFee += inv.getTotalAmount();
                inv.setStatus("Đã giao");
                invoiceDAO.update(inv);
            }


            // PHẦN 3 & 4: XỬ LÝ THANH TOÁN
            // Tính tổng chi phí gốc
            float exactTotal = Math.round(totalFee + totalServiceFee);
            float finalDeducted; // Biến này để lưu tổng tiền vào SessionLog
            String billMessage;

            Account player = log.getAccount();

            if (player != null && player.getRole().equalsIgnoreCase("Member")) {
                float currentBalance = player.getBalance() != null ? player.getBalance() : 0f;

                if (currentBalance >= exactTotal) {
                    // TRƯỜNG HỢP 1: HỘI VIÊN ĐỦ TIỀN
                    // -> Trừ chính xác từng đồng lẻ, KHÔNG làm tròn lên ngàn
                    float newBalance = currentBalance - exactTotal;
                    player.setBalance(newBalance);
                    accountDAO.update(player);

                    finalDeducted = exactTotal;

                    billMessage = String.format("HÓA ĐƠN (HỘI VIÊN)\n" +
                                    "Thu ngân: %s\n" +
                                    "Tài khoản: %s\n" +
                                    "Thời gian chơi: %d phút\n" +
                                    "Tiền máy: %,.0f VNĐ\n" +
                                    "Tiền dịch vụ: %,.0f VNĐ\n" +
                                    "------------------\n" +
                                    "TỔNG CỘNG: %,.0f VNĐ\n" +
                                    "(Đã trừ chính xác vào số dư tài khoản)\n" +
                                    "Số dư còn lại: %,.0f VNĐ",
                            cashierName, player.getUsername(), displayMinutes, totalFee, totalServiceFee, exactTotal, newBalance);
                } else {
                    // TRƯỜNG HỢP 2: HỘI VIÊN THIẾU TIỀN
                    // -> Chỉ làm tròn lên ngàn đối với phần tiền mặt phải thu thêm
                    float exactMissing = exactTotal - currentBalance;

                    // Làm tròn phần tiền mặt thu thêm lên mức 1.000đ chẵn
                    float cashNeeded = (float) (Math.ceil(exactMissing / 1000.0) * 1000);

                    // Tính phần tiền dư ra do việc làm tròn để nạp ngược lại cho khách
                    float surplus = cashNeeded - exactMissing;

                    // Cập nhật ví (Khách có lại phần tiền thừa)
                    player.setBalance(surplus);
                    accountDAO.update(player);

                    finalDeducted = currentBalance + cashNeeded;

                    // Lưu vết số tiền mặt thu thêm vào DB
                    com.NetProject.entity.DepositTransaction cashReceipt = new com.NetProject.entity.DepositTransaction();
                    cashReceipt.setTransactionId("CASH_" + System.currentTimeMillis());
                    cashReceipt.setAmount(cashNeeded);
                    cashReceipt.setDepositTime(java.time.LocalDateTime.now());
                    cashReceipt.setAccount(player);
                    new com.NetProject.dao.DepositTransactionDAO().create(cashReceipt);

                    billMessage = String.format("HÓA ĐƠN (HỘI VIÊN - TRẢ THÊM TIỀN MẶT)\n" +
                                    "Thu ngân: %s\n" +
                                    "Tài khoản: %s\n" +
                                    "Thời gian chơi: %d phút\n" +
                                    "------------------\n" +
                                    "Tổng chi phí: %,.0f VNĐ\n" +
                                    "Tiền trong tài khoản: %,.0f VNĐ\n" +
                                    "CẦN THU TIỀN MẶT: %,.0f VNĐ\n",
                            cashierName, player.getUsername(), displayMinutes, exactTotal, currentBalance, cashNeeded, surplus);
                }
            } else {
                // TRƯỜNG HỢP 3: KHÁCH VÃNG LAI
                // -> Phải trả bằng tiền mặt toàn bộ, làm tròn tổng bill lên mức ngàn đồng
                finalDeducted = (float) (Math.ceil(exactTotal / 1000.0) * 1000);

                billMessage = String.format("HÓA ĐƠN (KHÁCH VÃNG LAI)\n" +
                                "Thu ngân: %s\n" +
                                "Thời gian chơi: %d phút\n" +
                                "Tiền máy: %,.0f VNĐ\n" +
                                "Tiền dịch vụ: %,.0f VNĐ\n" +
                                "------------------\n" +
                                "TỔNG CỘNG KHÁCH TRẢ: %,.0f VNĐ",
                        cashierName, displayMinutes, totalFee, totalServiceFee, finalDeducted);
            }

            // Ghi Log tổng tiền cuối cùng và Đóng máy
            log.setDeductedAmount(finalDeducted);
            sessionLogDAO.update(log);

            pc.setStatus("Available");
            computerDAO.update(pc);

            return billMessage;

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi hệ thống khi đóng máy!";
        }
    }
}