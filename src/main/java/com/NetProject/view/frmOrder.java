package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmOrder extends JFrame {
    // Các thành phần giao diện
    private JTable tblMenu;
    private DefaultTableModel menuModel;

    private JTable tblCart;
    private DefaultTableModel cartModel;

    private JButton btnAddToCart;
    private JButton btnRemoveItem;
    private JButton btnCheckout;
    private JLabel lblTotalAmount;

    public frmOrder() {
        setTitle("Dịch Vụ Ăn Uống - Bán Hàng");
        setSize(1000, 600); // Đã mở rộng bề ngang ra 1000px cho thoáng
        setLocationRelativeTo(null); // Giữa màn hình
        setLayout(new BorderLayout(10, 10));

        // Padding cho toàn bộ Frame
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // PANEL BÊN TRÁI: DANH SÁCH THỰC ĐƠN
        JPanel pnlMenu = new JPanel(new BorderLayout(5, 5));
        pnlMenu.setBorder(BorderFactory.createTitledBorder("Thực Đơn (Menu)"));
        pnlMenu.setPreferredSize(new Dimension(450, 0));

        // Bảng chứa danh sách món
        String[] menuCols = {"Mã Món", "Tên Món", "Đơn Giá", "Tồn Kho"};
        menuModel = new DefaultTableModel(menuCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblMenu = new JTable(menuModel);
        tblMenu.setRowHeight(25);
        pnlMenu.add(new JScrollPane(tblMenu), BorderLayout.CENTER);

        // Nút Thêm vào giỏ
        btnAddToCart = new JButton("Thêm Vào Giỏ Hàng >>");
        btnAddToCart.setBackground(new Color(46, 204, 113));
        btnAddToCart.setForeground(Color.WHITE);
        btnAddToCart.setFont(new Font("Arial", Font.BOLD, 14));
        pnlMenu.add(btnAddToCart, BorderLayout.SOUTH);

        add(pnlMenu, BorderLayout.WEST);


        // PANEL BÊN PHẢI: GIỎ HÀNG
        JPanel pnlCart = new JPanel(new BorderLayout(5, 5));
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ Hàng Của Khách"));

        // Bảng chứa giỏ hàng
        String[] cartCols = {"Tên Món", "Số Lượng", "Đơn Giá", "Thành Tiền", "Mã Món"};
        cartModel = new DefaultTableModel(cartCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCart = new JTable(cartModel);
        tblCart.setRowHeight(25);
        // Ẩn cột "Mã Món" đi cho đẹp
        tblCart.getColumnModel().getColumn(4).setMinWidth(0);
        tblCart.getColumnModel().getColumn(4).setMaxWidth(0);

        pnlCart.add(new JScrollPane(tblCart), BorderLayout.CENTER);
        // KHU VỰC TỔNG TIỀN VÀ THANH TOÁN
        JPanel pnlCheckout = new JPanel(new BorderLayout(5, 5));
        pnlCheckout.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // 1. Tổng tiền
        lblTotalAmount = new JLabel("Tổng Tiền: 0 VNĐ");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 22));
        lblTotalAmount.setForeground(new Color(192, 57, 43));
        lblTotalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        pnlCheckout.add(lblTotalAmount, BorderLayout.NORTH);

        // 2. Hai nút bấm xóa món và thanh toán
        JPanel pnlButtons = new JPanel(new BorderLayout());
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnRemoveItem = new JButton("Xóa Món Chọn");
        btnRemoveItem.setBackground(new Color(231, 76, 60));
        btnRemoveItem.setForeground(Color.WHITE);
        btnRemoveItem.setPreferredSize(new Dimension(140, 45));
        pnlButtons.add(btnRemoveItem, BorderLayout.WEST);

        btnCheckout = new JButton("THANH TOÁN");
        btnCheckout.setBackground(new Color(41, 128, 185));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 16));
        btnCheckout.setPreferredSize(new Dimension(160, 45));
        pnlButtons.add(btnCheckout, BorderLayout.EAST);

        pnlCheckout.add(pnlButtons, BorderLayout.CENTER);

        // Lắp khu checkout vào dưới cùng của panel giỏ hàng
        pnlCart.add(pnlCheckout, BorderLayout.SOUTH);
        add(pnlCart, BorderLayout.CENTER);
    }

    // CÁC HÀM GETTER
    public JTable getTblMenu() { return tblMenu; }
    public DefaultTableModel getMenuModel() { return menuModel; }

    public JTable getTblCart() { return tblCart; }
    public DefaultTableModel getCartModel() { return cartModel; }

    public JButton getBtnAddToCart() { return btnAddToCart; }
    public JButton getBtnRemoveItem() { return btnRemoveItem; }
    public JButton getBtnCheckout() { return btnCheckout; }

    public void setTotalAmountText(String text) { lblTotalAmount.setText(text); }

    public void showMessage(String msg, boolean isError) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo",
                isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }
}