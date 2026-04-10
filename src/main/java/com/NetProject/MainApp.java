package com.NetProject;

import com.NetProject.controller.LoginController;
import com.NetProject.service.AccountService;
import com.NetProject.service.AccountServiceImp;
import com.NetProject.view.frmLogin;
import com.NetProject.util.HibernateUtil;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // 1. KÍCH HOẠT GIAO DIỆN FLATLAF
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể khởi tạo giao diện FlatLaf, sử dụng mặc định.");
        }

        // 2. KHỞI CHẠY PHẦN MỀM
        java.awt.EventQueue.invokeLater(() -> {
            // Khởi tạo các thành phần
            frmLogin loginView = new frmLogin();
            AccountService accountService = new AccountServiceImp();

            // Kết nối View và Service thông qua Controller
            new LoginController(loginView, accountService);

            // Hiển thị
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });

        // 3. TỰ ĐỘNG ĐÓNG KẾT NỐI KHI TẮT CHƯƠNG TRÌNH
        // Đoạn này giúp giải phóng bộ nhớ và MySQL an toàn
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Đang đóng kết nối Cơ sở dữ liệu...");
            HibernateUtil.shutdown();
        }));
    }
}