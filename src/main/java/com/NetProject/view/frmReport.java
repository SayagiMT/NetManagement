package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmReport extends JFrame {
    private JTable tblRevenue;
    private DefaultTableModel model;
    private JLabel lblTotalRevenue;

    public frmReport() {
        setTitle("Báo Cáo Doanh Thu Thực Tế");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Tiêu đề
        JPanel pnlHeader = new JPanel();
        pnlHeader.add(new JLabel("<html><h2 style='color:#2c3e50'>THỐNG KÊ DOANH THU THEO NGÀY</h2></html>"));
        add(pnlHeader, BorderLayout.NORTH);

        // 2. KHU VỰC QUAN TRỌNG: KHỞI TẠO BẢNG VÀ KHÓA CHỈNH SỬA
        String[] cols = {"Ngày", "Tiền nạp Hội viên", "Tiền mặt", "TỔNG DOANH THU NGÀY"};

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khóa chết không cho gõ vào bảng
            }
        };

        tblRevenue = new JTable(model);
        tblRevenue.setRowHeight(25);
        tblRevenue.setFont(new Font("Arial", Font.PLAIN, 14));

        // 3. PHẢI CÓ DÒNG NÀY THÌ BẢNG MỚI HIỆN LÊN
        JScrollPane scrollPane = new JScrollPane(tblRevenue);
        add(scrollPane, BorderLayout.CENTER); // Dán bảng vào giữa màn hình

        // 4. Footer hiển thị tổng tiền
        lblTotalRevenue = new JLabel("TỔNG DOANH THU HỆ THỐNG: 0 VNĐ", SwingConstants.RIGHT);
        lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalRevenue.setForeground(new Color(192, 57, 43));
        lblTotalRevenue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotalRevenue, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModel() { return model; }
    public JLabel getLblTotalRevenue() { return lblTotalRevenue; }
}