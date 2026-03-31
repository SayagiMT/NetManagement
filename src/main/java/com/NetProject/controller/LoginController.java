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
            view.showMessage("Đăng nhập thành công! Chào " + account.getRole());

            // 1. Đóng form Login hiện tại đi
            view.dispose();

            // 2. MỞ FORM TIẾP THEO LÊN (Ví dụ: mở form Sơ đồ máy - frmMain)
            // (Nếu bạn muốn test form Order thì đổi frmMain thành frmOrder nhé)
            com.NetProject.view.frmMain mainForm = new com.NetProject.view.frmMain();
            new com.NetProject.controller.MainController(mainForm, account);
            mainForm.setVisible(true);

        } else {
            view.showMessage("Sai tài khoản hoặc mật khẩu!");
        }
    }
}