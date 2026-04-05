package com.NetProject.service;

import com.NetProject.dto.AccountDTO;

public interface AccountService {
    AccountDTO login(String username, String password);
}
