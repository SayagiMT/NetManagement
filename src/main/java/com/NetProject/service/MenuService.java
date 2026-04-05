package com.NetProject.service;

import com.NetProject.dao.ServiceItemDAO;
import com.NetProject.entity.ServiceItem;
import java.util.List;

public class MenuService {
    private final ServiceItemDAO dao = new ServiceItemDAO();

    public List<ServiceItem> getAllItems() {
        List<ServiceItem> allItems = dao.findAll();
        List<ServiceItem> activeItems = new java.util.ArrayList<>();

        // Chỉ lấy những món vẫn đang kinh doanh để hiển thị lên UI
        if (allItems != null) {
            for (ServiceItem item : allItems) {
                if (item.getServiceType() != null && !item.getServiceType().equalsIgnoreCase("Ngừng bán")) {
                    activeItems.add(item);
                }
            }
        }
        return activeItems;
    }

    public boolean addMenu(String name, float price, int stock) {
        try {
            ServiceItem item = new ServiceItem();

            // TỰ ĐỘNG SINH MÁ: Chữ "SVC_" ghép với 6 số ngẫu nhiên sinh ra từ thời gian thực
            String autoId = "SVC_" + (System.currentTimeMillis() % 1000000);

            item.setServiceId(autoId);
            item.setServiceName(name);
            item.setPrice(price);
            item.setStockQuantity(stock);

            item.setServiceType("Đang bán");

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
                // KHÔNG DÙNG: dao.delete(item);

                // ÁP DỤNG SOFT DELETE (XÓA MỀM)
                item.setServiceType("Ngừng bán"); // Bạn có thể tận dụng trường type hoặc thêm trường status mới
                // item.setStockQuantity(0); // Có thể ép tồn kho về 0 để chắc chắn không ai bán được nữa

                dao.update(item);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}