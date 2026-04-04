package com.NetProject.service;

import com.NetProject.dao.ServiceItemDAO;
import com.NetProject.entity.ServiceItem;
import java.util.List;

public class MenuService {
    // Thêm chữ 'final' để dập tắt cảnh báo vàng của IDE
    private final ServiceItemDAO dao = new ServiceItemDAO();

    public List<ServiceItem> getAllItems() {
        return dao.findAll();
    }

    public boolean addMenu(String name, float price, int stock) {
        try {
            ServiceItem item = new ServiceItem();

            // Đã đổi thành setServiceId và setServiceName
            // (Nếu trong file ServiceItem.java của bạn tên khác, hãy sửa lại cho khớp nhé)
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
                // Đã đổi thành setServiceName
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