package com.NetProject.service;

import com.NetProject.dao.AccountDAO;

import com.NetProject.dao.DepositTransactionDAO;
import com.NetProject.dto.CustomerDTO;
import com.NetProject.entity.Employee;
import com.NetProject.entity.Account;
import com.NetProject.entity.DepositTransaction;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final AccountDAO accountDAO;

    public CustomerService() {
        this.accountDAO = new AccountDAO();
    }

    // 1. Lấy danh sách tất cả Hội viên
    public List<CustomerDTO> getAllMembers() {
        List<Account> accounts = accountDAO.findAll();
        List<CustomerDTO> members = new ArrayList<>();

        if (accounts != null) {
            for (Account acc : accounts) {
                // Chỉ lấy những tài khoản có Role là Member
                if ("Member".equalsIgnoreCase(acc.getRole())) {
                    members.add(new CustomerDTO(acc.getAccountId(), acc.getUsername(), acc.getPassword(), acc.getBalance()));
                }
            }
        }
        return members;
    }

    // 2. Thêm Hội viên mới
    public boolean addMember(String username, String password) {
        try {
            Account acc = new Account();
            acc.setAccountId("MEM_" + System.currentTimeMillis()); // Mã tự sinh ngẫu nhiên
            acc.setUsername(username);
            acc.setPassword(password);
            acc.setRole("Member");
            acc.setBalance(0f); // Mới tạo thì số dư = 0

            accountDAO.create(acc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Cập nhật thông tin (Đổi pass)
    public boolean updateMember(String accountId, String newPassword) {
        try {
            Account acc = accountDAO.findById(accountId);
            if (acc != null) {
                acc.setPassword(newPassword);
                accountDAO.update(acc);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Xóa Hội viên (Kết hợp Hard Delete và Soft Delete)
    public boolean deleteMember(String accountId) {
        try {
            Account acc = accountDAO.findById(accountId);
            if (acc != null) {
                // 1. Thử lệnh xóa cứng (Hard Delete)
                accountDAO.delete(acc);
                return true;
            }
            return false;
        } catch (Exception e) {
            // 2. Nếu Database báo lỗi (Do dính khóa ngoại) -> Xóa mềm (Soft Delete)
            try {
                Account acc = accountDAO.findById(accountId);
                if (acc != null) {
                    acc.setRole("Banned"); // Đổi Role để cấm đăng nhập
                    acc.setUsername(acc.getUsername() + " (Đã xóa)");
                    accountDAO.update(acc);
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    // 5. Nạp tiền vào tài khoản Hội viên (Có lưu người thu tiền)
    public boolean topUp(String accountId, float amount, String employeeId) { // <-- Thêm tham số employeeId
        try {
            Account acc = accountDAO.findById(accountId);

            // Tìm nhân viên/admin đang thao tác nạp tiền
            com.NetProject.dao.EmployeeDAO empDAO = new com.NetProject.dao.EmployeeDAO();
            Employee emp = empDAO.findById(employeeId);

            if (acc != null) {
                // 1. Cộng tiền vào ví
                float currentBalance = acc.getBalance() != null ? acc.getBalance() : 0f;
                acc.setBalance(currentBalance + amount);
                accountDAO.update(acc);

                // 2. Tạo biên lai và gắn tên người thu tiền
                DepositTransaction trans = new DepositTransaction();
                trans.setTransactionId("DEP_" + System.currentTimeMillis());
                trans.setAmount(amount);
                trans.setDepositTime(java.time.LocalDateTime.now());
                trans.setAccount(acc); // Khách nạp

                if (emp != null) {
                    trans.setEmployee(emp); // Gắn Nhân viên / Admin thu tiền
                }

                // Lưu xuống DB
                DepositTransactionDAO transDAO = new DepositTransactionDAO();
                transDAO.create(trans);

                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}