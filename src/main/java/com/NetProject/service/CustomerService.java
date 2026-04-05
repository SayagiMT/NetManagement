package com.NetProject.service;

import com.NetProject.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    // 1. Lấy danh sách tất cả Hội viên
    List<CustomerDTO> getAllMembers();

    // 2. Thêm Hội viên mới
    boolean addMember(String username, String password);

    // 3. Cập nhật thông tin
    boolean updateMember(String accountId, String newUsername, String newPassword);

    // 4. Xóa Hội viên
    boolean deleteMember(String accountId);

    // 5. Nạp tiền vào tài khoản Hội viên
    boolean topUp(String accountId, float amount, String employeeId);
}
