package com.NetProject;

import com.NetProject.controller.LoginController;
import com.NetProject.service.AccountService;
import com.NetProject.view.frmLogin;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // 1. KÍCH HOẠT GIAO DIỆN FLATLAF
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Không thể khởi tạo giao diện FlatLaf");
        }


        // 2. KHỞI CHẠY PHẦN MỀM
        // Bắt buộc phải chạy trong EventQueue để giao diện mượt mà và không bị kẹt luồng
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Khởi tạo màn hình giao diện (View)
                frmLogin loginView = new frmLogin();

                // Khởi tạo Service xử lý dữ liệu
                AccountService accountService = new AccountService();

                // Khởi tạo bộ xử lý sự kiện
                new LoginController(loginView, accountService);

                // Căn giữa màn hình và hiển thị
                loginView.setLocationRelativeTo(null);
                loginView.setVisible(true);
            }
        });
    }
}