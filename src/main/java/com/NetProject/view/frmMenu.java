package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmMenu extends JFrame {
    private JTextField txtId, txtName, txtPrice, txtStock;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable tblMenu;
    private DefaultTableModel model;

    public frmMenu() {
        setTitle("Quản Lý Thực Đơn");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Khu vực nhập liệu (TitledBorder giống ảnh mẫu của bạn)
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin món ăn / dịch vụ"));

        pnlInput.add(new JLabel("Mã món:"));
        txtId = new JTextField(); txtId.setEditable(false);
        pnlInput.add(txtId);

        pnlInput.add(new JLabel("Tên món:"));
        txtName = new JTextField();
        pnlInput.add(txtName);

        pnlInput.add(new JLabel("Đơn giá (VNĐ):"));
        txtPrice = new JTextField();
        pnlInput.add(txtPrice);

        pnlInput.add(new JLabel("Tồn kho:"));
        txtStock = new JTextField();
        pnlInput.add(txtStock);

        add(pnlInput, BorderLayout.NORTH);

        // 2. Bảng hiển thị
        String[] cols = {"Mã Món", "Tên Món", "Đơn Giá", "Tồn Kho"};
        model = new DefaultTableModel(cols, 0);
        tblMenu = new JTable(model);
        add(new JScrollPane(tblMenu), BorderLayout.CENTER);

        // 3. Hàng nút bấm phía dưới
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm mới");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa món");
        btnClear = new JButton("Làm mới");

        pnlBtns.add(btnAdd); pnlBtns.add(btnUpdate);
        pnlBtns.add(btnDelete); pnlBtns.add(btnClear);
        add(pnlBtns, BorderLayout.SOUTH);
    }

    // Getters cho Controller
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtStock() { return txtStock; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTblMenu() { return tblMenu; }
    public DefaultTableModel getModel() { return model; }
}