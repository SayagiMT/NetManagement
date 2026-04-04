package com.NetProject.view;

import com.NetProject.dto.ComputerDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class frmMain extends JFrame {
    private JLabel lblWelcome;
    private JPanel pnlComputerMap;
    private JButton btnManageMembers;
    private JButton btnReport;
    private JButton btnManageMenu;
    private JButton btnManageEmployee;
    private JButton btnLogout;

    public frmMain() {
        setTitle("Phần Mềm Quản Lý Tiệm Net - Sơ Đồ Phòng Máy");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Header: Thanh điều hướng và Lời chào
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHeader.setBackground(new Color(41, 128, 185));
        pnlHeader.setPreferredSize(new Dimension(1000, 50));

        lblWelcome = new JLabel("Xin chào: ...");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        lblWelcome.setForeground(Color.WHITE);
        pnlHeader.add(lblWelcome);

        // --- NÚT QUẢN LÝ HỘI VIÊN ---
        pnlHeader.add(Box.createHorizontalStrut(50));
        btnManageMembers = new JButton("Quản Lý Hội Viên");
        pnlHeader.add(btnManageMembers);

        // --- NÚT QUẢN LÝ DỊCH VỤ ---
        pnlHeader.add(Box.createHorizontalStrut(10));
        btnManageMenu = new JButton("Quản Lý Dịch Vụ");
        pnlHeader.add(btnManageMenu);

        // --- NÚT QUẢN LÝ NHÂN SỰ ---
        pnlHeader.add(Box.createHorizontalStrut(10));
        btnManageEmployee = new JButton("Quản Lý Nhân Sự");
        pnlHeader.add(btnManageEmployee);

        // --- NÚT BÁO CÁO DOANH THU ---
        pnlHeader.add(Box.createHorizontalStrut(10));
        btnReport = new JButton("Báo Cáo Doanh Thu");
        pnlHeader.add(btnReport);

        // --- NÚT ĐĂNG XUẤT ---
        pnlHeader.add(Box.createHorizontalStrut(10));
        btnLogout = new JButton("Đăng xuất");
        pnlHeader.add(btnLogout);
        // ----------------------------

        add(pnlHeader, BorderLayout.NORTH);

        // 2. Center: Khu vực lưới sơ đồ máy tính
        pnlComputerMap = new JPanel(new GridLayout(0, 5, 15, 15));
        pnlComputerMap.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.add(pnlComputerMap, BorderLayout.NORTH);

        // Gắn thanh cuộn phòng trường hợp số máy quá nhiều vượt quá màn hình
        JScrollPane scrollPane = new JScrollPane(pnlWrapper);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setWelcomeText(String text) {
        lblWelcome.setText(text);
    }

    // HÀM VẼ SƠ ĐỒ MÁY TÍNH TỰ ĐỘNG
    public void drawComputerMap(List<ComputerDTO> computers, ActionListener onPcClick) {
        pnlComputerMap.removeAll();

        for (ComputerDTO pc : computers) {
            JButton btnPC = new JButton();
            btnPC.setPreferredSize(new Dimension(160, 120));

            // Dùng thủ thuật HTML để JButton hiển thị được nhiều dòng chữ
            String htmlText = "<html><center>"
                    + "<h2 style='margin:0; padding:0;'>" + pc.getComputerName() + "</h2>"
                    + "<i>" + pc.getZoneName() + "</i><br>"
                    + "<b>" + pc.getStatus() + "</b>"
                    + "</center></html>";
            btnPC.setText(htmlText);

            // Gắn Mã Máy (ID) chìm vào nút để Controller biết đang click máy nào
            btnPC.setActionCommand(pc.getComputerId());

            // Tô màu theo trạng thái máy
            if (pc.getStatus().equalsIgnoreCase("In Use")) {
                btnPC.setBackground(new Color(231, 76, 60));  // Màu Đỏ
                btnPC.setForeground(Color.WHITE);
            } else if (pc.getStatus().equalsIgnoreCase("Available")) {
                btnPC.setBackground(new Color(46, 204, 113)); // Màu Xanh lá
                btnPC.setForeground(Color.BLACK);
            } else if(pc.getStatus().equalsIgnoreCase("Maintenance")){
                btnPC.setBackground(Color.GRAY);              // Màu xám (Bảo trì)
                btnPC.setForeground(Color.WHITE);
            }

            // Gắn sự kiện click chuột được truyền từ Controller sang
            btnPC.addActionListener(onPcClick);

            pnlComputerMap.add(btnPC); // Thêm máy vào lưới
        }

        // Cập nhật lại giao diện ngay lập tức
        pnlComputerMap.revalidate();
        pnlComputerMap.repaint();
    }

    public JButton getBtnManageMembers() {
        return btnManageMembers;
    }

    public JButton getBtnReport() {
        return btnReport;
    }

    public JButton getBtnManageMenu(){
        return btnManageMenu;
    }

    public JButton getBtnManageEmployee() {
        return btnManageEmployee;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}