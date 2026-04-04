package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.dto.ComputerDTO;
import com.NetProject.service.AccountService;
import com.NetProject.service.ComputerService;
import com.NetProject.view.frmCustomer;
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

        // Hiển thị lời chào
        mainView.setWelcomeText("Xin chào [" + user.getRole() + "] : " + user.getUsername());

        // Vẽ sơ đồ phòng máy
        loadComputerMap();

        // ==========================================
        // SỰ KIỆN: MỞ FORM QUẢN LÝ HỘI VIÊN
        // ==========================================
        this.mainView.getBtnManageMembers().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmCustomer memberView = new frmCustomer();
                new CustomerController(memberView, loggedInUser);
                memberView.setVisible(true);
            }
        });

        // ==========================================
        // SỰ KIỆN: MỞ FORM BÁO CÁO DOANH THU
        // ==========================================
        this.mainView.getBtnReport().addActionListener(e -> {
            try {
                com.NetProject.view.frmReport reportView = new com.NetProject.view.frmReport();
                new com.NetProject.controller.ReportController(reportView);
                reportView.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi mở form Báo Cáo: " + ex.toString(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ==========================================
        // SỰ KIỆN: MỞ FORM QUẢN LÝ DỊCH VỤ (MENU F&B) - Đã được thêm vào đây!
        // ==========================================
        this.mainView.getBtnManageMenu().addActionListener(e -> {
            try {
                com.NetProject.view.frmMenu menuView = new com.NetProject.view.frmMenu();
                new com.NetProject.controller.MenuController(menuView);
                menuView.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi mở form Dịch vụ: " + ex.toString(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ==========================================
        // SỰ KIỆN: MỞ FORM QUẢN LÝ NHÂN VIÊN (CHỈ DÀNH CHO ADMIN)
        // ==========================================
        this.mainView.getBtnManageEmployee().addActionListener(e -> {

            // 🛡️ LỚP KHIÊN BẢO VỆ: KIỂM TRA QUYỀN TRUY CẬP
            if (!loggedInUser.getRole().equalsIgnoreCase("Admin")) {
                JOptionPane.showMessageDialog(null,
                        "TRUY CẬP BỊ TỪ CHỐI!\nBạn đang đăng nhập bằng tài khoản Thu Ngân (Employee).\nChỉ có Quản lý (Admin) mới có quyền truy cập khu vực này.",
                        "Cảnh Báo Quyền Hạn",
                        JOptionPane.WARNING_MESSAGE);
                return; // Đá văng ra ngoài, không cho chạy code mở form bên dưới
            }

            // Nếu vượt qua được lớp khiên trên (là Admin) thì mở form
            try {
                com.NetProject.view.frmEmployee empView = new com.NetProject.view.frmEmployee();
                new com.NetProject.controller.EmployeeController(empView);
                empView.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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
                    // ==========================================
                    // TRƯỜNG HỢP 1: MÁY TRỐNG (MÀU XANH) -> MỞ MÁY
                    // ==========================================
                    if (clickedPc.getStatus().equalsIgnoreCase("Available")) {

                        String[] openOptions = {"🚶 Khách Vãng Lai (Mở ngay)", "👑 Khách Hội Viên (Đăng nhập)", "Hủy"};
                        int openChoice = JOptionPane.showOptionDialog(
                                null,
                                "Chọn hình thức mở máy [" + clickedPc.getComputerName() + "]:",
                                "Khởi động máy trạm",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                openOptions,
                                openOptions[0]
                        );

                        if (openChoice == 0) {
                            // 1. Khách vãng lai: Thu ngân mở máy
                            boolean success = computerService.openComputer(computerId, loggedInUser.getAccountId());
                            if (success) {
                                loadComputerMap();
                            } else {
                                JOptionPane.showMessageDialog(null, "Lỗi khi mở máy!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                            }

                        } else if (openChoice == 1) {
                            // 2. Khách hội viên: Giả lập đăng nhập máy trạm
                            JTextField txtUser = new JTextField();
                            JPasswordField txtPass = new JPasswordField();
                            Object[] loginForm = {
                                    "Tài khoản Hội viên:", txtUser,
                                    "Mật khẩu:", txtPass
                            };

                            int loginResult = JOptionPane.showConfirmDialog(null, loginForm,
                                    "MÁY TRẠM: " + clickedPc.getComputerName() + " - Khách Đăng Nhập",
                                    JOptionPane.OK_CANCEL_OPTION);

                            if (loginResult == JOptionPane.OK_OPTION) {
                                String mUser = txtUser.getText();
                                String mPass = new String(txtPass.getPassword());

                                AccountService accService = new AccountService();
                                AccountDTO memberAcc = accService.login(mUser, mPass);

                                if (memberAcc != null && memberAcc.getRole().equalsIgnoreCase("Member")) {
                                    boolean success = computerService.openComputer(computerId, memberAcc.getAccountId());
                                    if (success) {
                                        JOptionPane.showMessageDialog(null, "Hội viên [" + memberAcc.getUsername() + "] đã đăng nhập thành công vào máy!");
                                        loadComputerMap();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Lỗi hệ thống khi khởi tạo phiên chơi!", "Thất bại", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Đăng nhập máy trạm thất bại!\nSai tài khoản hoặc bạn không phải là Hội viên.", "Từ chối", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }

                        // ==========================================
                        // TRƯỜNG HỢP 2: MÁY ĐANG SỬ DỤNG (MÀU ĐỎ) -> MENU DỊCH VỤ
                        // ==========================================
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
                            // CHỌN 1: KHÁCH GỌI ĐỒ ĂN (GHI NỢ)
                            com.NetProject.view.frmOrder orderView = new com.NetProject.view.frmOrder();
                            new com.NetProject.controller.OrderController(orderView, loggedInUser, computerId);
                            orderView.setVisible(true);

                        } else if (choice == 1) {
                            // CHỌN 2: TÍNH TIỀN (GỘP BILL) VÀ ĐÓNG MÁY
                            int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "Xác nhận TÍNH TIỀN và ĐÓNG MÁY [" + clickedPc.getComputerName() + "]?",
                                    "Thanh toán",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (confirm == JOptionPane.YES_OPTION) {

                                // --- BẮT ĐẦU: TRUY XUẤT TÊN THẬT NHÂN VIÊN ---
                                String realName = loggedInUser.getUsername(); // Mặc định là Username

                                try {
                                    com.NetProject.dao.EmployeeDAO empDAO = new com.NetProject.dao.EmployeeDAO();

                                    // Lấy danh sách toàn bộ nhân viên
                                    java.util.List<com.NetProject.entity.Employee> allEmps = empDAO.findAll();

                                    if (allEmps != null) {
                                        for (com.NetProject.entity.Employee emp : allEmps) {
                                            // Dò xem nhân viên nào đang liên kết với Account đang đăng nhập
                                            if (emp.getAccount() != null && emp.getAccount().getAccountId().equals(loggedInUser.getAccountId())) {
                                                realName = emp.getEmployeeName(); // Lôi tên thật ra!
                                                break; // Tìm thấy rồi thì thoát vòng lặp cho nhẹ máy
                                            }
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                // Ghép Tên thật và Chức vụ
                                String cashierName = realName + " (" + loggedInUser.getRole() + ")";

                                String resultMessage = computerService.closeComputer(computerId, cashierName);
                                // --- KẾT THÚC ---

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