package com.NetProject.service;

import com.NetProject.dto.ComputerDTO;

import java.util.List;

public interface ComputerService {
    List<ComputerDTO> getAllComputersForDisplay();

    boolean openComputer(String computerId, String accountId);

    String closeComputer(String computerId, String cashierName);
}
