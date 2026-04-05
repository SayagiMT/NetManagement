package com.NetProject.service;

import com.NetProject.dto.CartItemDTO;
import com.NetProject.dto.MenuItemDTO;

import java.util.List;

public interface OrderService {
    List<MenuItemDTO> getAllMenuItems();

    boolean checkout(List<CartItemDTO> cartList, Float totalAmount, String accountId, String computerId);
}
