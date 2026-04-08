package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmMenu extends JFrame {
    private JTextField txtId, txtName, txtPrice, txtStock;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable tblMenu;
    private DefaultTableModel model;

    // Khai báo 2 biến cho ảnh
    private JButton btnUploadImage;
    private JLabel lblImagePreview;

    public frmMenu() {
        setTitle("Quản Lý Thực Đơn");
        setSize(850, 500); // Tăng chiều ngang ra một chút để có chỗ để ảnh
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Padding cho toàn bộ Frame
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //  KHU CHỨA NHẬP LIỆU & ẢNH
        JPanel pnlTop = new JPanel(new BorderLayout(10, 10));

        // 1. Khu vực nhập liệu
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

        pnlTop.add(pnlInput, BorderLayout.CENTER);

        // 2. Khu vực Ảnh minh họa
        JPanel pnlImage = new JPanel(new BorderLayout(5, 5));
        pnlImage.setBorder(BorderFactory.createTitledBorder("Ảnh minh họa"));
        pnlImage.setPreferredSize(new Dimension(160, 0)); // Cố định chiều rộng cho vùng ảnh

        // Khởi tạo Nhãn xem trước ảnh
        lblImagePreview = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(140, 140)); // Khung vuông cho ảnh
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pnlImage.add(lblImagePreview, BorderLayout.CENTER);

        // Khởi tạo Nút Tải ảnh
        btnUploadImage = new JButton("Tải Ảnh Lên");
        pnlImage.add(btnUploadImage, BorderLayout.SOUTH);
        pnlTop.add(pnlImage, BorderLayout.EAST);
        add(pnlTop, BorderLayout.NORTH);


        // BẢNG HIỂN THỊ
        String[] cols = {"Mã Món", "Tên Món", "Đơn Giá", "Tồn Kho"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Khóa không cho sửa trực tiếp trên bảng
        };
        tblMenu = new JTable(model);
        tblMenu.setRowHeight(25);
        add(new JScrollPane(tblMenu), BorderLayout.CENTER);


        // NÚT BẤM
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
    public JButton getBtnUploadImage() { return btnUploadImage; }
    public JLabel getLblImagePreview() { return lblImagePreview; }
}