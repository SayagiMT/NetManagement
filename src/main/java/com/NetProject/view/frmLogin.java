package com.NetProject.view;

import javax.swing.*;
import java.awt.*;

public class frmLogin extends JFrame {
    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public frmLogin() {
        // 1. Cấu hình cơ bản cho Frame
        setTitle("Đăng Nhập Hệ Thống Quản Lý Tiệm Net");
        setSize(400, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 2. Khởi tạo mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));
        setContentPane(mainPanel);

        // 3. Tạo tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBounds(0, 15, 400, 30);
        mainPanel.add(lblTitle);

        // 4. Khởi tạo các nhãn và ô nhập liệu
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Arial", Font.BOLD, 13));
        lblUser.setBounds(50, 70, 80, 25);
        mainPanel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(140, 70, 180, 25);
        mainPanel.add(txtUsername);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Arial", Font.BOLD, 13));
        lblPass.setBounds(50, 110, 80, 25);
        mainPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 110, 180, 25);
        mainPanel.add(txtPassword);

        // 5. Khởi tạo nút Đăng Nhập
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBounds(140, 150, 120, 35);
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnLogin);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}