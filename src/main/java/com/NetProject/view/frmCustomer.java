package com.NetProject.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class frmCustomer extends JFrame {
    private JTextField txtAccountId;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtBalance;

    // 1. Khai báo thêm btnTopUp ở đây
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnTopUp;

    private JTable tblMembers;
    private DefaultTableModel tableModel;

    public frmCustomer() {
        setTitle("Quản Lý Hội Viên");
        setSize(750, 500); // Tăng chiều ngang một chút để đủ chỗ chứa 5 nút
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==========================================
        // KHU VỰC NHẬP LIỆU (TOP)
        // ==========================================
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Hội viên"));

        pnlInput.add(new JLabel("Mã Hội Viên:"));
        txtAccountId = new JTextField();
        txtAccountId.setEditable(false);
        pnlInput.add(txtAccountId);

        pnlInput.add(new JLabel("Tên Đăng Nhập:"));
        txtUsername = new JTextField();
        pnlInput.add(txtUsername);

        pnlInput.add(new JLabel("Mật Khẩu:"));
        txtPassword = new JPasswordField();
        pnlInput.add(txtPassword);

        pnlInput.add(new JLabel("Số Dư (VNĐ):"));
        txtBalance = new JTextField("0");
        txtBalance.setEditable(false);
        pnlInput.add(txtBalance);

        add(pnlInput, BorderLayout.NORTH);

        // ==========================================
        // KHU VỰC NÚT CHỨC NĂNG (CENTER)
        // ==========================================
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAdd = new JButton("Thêm Mới");
        btnUpdate = new JButton("Sửa Mật Khẩu");
        btnDelete = new JButton("Xóa Hội Viên");
        btnClear = new JButton("Làm Mới Form");

        // Khởi tạo Nút Nạp Tiền
        btnTopUp = new JButton("💰 Nạp Tiền");
        btnTopUp.setBackground(new Color(241, 196, 15)); // Màu vàng cam nổi bật
        btnTopUp.setFont(new Font("Arial", Font.BOLD, 12));

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);
        pnlButtons.add(btnTopUp); // Nhúng nút vào Panel

        add(pnlButtons, BorderLayout.CENTER);

        // ==========================================
        // KHU VỰC BẢNG DANH SÁCH (BOTTOM)
        // ==========================================
        String[] cols = {"Mã Hội Viên", "Tên Đăng Nhập", "Mật Khẩu", "Số Dư (VNĐ)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblMembers = new JTable(tableModel);
        tblMembers.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tblMembers);
        scrollPane.setPreferredSize(new Dimension(730, 250));
        add(scrollPane, BorderLayout.SOUTH);
    }

    // Các Getter cho Controller
    public JTextField getTxtAccountId() { return txtAccountId; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JTextField getTxtBalance() { return txtBalance; }

    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }

    // 2. Hàm Getter cho btnTopUp để Controller có thể "nhìn thấy"
    public JButton getBtnTopUp() { return btnTopUp; }

    public JTable getTblMembers() { return tblMembers; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void showMessage(String msg, boolean isError) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }
}