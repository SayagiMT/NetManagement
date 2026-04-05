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
        // 1. THANH HEADER (PHÍA TRÊN)
        // ==========================================
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(41, 128, 185)); // Xanh dương đậm
        pnlHeader.setPreferredSize(new Dimension(1100, 60));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblCyberName = new JLabel("CYBER GAMING PRO");
        lblCyberName.setFont(new Font("Arial", Font.BOLD, 22));
        lblCyberName.setForeground(Color.WHITE);
        pnlHeader.add(lblCyberName, BorderLayout.WEST);

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

        btnToggleMenu = createSidebarButton(" MENU", new Color(20, 20, 20), false);
        btnToggleMenu.setIcon(createLargeMenuIcon(32, 32));
        btnToggleMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnToggleMenu.setIconTextGap(10);

        btnManageMembers = createSidebarButton("Quản Lí Hội Viên", new Color(40, 40, 40), true);
        btnManageMenu = createSidebarButton("Quản Lí Dịch Vụ", new Color(40, 40, 40), true);
        btnManageEmployee = createSidebarButton("Quản Lí Nhân Sự", new Color(40, 40, 40), true);
        btnReport = createSidebarButton("Thống Kê Doanh Thu", new Color(40, 40, 40), true);

        btnLogout = createSidebarButton(" Đăng Xuất", new Color(40, 40, 40), true);
        btnLogout.setForeground(new Color(255, 100, 100));

        pnlSidebar.add(btnToggleMenu);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        pnlSidebar.add(btnManageMembers);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlSidebar.add(btnManageMenu);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlSidebar.add(btnManageEmployee);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlSidebar.add(btnReport);
        pnlSidebar.add(Box.createVerticalGlue());
        pnlSidebar.add(btnLogout);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 20)));

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
                pnlSidebar.setPreferredSize(new Dimension(55, 0));
                hideButtonText();
            }
            pnlSidebar.revalidate();
            pnlSidebar.repaint();
        });
    }

    public void setWelcomeText(String text) {
        setTitle("Phần Mềm Quản Lý Tiệm Net - Cyber Pro | " + text);
    }

    // ==================================================
    // HÀM VẼ SƠ ĐỒ MÁY TÍNH (THUẬT TOÁN XẾP GẠCH KHÍT 100%)
    // ==================================================
    public void drawComputerMap(List<ComputerDTO> computers, ActionListener onPcClick) {
        pnlComputerMap.removeAll();

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        Map<String, JPanel> zonePanels = new HashMap<>();

        for (ComputerDTO pc : computers) {
            String zoneName = pc.getZoneName();

            if (!zonePanels.containsKey(zoneName)) {
                // Lưới chứa máy tính (Tạm thời để 5 cột, hệ thống sẽ tự đổi lại sau)
                JPanel gridPanel = new JPanel(new GridLayout(0, 5, 15, 15));
                gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                gridPanel.setBackground(Color.WHITE);
                zonePanels.put(zoneName, gridPanel);

                // Lớp vỏ bọc giúp lưới không bị kéo giãn tuột luốt theo chiều dọc
                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBackground(Color.WHITE);
                wrapperPanel.add(gridPanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(wrapperPanel);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Tắt cuộn ngang
                scrollPane.setBorder(null);

                tabbedPane.addTab("   " + zoneName + "   ", scrollPane);

                // ==========================================
                // MA THUẬT NẰM Ở ĐÂY: Lắng nghe kích thước màn hình
                // ==========================================
                scrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
                    @Override
                    public void componentResized(java.awt.event.ComponentEvent e) {
                        int viewWidth = scrollPane.getViewport().getWidth();
                        if (viewWidth <= 0) return;

                        int padding = 40; // Lề trái phải
                        int gap = 15;     // Khe hở giữa các máy
                        int minSize = 130; // Kích thước máy tối thiểu

                        // 1. Tự động tính xem nhét được bao nhiêu cột thì khít
                        int cols = Math.max(1, (viewWidth - padding + gap) / (minSize + gap));

                        // 2. Chỉnh lại số cột của Lưới
                        GridLayout layout = (GridLayout) gridPanel.getLayout();
                        layout.setColumns(cols);

                        // 3. Tính toán kích thước Hình Vuông hoàn hảo nhất
                        int exactSize = (viewWidth - padding - (cols - 1) * gap) / cols;

                        // 4. Ép tất cả các nút thành hình vuông khít rịt
                        for (Component c : gridPanel.getComponents()) {
                            c.setPreferredSize(new Dimension(exactSize, exactSize));
                        }

                        gridPanel.revalidate();
                    }
                });
            }

            // Tạo Nút (KHÔNG CẦN CÁI VỎ TÀNG HÌNH NỮA VÌ ĐÃ CÓ LƯỚI BẢO KÊ)
            JButton btnPC = new JButton();
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

            // Ném thẳng nút vào Grid (Cực kỳ sạch sẽ)
            zonePanels.get(zoneName).add(btnPC);
        }

        pnlComputerMap.add(tabbedPane, BorderLayout.CENTER);
        pnlComputerMap.revalidate();
        pnlComputerMap.repaint();
    }

    // Các hàm Getter
    public JButton getBtnManageMembers() { return btnManageMembers; }
    public JButton getBtnReport() { return btnReport; }
    public JButton getBtnManageMenu() { return btnManageMenu; }
    public JButton getBtnManageEmployee() { return btnManageEmployee; }
    public JButton getBtnLogout() { return btnLogout; }

    private JButton createSidebarButton(String text) {
        return createSidebarButton(text, new Color(40, 40, 40), true);
    }

    private JButton createSidebarButton(String text, Color bgColor, boolean hasFrame) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (hasFrame) {
            javax.swing.border.Border line = BorderFactory.createLineBorder(new Color(100, 100, 100), 1, true);
            javax.swing.border.Border padding = BorderFactory.createEmptyBorder(5, 15, 5, 10);
            btn.setBorder(BorderFactory.createCompoundBorder(line, padding));
            btn.setBorderPainted(true);
        } else {
            btn.setBorderPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        return btn;
    }

    private void hideButtonText() {
        btnToggleMenu.setText("");
        btnManageMembers.setVisible(false);
        btnManageMenu.setVisible(false);
        btnManageEmployee.setVisible(false);
        btnReport.setVisible(false);
        btnLogout.setVisible(false);
    }

    private void showButtonText() {
        btnToggleMenu.setText(" MENU");
        btnManageMembers.setVisible(true);
        btnManageMenu.setVisible(true);
        btnManageEmployee.setVisible(true);
        btnReport.setVisible(true);
        btnLogout.setVisible(true);
    }

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