package com.NetProject.controller;

import com.NetProject.entity.ServiceItem;
import com.NetProject.service.MenuService;
import com.NetProject.service.MenuServiceImp;
import com.NetProject.view.frmMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MenuController {
    private final frmMenu view;
    private final MenuService service;
    private List<ServiceItem> list;
    private String currentImagePath = "";

    public MenuController(frmMenu view) {
        this.view = view;
        this.service = new MenuServiceImp();
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
        // 1. ĐỔ DỮ LIỆU TỪ BẢNG LÊN FORM KHI CLICK VÀ LOAD ẢNH
        view.getTblMenu().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTblMenu().getSelectedRow();
            if (row >= 0 && !e.getValueIsAdjusting()) {
                ServiceItem item = list.get(row);
                view.getTxtId().setText(item.getServiceId());
                view.getTxtName().setText(item.getServiceName());
                view.getTxtPrice().setText(String.valueOf(item.getPrice()));
                view.getTxtStock().setText(String.valueOf(item.getStockQuantity()));

                // --- XỬ LÝ LOAD ẢNH LÊN PREVIEW CHỐNG ĐƠ MÁY (SỬ DỤNG SWINGWORKER) ---
                currentImagePath = (item.getImagePath() != null) ? item.getImagePath() : "";
                String targetDirPath = System.getProperty("user.dir") + "/src/main/resources/images/";
                String imageName = currentImagePath.isEmpty() ? "no-image.png" : currentImagePath;
                File imgFile = new File(targetDirPath + imageName);

                if (imgFile.exists()) {
                    // Hiện thông báo đang tải để giao diện có phản hồi
                    view.getLblImagePreview().setIcon(null);
                    view.getLblImagePreview().setText("Đang load ảnh...");

                    // SỬ DỤNG SWING WORKER ĐỂ LOAD ẢNH NGẦM
                    SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
                        @Override
                        protected ImageIcon doInBackground() {
                            ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                            // Bắt lỗi an toàn kích thước ảnh, nếu getWidth() = 0 dùng mặc định 140
                            int width = view.getLblImagePreview().getWidth() > 0 ? view.getLblImagePreview().getWidth() : 140;
                            int height = view.getLblImagePreview().getHeight() > 0 ? view.getLblImagePreview().getHeight() : 140;

                            // SỬ DỤNG SCALE_FAST để load ảnh mượt mà, không bị đơ giao diện
                            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_FAST);
                            return new ImageIcon(img);
                        }

                        @Override
                        protected void done() {
                            try {
                                view.getLblImagePreview().setIcon(get()); // Lấy kết quả ảnh từ doInBackground
                                view.getLblImagePreview().setText("");
                            } catch (Exception ex) {
                                view.getLblImagePreview().setText("Ảnh bị lỗi");
                            }
                        }
                    };
                    worker.execute(); // Bắt đầu chạy ngầm
                } else {
                    view.getLblImagePreview().setIcon(null);
                    view.getLblImagePreview().setText("Chưa có ảnh");
                }
            }
        });

        // 2. NÚT THÊM
        view.getBtnAdd().addActionListener(e -> {
            try {
                // ... (kiểm tra tên món)
                String name = view.getTxtName().getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập Tên món!");
                    return;
                }

                float price = Float.parseFloat(view.getTxtPrice().getText().trim());
                int stock = Integer.parseInt(view.getTxtStock().getText().trim());

                // ĐÃ SỬA: Truyền thêm currentImagePath vào hàm addMenu
                if (service.addMenu(name, price, stock, currentImagePath)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                    loadData();
                    view.getBtnClear().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại do lỗi hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi: Giá tiền và Tồn kho phải là số!", "Sai định dạng", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 3. XỬ LÝ NÚT TẢI ẢNH LÊN (Đã tối ưu Đa Luồng SwingWorker)
        view.getBtnUploadImage().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh minh họa cho món ăn");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh (JPG, PNG)", "jpg", "jpeg", "png"));

            int userSelection = fileChooser.showOpenDialog(view);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File sourceFile = fileChooser.getSelectedFile();

                // Khóa nút báo hiệu đang xử lý
                view.getBtnUploadImage().setEnabled(false);
                view.getLblImagePreview().setIcon(null);
                view.getLblImagePreview().setText("Đang xử lý ảnh...");

                // SỬ DỤNG SWING WORKER ĐỂ TẢI ẢNH NGẦM (Copy & Scale)
                SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        String ext = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
                        String newFileName = "IMG_" + System.currentTimeMillis() + ext;
                        String targetDirPath = System.getProperty("user.dir") + "/src/main/resources/images/";
                        File dir = new File(targetDirPath);
                        if (!dir.exists()) dir.mkdirs();

                        File targetFile = new File(targetDirPath + newFileName);

                        // Copy file tốn thời gian -> Chạy ngầm
                        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        currentImagePath = newFileName;

                        // Load & Scale ảnh tốn thời gian -> Chạy ngầm
                        ImageIcon icon = new ImageIcon(targetFile.getAbsolutePath());
                        int width = view.getLblImagePreview().getWidth() > 0 ? view.getLblImagePreview().getWidth() : 140;
                        int height = view.getLblImagePreview().getHeight() > 0 ? view.getLblImagePreview().getHeight() : 140;
                        // Chạy ngầm nên dùng SCALE_SMOOTH cho ảnh đẹp mà không sợ treo
                        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        return new ImageIcon(img);
                    }

                    // Khi chạy ngầm xong thì trả kết quả lên giao diện
                    @Override
                    protected void done() {
                        try {
                            view.getLblImagePreview().setIcon(get());
                            view.getLblImagePreview().setText("");
                        } catch (Exception ex) {
                            view.getLblImagePreview().setText("Lỗi xử lý");
                            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            view.getBtnUploadImage().setEnabled(true); // Mở khóa nút bấm
                        }
                    }
                };
                worker.execute();
            }
        });

        // 4. NÚT CẬP NHẬT
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

                // ĐÃ SỬA: Truyền thêm currentImagePath vào hàm updateMenu
                if (service.updateMenu(id, name, price, stock, currentImagePath)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadData();
                    view.getBtnClear().doClick();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Giá và Tồn kho phải là số hợp lệ!");
            }
        });

        // 5. NÚT XÓA MÓN
        view.getBtnDelete().addActionListener(e -> {
            // ... (code xóa món cũ)
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

        // 6. NÚT LÀM MỚI (CLEAR FORM)
        view.getBtnClear().addActionListener(e -> {
            view.getTxtId().setText("");
            view.getTxtName().setText("");
            view.getTxtPrice().setText("");
            view.getTxtStock().setText("");
            view.getTblMenu().clearSelection();

            // ĐÃ SỬA: Dọn dẹp ảnh trên UI về mặc định
            currentImagePath = "";
            view.getLblImagePreview().setIcon(null);
            view.getLblImagePreview().setText("Chưa có ảnh");
        });
    }
}