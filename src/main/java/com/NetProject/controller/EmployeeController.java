package com.NetProject.controller;

import com.NetProject.entity.Employee;
import com.NetProject.service.EmployeeService;
import com.NetProject.service.EmployeeServiceImp;
import com.NetProject.view.frmEmployee;

import javax.swing.*;
import java.util.List;

public class EmployeeController {
    private final frmEmployee view;
    private final EmployeeService service;
    private List<Employee> list;

    //Constructor
    public EmployeeController(frmEmployee view) {
        this.view = view;
        this.service = new EmployeeServiceImp();
        loadData();
        initEvents();
    }
    // Hàm tải dữ liệu lên bảng
    private void loadData() {
        list = service.getAllEmployees();
        view.getModel().setRowCount(0);
        if (list != null) {
            for (Employee emp : list) {
                // Kiểm tra null an toàn cho Account
                String user = (emp.getAccount() != null) ? emp.getAccount().getUsername() : "N/A";
                String pass = (emp.getAccount() != null) ? emp.getAccount().getPassword() : "N/A";
                String role = (emp.getAccount() != null) ? emp.getAccount().getRole() : "N/A";

                view.getModel().addRow(new Object[]{
                        emp.getEmployeeId(), emp.getEmployeeName(), user, pass, role
                });
            }
        }
    }

    // Hàm gắn sự kiện
    private void initEvents() {
        // Đổ dữ liệu lên textfield khi bấm vào bảng
        view.getTblEmployee().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTblEmployee().getSelectedRow();
            if (row >= 0 && !e.getValueIsAdjusting()) {
                view.getTxtId().setText(view.getModel().getValueAt(row, 0).toString());
                view.getTxtRealName().setText(view.getModel().getValueAt(row, 1).toString());
                view.getTxtUsername().setText(view.getModel().getValueAt(row, 2).toString());
                view.getTxtPassword().setText(view.getModel().getValueAt(row, 3).toString());
                view.getCbRole().setSelectedItem(view.getModel().getValueAt(row, 4).toString());
            }
        });

        // Xử lý nút Thêm
        view.getBtnAdd().addActionListener(e -> {
            String realName = view.getTxtRealName().getText().trim();
            String username = view.getTxtUsername().getText().trim();
            String password = view.getTxtPassword().getText().trim();
            String role = view.getCbRole().getSelectedItem().toString();

            if (realName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (service.addEmployee(realName, username, password, role)) {
                JOptionPane.showMessageDialog(view, "Tạo tài khoản nhân viên thành công!");
                loadData();
                view.getBtnClear().doClick();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi! Có thể tên đăng nhập đã tồn tại.");
            }
        });

        // Xử lý nút Xóa
        view.getBtnDelete().addActionListener(e -> {
            String id = view.getTxtId().getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên cần sa thải!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn sa thải và xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteEmployee(id)) {
                    JOptionPane.showMessageDialog(view, "Đã xóa nhân viên!");
                    loadData();
                    view.getBtnClear().doClick();
                }
            }
        });

        // Xử lý làm mới
        view.getBtnClear().addActionListener(e -> {
            view.getTxtId().setText("");
            view.getTxtRealName().setText("");
            view.getTxtUsername().setText("");
            view.getTxtPassword().setText("");
            view.getCbRole().setSelectedIndex(0);
            view.getTblEmployee().clearSelection();
        });
    }
}