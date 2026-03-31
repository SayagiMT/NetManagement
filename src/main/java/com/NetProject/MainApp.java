package com.NetProject;

import com.NetProject.controller.LoginController;
import com.NetProject.service.AccountService;
import com.NetProject.view.frmLogin;

public class MainApp {
    public static void main(String[] args) {
        // 1. Khởi tạo form Đăng nhập và Service
        frmLogin loginView = new frmLogin();
        AccountService accountService = new AccountService();

        // 2. Gắn Controller
        new LoginController(loginView, accountService);

        // 3. CHỈ hiển thị duy nhất màn hình Đăng nhập lúc phần mềm khởi động
        loginView.setVisible(true);
    }
}