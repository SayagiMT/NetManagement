package com.NetProject.controller;

import com.NetProject.dto.AccountDTO;
import com.NetProject.dto.CustomerDTO;
import com.NetProject.service.CustomerService;
import com.NetProject.service.CustomerServiceImp;
import com.NetProject.view.frmCustomer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerController {
    private frmCustomer view;
    private CustomerService service;
    private List<CustomerDTO> memberList;
    private AccountDTO loggedInUser;

    // Constructor
    public CustomerController(frmCustomer view, AccountDTO user) {
        this.view = view;
        this.loggedInUser = user; // Lưu lại người đang đăng nhập
        this.service = new CustomerServiceImp();

        loadData();
        initEvents();
    }

    // Hàm tải dữ liệu lên bảng
    private void loadData() {
        memberList = service.getAllMembers();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (CustomerDTO mem : memberList) {
            model.addRow(new Object[]{
                    mem.getAccountId(),
                    mem.getUsername(),
                    mem.getPassword(),
                    String.format("%,.0f", mem.getBalance())
            });
        }
    }
    // Hàm gắn sự kiện (Click, Thêm, Sửa, Xóa...)
    private void initEvents() {
        // 1. CLick vào bảng hiện dữ liệu lên form
        view.getTblMembers().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = view.getTblMembers().getSelectedRow();
                    if (row >= 0) {
                        view.getTxtAccountId().setText(memberList.get(row).getAccountId());
                        view.getTxtUsername().setText(memberList.get(row).getUsername());
                        view.getTxtPassword().setText(memberList.get(row).getPassword());
                        view.getTxtBalance().setText(String.format("%,.0f", memberList.get(row).getBalance()));
                    }
                }
            }
        });

        // 2. Nút Làm Mới (Clear): Xóa các ô nhập để nhập thông tin khác
        view.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getTxtAccountId().setText("");
                view.getTxtUsername().setText("");
                view.getTxtPassword().setText("");
                view.getTxtBalance().setText("0");
                view.getTxtUsername().setEditable(true); // Mở lại cho phép nhập
                view.getTblMembers().clearSelection();
            }
        });

        // 3. Nút Thêm Mới (Create): Kiểm tra xem Password và Username có trống không, sau đó lưu vào Database qua Services
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = view.getTxtUsername().getText().trim();
                String pass = new String(view.getTxtPassword().getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    view.showMessage("Vui lòng nhập Username và Password!", true);
                    return;
                }

                if (service.addMember(user, pass)) {
                    view.showMessage("Thêm hội viên thành công!", false);
                    view.getBtnClear().doClick();
                    loadData();
                } else {
                    view.showMessage("Lỗi! Username có thể đã tồn tại.", true);
                }
            }
        });

        // 4. Nút Cập Nhật (Tên đăng nhập & Mật Khẩu)
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTxtAccountId().getText();
                if (id.isEmpty()) {
                    view.showMessage("Vui lòng chọn hội viên cần sửa từ danh sách!", true);
                    return;
                }

                // Lấy thêm username và password từ giao diện
                String newUsername = view.getTxtUsername().getText().trim();
                String newPass = new String(view.getTxtPassword().getPassword());

                if (newUsername.isEmpty()) {
                    view.showMessage("Tên đăng nhập không được để trống!", true);
                    return;
                }

                if (service.updateMember(id, newUsername, newPass)) {
                    view.showMessage("Cập nhật thông tin hội viên thành công!", false);
                    loadData();
                } else {
                    view.showMessage("Lỗi khi cập nhật! (Có thể tên đăng nhập đã bị trùng)", true);
                }
            }
        });

        // 5. Nút Xóa : Sử dụng ID để xóa trong Database
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTxtAccountId().getText();
                if (id.isEmpty()) {
                    view.showMessage("Vui lòng chọn hội viên cần xóa!", true);
                    return;
                }

                if (service.deleteMember(id)) {
                    view.showMessage("Xóa thành công!", false);
                    view.getBtnClear().doClick();
                    loadData();
                } else {
                    view.showMessage("Lỗi khi xóa! Hội viên có thể đang có hóa đơn chưa thanh toán.", true);
                }
            }
        });

        // 6. Nút Nạp Tiền: Bật khung thông báo hỏi nạp bao nhiêu tiền, sau đó gọi Service để cộng tiền vào tài khoản Hội viên, đồng thời lưu lại bằng chứng thu ngân nào đã nạp.
        view.getBtnTopUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy ID hội viên đang được chọn trên form
                String id = view.getTxtAccountId().getText();
                if (id.isEmpty()) {
                    view.showMessage("Vui lòng click chọn một Hội viên từ danh sách bên dưới để nạp tiền!", true);
                    return;
                }

                // Hiển thị hộp thoại yêu cầu nhập số tiền
                String input = JOptionPane.showInputDialog(view,
                        "Nhập số tiền muốn nạp cho [" + view.getTxtUsername().getText() + "] (VNĐ):",
                        "Nạp Tiền Giao Dịch", JOptionPane.QUESTION_MESSAGE);

                if (input == null || input.trim().isEmpty()) return; // Hủy bỏ nếu người dùng bấm Cancel

                try {
                    float amount = Float.parseFloat(input);
                    if (amount <= 0) {
                        view.showMessage("Số tiền nạp phải lớn hơn 0!", true);
                        return;
                    }

                    // Gọi Service xử lý cộng tiền, truyền luôn ID của người đang thu tiền
                    if (service.topUp(id, amount, loggedInUser.getAccountId())) {
                        view.showMessage(String.format("Nạp thành công %,.0f VNĐ vào tài khoản!", amount), false);
                        loadData(); // Load lại bảng để thấy số dư tăng lên
                    } else {
                        view.showMessage("Lỗi hệ thống khi nạp tiền!", true);
                    }
                } catch (NumberFormatException ex) {
                    view.showMessage("Vui lòng nhập số tiền hợp lệ (chỉ nhập số)!", true);
                }
            }
        });
    }
}