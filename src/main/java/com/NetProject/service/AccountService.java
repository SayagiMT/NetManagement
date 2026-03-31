package com.NetProject.service;

import com.NetProject.dao.AccountDAO;
import com.NetProject.dto.AccountDTO;
import com.NetProject.entity.Account;

public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Hàm xử lý logic đăng nhập
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return AccountDTO nếu đúng, null nếu sai
     */
    public AccountDTO login(String username, String password) {
        // 1. Gọi DAO tìm tài khoản (sử dụng đúng hàm checkLogin bạn đã viết)
        Account acc = accountDAO.checkLogin(username, password);

        // 2. Nếu sai user hoặc pass
        if (acc == null) {
            return null;
        }

        // 3. Nếu đúng, map dữ liệu sang DTO để trả về
        return new AccountDTO(
                acc.getAccountId(),
                acc.getUsername(),
                acc.getBalance(),
                acc.getRole()
        );
    }
}