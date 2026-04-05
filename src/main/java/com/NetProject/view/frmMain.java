package com.NetProject.view;

import com.NetProject.dto.ComputerDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class frmMain extends JFrame {
    // --- Header & Info ---
    private JLabel lblCyberName;
    private JLabel lblHotline;
    private JLabel lblAddress;

    // --- Sidebar ---
    private JPanel pnlSidebar;
    private JButton btnToggleMenu;
    private boolean isMenuExpanded = true;

    // --- Nút Chức Năng ---
    private JButton btnManageMembers;
    private JButton btnManageMenu;
    private JButton btnManageEmployee;
    private JButton btnReport;
    private JButton btnLogout;

    // --- Content ---
    private JPanel pnlComputerMap;
    private JTabbedPane tabbedPane;

    public frmMain() {
        setTitle("Phần Mềm Quản Lý Tiệm Net - Cyber Pro");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. THANH HEADER (PHÍA TRÊN) - CHỨA THÔNG TIN QUÁN
        // ==========================================
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(41, 128, 185)); // Xanh dương đậm
        pnlHeader.setPreferredSize(new Dimension(1100, 60));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Tên Quán (Bên trái)
        lblCyberName = new JLabel("CYBER GAMING PRO");
        lblCyberName.setFont(new Font("Arial", Font.BOLD, 22));
        lblCyberName.setForeground(Color.WHITE);
        pnlHeader.add(lblCyberName, BorderLayout.WEST);

        // Thông tin liên hệ (Bên phải)
        JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
        pnlInfo.setOpaque(false);

        lblHotline = new JLabel("Hotline: 0987.654.321", SwingConstants.RIGHT);
        lblHotline.setFont(new Font("Arial", Font.BOLD, 14));
        lblHotline.setForeground(Color.ORANGE);

        lblAddress = new JLabel("Địa chỉ: 123 Đường Víp, TP.HCM", SwingConstants.RIGHT);
        lblAddress.setFont(new Font("Arial", Font.ITALIC, 12));
        lblAddress.setForeground(Color.WHITE);

        pnlInfo.add(lblHotline);
        pnlInfo.add(lblAddress);
        pnlHeader.add(pnlInfo, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // ==========================================
        // 2. THANH SIDEBAR (BÊN TRÁI)
        // ==========================================
        pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(new Color(40, 40, 40));
        pnlSidebar.setPreferredSize(new Dimension(200, 0));

        // Nút Hamburger (3 gạch) - Không đóng khung
        btnToggleMenu = createSidebarButton(" MENU", new Color(20, 20, 20), false);
        btnToggleMenu.setIcon(createLargeMenuIcon(32, 32));
        btnToggleMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnToggleMenu.setIconTextGap(10);

        // Khởi tạo các nút chức năng (Bật tính năng Đóng Khung = true)
        btnManageMembers = createSidebarButton(" Hội Viên", new Color(40, 40, 40), true);
        btnManageMenu = createSidebarButton(" Dịch Vụ (F&B)", new Color(40, 40, 40), true);
        btnManageEmployee = createSidebarButton(" Nhân Sự", new Color(40, 40, 40), true);
        btnReport = createSidebarButton(" Doanh Thu", new Color(40, 40, 40), true);

        btnLogout = createSidebarButton(" Đăng Xuất", new Color(40, 40, 40), true);
        btnLogout.setForeground(new Color(255, 100, 100)); // Đỏ sáng hơn chút cho dễ nhìn trên nền tối

        // Gắn vào Sidebar kèm các khoảng trống (Margin)
        pnlSidebar.add(btnToggleMenu);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 30))); // Khoảng trống lớn dưới nút Menu

        pnlSidebar.add(btnManageMembers);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15))); // Tách nút 15px

        pnlSidebar.add(btnManageMenu);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15))); // Tách nút 15px

        pnlSidebar.add(btnManageEmployee);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15))); // Tách nút 15px

        pnlSidebar.add(btnReport);

        pnlSidebar.add(Box.createVerticalGlue()); // Đẩy nút Đăng xuất xuống tít dưới đáy

        pnlSidebar.add(btnLogout);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Cách đáy 20px

        add(pnlSidebar, BorderLayout.WEST);

        // ==========================================
        // 3. KHU VỰC SƠ ĐỒ MÁY CHÍNH (Ở GIỮA)
        // ==========================================
        pnlComputerMap = new JPanel(new BorderLayout());
        pnlComputerMap.setBackground(Color.WHITE);
        add(pnlComputerMap, BorderLayout.CENTER);

        // ==========================================
        // 4. SỰ KIỆN THU/PHÓNG SIDEBAR
        // ==========================================
        btnToggleMenu.addActionListener(e -> {
            isMenuExpanded = !isMenuExpanded;
            if (isMenuExpanded) {
                pnlSidebar.setPreferredSize(new Dimension(200, 0));
                showButtonText();
            } else {
                pnlSidebar.setPreferredSize(new Dimension(55, 0)); // Thu nhỏ lại chỉ chừa icon
                hideButtonText();
            }
            pnlSidebar.revalidate();
            pnlSidebar.repaint();
        });
    }

    // ==========================================
    // CÁC HÀM TIỆN ÍCH TẠO NÚT (ĐÃ FIX LỖI CỦA SẾP)
    // ==========================================
    private JButton createSidebarButton(String text) {
        return createSidebarButton(text, new Color(40, 40, 40), true);
    }

    private JButton createSidebarButton(String text, Color bgColor, boolean hasFrame) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 45)); // Rộng 180 để chừa khoảng trống 2 bên cho đẹp
        btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa thanh sidebar
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (hasFrame) {
            // Tạo một cái khung viền màu xám sáng mỏng 1 pixel, kết hợp đệm lề bên trong
            javax.swing.border.Border line = BorderFactory.createLineBorder(new Color(100, 100, 100), 1, true);
            javax.swing.border.Border padding = BorderFactory.createEmptyBorder(5, 15, 5, 10);
            btn.setBorder(BorderFactory.createCompoundBorder(line, padding));
            btn.setBorderPainted(true);
        } else {
            // Nút Menu 3 gạch thì không cần viền
            btn.setBorderPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        return btn;
    }

    private void hideButtonText() {
        // Chỉ giấu chữ của nút Menu 3 gạch
        btnToggleMenu.setText("");

        // TẮT HẲN CÁC NÚT KHÁC (Mất luôn khung viền, không cho bấm)
        btnManageMembers.setVisible(false);
        btnManageMenu.setVisible(false);
        btnManageEmployee.setVisible(false);
        btnReport.setVisible(false);
        btnLogout.setVisible(false);
    }

    private void showButtonText() {
        // Hiện lại chữ của nút Menu
        btnToggleMenu.setText(" MENU");

        // BẬT HIỂN THỊ LẠI CÁC NÚT CHỨC NĂNG
        btnManageMembers.setVisible(true);
        btnManageMenu.setVisible(true);
        btnManageEmployee.setVisible(true);
        btnReport.setVisible(true);
        btnLogout.setVisible(true);
    }

    // ==================================================
    // HÀM VẼ SƠ ĐỒ MÁY TÍNH
    // ==================================================
    public void drawComputerMap(List<ComputerDTO> computers, ActionListener onPcClick) {
        pnlComputerMap.removeAll();

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        Map<String, JPanel> zonePanels = new HashMap<>();

        for (ComputerDTO pc : computers) {
            String zoneName = pc.getZoneName();

            if (!zonePanels.containsKey(zoneName)) {
                JPanel newPanel = new JPanel(new GridLayout(0, 5, 15, 15));
                newPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                zonePanels.put(zoneName, newPanel);

                JScrollPane scrollPane = new JScrollPane(newPanel);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                tabbedPane.addTab("   " + zoneName + "   ", scrollPane);
            }

            JButton btnPC = new JButton();
            btnPC.setPreferredSize(new Dimension(130, 130));
            btnPC.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String htmlText = "<html><center>"
                    + "<h2 style='margin:0; padding:0;'>" + pc.getComputerName() + "</h2>"
                    + "<br><b>" + pc.getStatus() + "</b>"
                    + "</center></html>";
            btnPC.setText(htmlText);
            btnPC.setActionCommand(pc.getComputerId());

            if (pc.getStatus().equalsIgnoreCase("In Use")) {
                btnPC.setBackground(new Color(231, 76, 60));
                btnPC.setForeground(Color.WHITE);
            } else if (pc.getStatus().equalsIgnoreCase("Available")) {
                btnPC.setBackground(new Color(46, 204, 113));
                btnPC.setForeground(Color.BLACK);
            } else {
                btnPC.setBackground(Color.GRAY);
                btnPC.setForeground(Color.WHITE);
            }

            btnPC.addActionListener(onPcClick);

            JPanel btnWrapper = new JPanel(new GridBagLayout());
            btnWrapper.setOpaque(false);
            btnWrapper.add(btnPC);

            zonePanels.get(zoneName).add(btnWrapper);
        }

        pnlComputerMap.add(tabbedPane, BorderLayout.CENTER);
        pnlComputerMap.revalidate();
        pnlComputerMap.repaint();
    }

    // Các hàm Getter cho Controller
    public JButton getBtnManageMembers() { return btnManageMembers; }
    public JButton getBtnReport() { return btnReport; }
    public JButton getBtnManageMenu() { return btnManageMenu; }
    public JButton getBtnManageEmployee() { return btnManageEmployee; }
    public JButton getBtnLogout() { return btnLogout; }

    // --- HÀM TIỆN ÍCH VẼ ICON MENU 3 GẠCH ---
    private ImageIcon createLargeMenuIcon(int width, int height) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) img.getGraphics();

        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(java.awt.Color.WHITE);

        int padding = 6;
        int lineThickness = 3;

        g2d.fillRoundRect(padding, 8, width - padding * 2, lineThickness, 2, 2);
        g2d.fillRoundRect(padding, 15, width - padding * 2, lineThickness, 2, 2);
        g2d.fillRoundRect(padding, 22, width - padding * 2, lineThickness, 2, 2);

        g2d.dispose();
        return new ImageIcon(img);
    }
}