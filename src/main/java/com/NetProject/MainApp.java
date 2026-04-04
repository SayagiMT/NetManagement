package com.NetProject;

import com.NetProject.controller.LoginController;
import com.NetProject.service.AccountService;
import com.NetProject.view.frmLogin;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // ==========================================
        // 1. KÍCH HOẠT GIAO DIỆN FLATLAF (Light Mode)
        // ==========================================
        try {
            // Sử dụng FlatLightLaf để có giao diện phẳng, hiện đại và sạch sẽ
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            // Nếu thích tông tối ngầu lòi, bạn dùng FlatDarkLaf() thay thế
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Không thể khởi tạo giao diện FlatLaf");
        }

        // ==========================================
        // 2. KHỞI CHẠY PHẦN MỀM (MÔ HÌNH MVC)
        // ==========================================
        // Bắt buộc phải chạy trong EventQueue để giao diện mượt mà và không bị kẹt luồng
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Khởi tạo màn hình giao diện (View)
                frmLogin loginView = new frmLogin();

                // Khởi tạo Service xử lý dữ liệu
                AccountService accountService = new AccountService();

                // Khởi tạo bộ xử lý sự kiện (Controller) và truyền ĐỦ 2 tham số: View và Service
                new LoginController(loginView, accountService);

                // Căn giữa màn hình và hiển thị
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);
            }
        });
    }
}