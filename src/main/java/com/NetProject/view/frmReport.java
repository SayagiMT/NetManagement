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

        // Header
        JPanel pnlHeader = new JPanel();
        pnlHeader.add(new JLabel("<html><h2 style='color:#2c3e50'>THỐNG KÊ DOANH THU THEO NGÀY</h2></html>"));
        add(pnlHeader, BorderLayout.NORTH);

        // Bảng
        String[] cols = {"Ngày", "Tiền nạp Hội viên", "Tiền Khách vãng lai", "TỔNG THU TIỀN MẶT"};
        model = new DefaultTableModel(cols, 0);
        tblRevenue = new JTable(model);
        tblRevenue.setRowHeight(20);
        tblRevenue.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(tblRevenue), BorderLayout.CENTER);

        // Footer hiển thị tổng tất cả các ngày
        lblTotalRevenue = new JLabel("TỔNG DOANH THU: 0 VNĐ", SwingConstants.RIGHT);
        lblTotalRevenue.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalRevenue.setForeground(new Color(192, 57, 43));
        lblTotalRevenue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTotalRevenue, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModel() { return model; }
    public JLabel getLblTotalRevenue() { return lblTotalRevenue; }
}