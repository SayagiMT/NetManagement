package com.NetProject.service;

import com.NetProject.entity.ServiceItem;

import java.util.List;

public interface MenuService {
    List<ServiceItem> getAllItems();

    boolean addMenu(String name, float price, int stock);

    boolean updateMenu(String id, String name, float price, int stock);

    boolean deleteMenu(String id);
}
