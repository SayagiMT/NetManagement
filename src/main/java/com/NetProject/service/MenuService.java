package com.NetProject.service;

import com.NetProject.dao.ServiceItemDAO;
import com.NetProject.entity.ServiceItem;
import java.util.List;

public class MenuService {
    private final ServiceItemDAO dao = new ServiceItemDAO();

    public List<ServiceItem> getAllItems() {
        return dao.findAll();
    }

    public boolean addMenu(String name, float price, int stock) {
        try {
            ServiceItem item = new ServiceItem();
            item.setServiceId("SVC_" + System.currentTimeMillis() % 100000);
            item.setServiceName(name);
            item.setPrice(price);
            item.setStockQuantity(stock);

            dao.create(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMenu(String id, String name, float price, int stock) {
        try {
            ServiceItem item = dao.findById(id);
            if (item != null) {
                item.setServiceName(name);
                item.setPrice(price);
                item.setStockQuantity(stock);
                dao.update(item);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteMenu(String id) {
        try {
            ServiceItem item = dao.findById(id);
            if (item != null) {
                dao.delete(item);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}