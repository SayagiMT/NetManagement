package com.NetProject.controller;

import com.NetProject.entity.ServiceItem;
import com.NetProject.service.MenuService;
import com.NetProject.view.frmMenu;

import javax.swing.*;
import java.util.List;

public class MenuController {
    private final frmMenu view;
    private final MenuService service;
    private List<ServiceItem> list;

    public MenuController(frmMenu view) {
        this.view = view;
        this.service = new MenuService();
        loadData();
        initEvents();
    }

    private void loadData() {
        list = service.getAllItems();
        view.getModel().setRowCount(0);
        for (ServiceItem i : list) {
            view.getModel().addRow(new Object[]{
                    i.getServiceId(),
                    i.getServiceName(),
                    String.format("%,.0f", i.getPrice()),
                    i.getStockQuantity()
            });
        }
    }

    private void initEvents() {
        // 1. Đổ dữ liệu từ bảng lên form khi click
        view.getTblMenu().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTblMenu().getSelectedRow();
            if (row >= 0 && !e.getValueIsAdjusting()) {
                ServiceItem item = list.get(row);
                view.getTxtId().setText(item.getServiceId());
                view.getTxtName().setText(item.getServiceName());
                view.getTxtPrice().setText(String.valueOf(item.getPrice()));
                view.getTxtStock().setText(String.valueOf(item.getStockQuantity()));
            }
        });

        // 2. Xử lý nút Thêm mới
        view.getBtnAdd().addActionListener(e -> {
            try {
                String name = view.getTxtName().getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập tên món!");
                    return;
                }
                float price = Float.parseFloat(view.getTxtPrice().getText());
                int stock = Integer.parseInt(view.getTxtStock().getText());

                if (service.addMenu(name, price, stock)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                    loadData();
                    view.getBtnClear().doClick();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Giá và Tồn kho phải là số!");
            }
        });

        // 3. Xử lý nút Cập nhật
        view.getBtnUpdate().addActionListener(e -> {
            try {
                String id = view.getTxtId().getText();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn món cần sửa từ bảng!");
                    return;
                }
                String name = view.getTxtName().getText().trim();
                float price = Float.parseFloat(view.getTxtPrice().getText());
                int stock = Integer.parseInt(view.getTxtStock().getText());

                if (service.updateMenu(id, name, price, stock)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadData();
                    view.getBtnClear().doClick();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Giá và Tồn kho phải là số hợp lệ!");
            }
        });

        // 4. Xử lý nút Xóa món
        view.getBtnDelete().addActionListener(e -> {
            String id = view.getTxtId().getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn món cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa món này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteMenu(id)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadData();
                    view.getBtnClear().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Không thể xóa! Món này có thể đang nằm trong hóa đơn của khách.");
                }
            }
        });

        // 5. Nút Làm mới (Clear form)
        view.getBtnClear().addActionListener(e -> {
            view.getTxtId().setText("");
            view.getTxtName().setText("");
            view.getTxtPrice().setText("");
            view.getTxtStock().setText("");
            view.getTblMenu().clearSelection();
        });
    }
}