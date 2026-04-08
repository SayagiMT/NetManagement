package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.dto.CartItemDTO;
import com.NetProject.dto.MenuItemDTO;
import com.NetProject.service.OrderService;
import com.NetProject.service.OrderServiceImp;
import com.NetProject.view.frmOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.Image;

public class OrderController {
    private frmOrder view;
    private OrderService service;
    private AccountDTO loggedInUser;
    private String computerId;
    private List<MenuItemDTO> menuList;
    private List<CartItemDTO> cartList;

    public OrderController(frmOrder view, AccountDTO user, String computerId) {
        this.view = view;
        this.loggedInUser = user;
        this.computerId = computerId;
        this.service = new OrderServiceImp();
        this.cartList = new ArrayList<>();

        loadMenu();
        initEvents();
    }

    private void loadMenu() {
        menuList = service.getAllMenuItems();

        DefaultTableModel model = view.getMenuModel();
        model.setRowCount(0);

        for (MenuItemDTO item : menuList) {
            model.addRow(new Object[]{
                    item.getServiceId(),
                    item.getServiceName(),
                    item.getPrice(),
                    item.getStockQuantity()
            });
        }
    }

    private void initEvents() {
        view.getTblMenu().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getTblMenu().getSelectedRow();
                if (row >= 0) {
                    MenuItemDTO selectedItem = menuList.get(row);

                    // 1. Lấy tên file ảnh
                    String imageName = selectedItem.getImagePath();
                    if (imageName == null || imageName.trim().isEmpty()) {
                        imageName = "no-image.png";
                    }

                    // 2. Chắp nối thành đường dẫn thư mục chuẩn xác
                    String dirPath = System.getProperty("user.dir") + "/src/main/resources/images/";
                    File imgFile = new File(dirPath + imageName);

                    // 3. Load ảnh lên giao diện bằng luồng ngầm
                    if (imgFile.exists()) {
                        view.getLblImage().setIcon(null);
                        view.getLblImage().setText("Đang load...");

                        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
                            @Override
                            protected ImageIcon doInBackground() {
                                ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                                // Ép cứng kích thước 150x150 để tránh lỗi Width = 0
                                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                                return new ImageIcon(img);
                            }

                            @Override
                            protected void done() {
                                try {
                                    view.getLblImage().setIcon(get());
                                    view.getLblImage().setText("");
                                } catch (Exception ex) {
                                    view.getLblImage().setText("Lỗi hiển thị");
                                }
                            }
                        };
                        worker.execute();
                    } else {
                        view.getLblImage().setIcon(null);
                        view.getLblImage().setText("Chưa có ảnh");
                    }
                }
            }
        });

        // Sự kiện: Nút THÊM VÀO GIỎ HÀNG
        view.getBtnAddToCart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getTblMenu().getSelectedRow();
                if (selectedRow == -1) {
                    view.showMessage("Vui lòng chọn một món từ Thực Đơn!", true);
                    return;
                }

                MenuItemDTO selectedItem = menuList.get(selectedRow);

                if (selectedItem.getStockQuantity() <= 0) {
                    view.showMessage("Món này đã hết hàng!", true);
                    return;
                }

                String input = JOptionPane.showInputDialog(view, "Nhập số lượng cho món [" + selectedItem.getServiceName() + "]:", "1");
                if (input == null || input.trim().isEmpty()) return; // Hủy bỏ

                try {
                    int quantity = Integer.parseInt(input);
                    if (quantity <= 0 || quantity > selectedItem.getStockQuantity()) {
                        view.showMessage("Số lượng không hợp lệ hoặc vượt quá tồn kho!", true);
                        return;
                    }

                    addToCart(selectedItem, quantity);

                } catch (NumberFormatException ex) {
                    view.showMessage("Vui lòng nhập số nguyên hợp lệ!", true);
                }
            }
        });

        // Sự kiện: Nút XÓA MÓN CHỌN
        view.getBtnRemoveItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getTblCart().getSelectedRow();
                if (selectedRow == -1) {
                    view.showMessage("Vui lòng chọn món cần xóa trong Giỏ Hàng!", true);
                    return;
                }

                cartList.remove(selectedRow);
                updateCartView();
            }
        });

        // Sự kiện: Nút THANH TOÁN / CHỐT ĐƠN
        view.getBtnCheckout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cartList.isEmpty()) {
                    view.showMessage("Giỏ hàng đang trống!", true);
                    return;
                }

                float total = 0;
                for (CartItemDTO c : cartList) total += c.getTotalAmount();

                int confirm = JOptionPane.showConfirmDialog(view,
                        String.format("Xác nhận chốt đơn hàng: %,.0f VNĐ?", total),
                        "Xác Nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    // GỌI SERVICE MỚI: Truyền thêm biến computerId vào để ghi nợ
                    boolean success = service.checkout(cartList, total, loggedInUser.getAccountId(), computerId);

                    if (success) {
                        view.showMessage("Chốt đơn thành công!", false);
                        cartList.clear();     // Xóa sạch giỏ hàng
                        updateCartView();     // Cập nhật lại giao diện giỏ
                        loadMenu();           // Load lại Menu để cập nhật số lượng tồn kho
                    } else {
                        view.showMessage("Lỗi hệ thống khi thanh toán!", true);
                    }
                }
            }
        });
    }

    // Hàm xử lí Logic Thêm vào giỏ
    private void addToCart(MenuItemDTO item, int quantity) {
        boolean exists = false;
        for (CartItemDTO cartItem : cartList) {
            if (cartItem.getServiceId().equals(item.getServiceId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                exists = true;
                break;
            }
        }

        if (!exists) {
            cartList.add(new CartItemDTO(item.getServiceId(), item.getServiceName(), quantity, item.getPrice()));
        }

        updateCartView();
    }

    // Hàm vẽ lại giỏ hàng: Hệ thống sẽ xóa giỏ hàng cũ và cập nhật lại giỏ hàng mới
    private void updateCartView() {
        DefaultTableModel model = view.getCartModel();
        model.setRowCount(0);

        float total = 0;

        for (CartItemDTO cartItem : cartList) {
            model.addRow(new Object[]{
                    cartItem.getServiceName(),
                    cartItem.getQuantity(),
                    cartItem.getPrice(),
                    cartItem.getTotalAmount(),
                    cartItem.getServiceId()
            });
            total += cartItem.getTotalAmount();
        }

        view.setTotalAmountText(String.format("Tổng Tiền: %,.0f VNĐ", total));
    }
}