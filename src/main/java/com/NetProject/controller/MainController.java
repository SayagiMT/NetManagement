package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.dto.ComputerDTO;
import com.NetProject.service.ComputerService;
import com.NetProject.view.frmMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainController {
    private frmMain mainView;
    private ComputerService computerService;
    private AccountDTO loggedInUser;

    public MainController(frmMain view, AccountDTO user) {
        this.mainView = view;
        this.loggedInUser = user;
        this.computerService = new ComputerService();

        mainView.setWelcomeText("Xin chào [" + user.getRole() + "] : " + user.getUsername());
        loadComputerMap();
    }

    private void loadComputerMap() {
        List<ComputerDTO> pcs = computerService.getAllComputersForDisplay();

        mainView.drawComputerMap(pcs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String computerId = e.getActionCommand();

                ComputerDTO clickedPc = null;
                for (ComputerDTO pc : pcs) {
                    if (pc.getComputerId().equals(computerId)) {
                        clickedPc = pc;
                        break;
                    }
                }

                if (clickedPc != null) {
                    if (clickedPc.getStatus().equalsIgnoreCase("Available")) {

                        int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Bạn có muốn mở máy " + clickedPc.getComputerName() + " không?",
                                "Xác nhận mở máy",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            boolean success = computerService.openComputer(computerId, loggedInUser.getAccountId());
                            if (success) {
                                loadComputerMap();
                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi khi mở máy!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } else if (clickedPc.getStatus().equalsIgnoreCase("In Use")) {

                        String[] options = {"🍔 Gọi Dịch Vụ (F&B)", "💰 Tính Tiền & Đóng Máy", "Hủy Bỏ"};
                        int choice = JOptionPane.showOptionDialog(
                                null,
                                "Chọn thao tác cho máy [" + clickedPc.getComputerName() + "]:",
                                "Quản lý máy đang hoạt động",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                        if (choice == 0) {
                            // Khách gọi dịch vụ tại máy
                            com.NetProject.view.frmOrder orderView = new com.NetProject.view.frmOrder();
                            new com.NetProject.controller.OrderController(orderView, loggedInUser, computerId);
                            orderView.setVisible(true);

                        } else if (choice == 1) {

                            int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "Xác nhận TÍNH TIỀN và ĐÓNG MÁY [" + clickedPc.getComputerName() + "]?",
                                    "Thanh toán",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (confirm == JOptionPane.YES_OPTION) {
                                String resultMessage = computerService.closeComputer(computerId);

                                // FIX LỖI 2: Đổi "Thanh toán" thành "HÓA ĐƠN" để khớp với ComputerService
                                if (resultMessage.startsWith("HÓA ĐƠN")) {
                                    JOptionPane.showMessageDialog(null, resultMessage, "Hóa đơn thanh toán", JOptionPane.INFORMATION_MESSAGE);
                                    loadComputerMap();
                                } else {
                                    JOptionPane.showMessageDialog(null, resultMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}