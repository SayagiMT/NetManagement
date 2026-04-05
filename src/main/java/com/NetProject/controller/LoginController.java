package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.service.AccountService;
import com.NetProject.view.frmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private frmLogin view;
    private AccountService service;

    public LoginController(frmLogin view, AccountService service) {
        this.view = view;
        this.service = service;

        // Gắn sự kiện cho nút bấm Đăng Nhập
        this.view.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }
    // Hàm xử lí thông tin Login
    private void handleLogin() {
        String user = view.getUsername();
        String pass = view.getPassword();

        // Gọi Service xử lý kiểm tra Database
        AccountDTO account = service.login(user, pass);

        if (account != null) {
            // Kiểm tra phân quyền: Chặn Member đăng nhập vào phần mềm quản lí
            if (account.getRole().equalsIgnoreCase("Member")) {
                view.showMessage("Truy cập bị từ chối!\nTài khoản Hội viên không được phép dùng phần mềm Quản lý.");
                return; // Dừng lại ngay lập tức, không cho đi tiếp
            }

            view.showMessage("Đăng nhập thành công! Chào " + account.getRole());
            view.dispose(); // Đóng form login

            // Mở giao diện Main và truyền thông tin người đăng nhập sang
            com.NetProject.view.frmMain mainForm = new com.NetProject.view.frmMain();
            new com.NetProject.controller.MainController(mainForm, account);
            mainForm.setVisible(true);
        } else {
            view.showMessage("Sai tài khoản hoặc mật khẩu!");
        }
    }
}