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
        setTitle("Dịch Vụ Ăn Uống - Bán Hàng (POS)");
        setSize(900, 600);
        setLocationRelativeTo(null); // Giữa màn hình
        setLayout(new BorderLayout(10, 10));

        // Padding cho toàn bộ Frame
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==========================================
        // PANEL BÊN TRÁI: DANH SÁCH THỰC ĐƠN (MENU)
        // ==========================================
        JPanel pnlMenu = new JPanel(new BorderLayout(5, 5));
        pnlMenu.setBorder(BorderFactory.createTitledBorder("Thực Đơn (Menu)"));
        pnlMenu.setPreferredSize(new Dimension(450, 0));

        // Bảng chứa danh sách món
        String[] menuCols = {"Mã Món", "Tên Món", "Đơn Giá", "Tồn Kho"};
        menuModel = new DefaultTableModel(menuCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Khóa không cho sửa trực tiếp
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

        // ==========================================
        // PANEL BÊN PHẢI: GIỎ HÀNG (CART)
        // ==========================================
        JPanel pnlCart = new JPanel(new BorderLayout(5, 5));
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ Hàng Của Khách"));

        // Bảng chứa giỏ hàng
        String[] cartCols = {"Tên Món", "Số Lượng", "Đơn Giá", "Thành Tiền", "Mã Món"}; // Mã món ẩn đi để xử lý ngầm
        cartModel = new DefaultTableModel(cartCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCart = new JTable(cartModel);
        tblCart.setRowHeight(25);
        // Ẩn cột "Mã Món" đi cho đẹp, chỉ dùng để lấy data ngầm
        tblCart.getColumnModel().getColumn(4).setMinWidth(0);
        tblCart.getColumnModel().getColumn(4).setMaxWidth(0);

        pnlCart.add(new JScrollPane(tblCart), BorderLayout.CENTER);

        // Khu vực Tổng tiền và Thanh toán
        JPanel pnlCheckout = new JPanel(new BorderLayout(5, 5));

        btnRemoveItem = new JButton("Xóa Món Chọn");
        btnRemoveItem.setBackground(new Color(231, 76, 60));
        btnRemoveItem.setForeground(Color.WHITE);
        pnlCheckout.add(btnRemoveItem, BorderLayout.WEST);

        lblTotalAmount = new JLabel("Tổng Tiền: 0 VNĐ");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalAmount.setForeground(new Color(192, 57, 43));
        lblTotalAmount.setHorizontalAlignment(SwingConstants.CENTER);
        pnlCheckout.add(lblTotalAmount, BorderLayout.CENTER);

        btnCheckout = new JButton("THANH TOÁN");
        btnCheckout.setBackground(new Color(41, 128, 185));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 16));
        btnCheckout.setPreferredSize(new Dimension(150, 40));
        pnlCheckout.add(btnCheckout, BorderLayout.EAST);

        pnlCart.add(pnlCheckout, BorderLayout.SOUTH);

        add(pnlCart, BorderLayout.CENTER);
    }

    // =========================================================
    // CÁC HÀM GETTER ĐỂ CONTROLLER LẤY DỮ LIỆU VÀ TƯƠNG TÁC
    // =========================================================
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