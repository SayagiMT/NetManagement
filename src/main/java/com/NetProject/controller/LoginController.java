package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.service.AccountService;
import com.NetProject.view.frmLogin;
import com.NetProject.view.frmMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private frmLogin view;
    private AccountService service;

    public LoginController(frmLogin view, AccountService service) {
        this.view = view;
        this.service = service;

        // Gắn sự kiện cho nút bấm
        this.view.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String user = view.getUsername();
        String pass = view.getPassword();

        // Gọi Service xử lý
        AccountDTO account = service.login(user, pass);

        if (account != null) {

            // Kiểm tra phân quyền: Chặn Member đăng nhập vào phần mềm của Thu ngân
            if (account.getRole().equalsIgnoreCase("Member")) {
                view.showMessage("Truy cập bị từ chối!\nTài khoản Hội viên không được phép dùng phần mềm Quản lý.");
                return; // Dừng lại ngay lập tức
            }

            view.showMessage("Đăng nhập thành công! Chào " + account.getRole());
            view.dispose(); // Đóng form login

            // Mở giao diện Main
            com.NetProject.view.frmMain mainForm = new com.NetProject.view.frmMain();
            new com.NetProject.controller.MainController(mainForm, account);
            mainForm.setVisible(true);
        } else {
            view.showMessage("Sai tài khoản hoặc mật khẩu!");
        }
    }
}