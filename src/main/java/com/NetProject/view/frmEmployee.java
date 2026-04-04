package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmEmployee extends JFrame {
    private JTextField txtId, txtRealName, txtUsername, txtPassword;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnDelete, btnClear;
    private JTable tblEmployee;
    private DefaultTableModel model;

    public frmEmployee() {
        setTitle("Quản Lý Nhân Sự (Dành riêng cho Admin)");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel Nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Tài khoản & Nhân sự"));

        pnlInput.add(new JLabel("Mã NV:"));
        txtId = new JTextField(); txtId.setEditable(false);
        pnlInput.add(txtId);

        pnlInput.add(new JLabel("Tên thật (In Bill):"));
        txtRealName = new JTextField();
        pnlInput.add(txtRealName);

        pnlInput.add(new JLabel("Tài khoản Đăng nhập:"));
        txtUsername = new JTextField();
        pnlInput.add(txtUsername);

        pnlInput.add(new JLabel("Mật khẩu:"));
        txtPassword = new JTextField();
        pnlInput.add(txtPassword);

        pnlInput.add(new JLabel("Cấp quyền:"));
        cbRole = new JComboBox<>(new String[]{"Employee", "Admin"});
        pnlInput.add(cbRole);

        add(pnlInput, BorderLayout.NORTH);

        // 2. Bảng hiển thị
        String[] cols = {"Mã NV", "Tên Thật", "Tài Khoản", "Mật Khẩu", "Quyền Hạn"};
        model = new DefaultTableModel(cols, 0);
        tblEmployee = new JTable(model);
        add(new JScrollPane(tblEmployee), BorderLayout.CENTER);

        // 3. Panel Nút bấm
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm Nhân Viên");
        btnDelete = new JButton("Sa thải (Xóa)");
        btnClear = new JButton("Làm mới");

        pnlBtns.add(btnAdd); pnlBtns.add(btnDelete); pnlBtns.add(btnClear);
        add(pnlBtns, BorderLayout.SOUTH);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtRealName() { return txtRealName; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JTextField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCbRole() { return cbRole; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTblEmployee() { return tblEmployee; }
    public DefaultTableModel getModel() { return model; }
}