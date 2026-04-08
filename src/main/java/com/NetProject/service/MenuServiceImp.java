package com.NetProject.service;

import com.NetProject.dao.ServiceItemDAO;
import com.NetProject.entity.ServiceItem;
import java.util.List;

public class MenuServiceImp implements MenuService {
    private final ServiceItemDAO dao = new ServiceItemDAO();

    @Override
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

    @Override
    public boolean addMenu(String name, float price, int stock, String imagePath) {
        try {
            ServiceItem item = new ServiceItem();

            // TỰ ĐỘNG SINH Mã: Chữ "SVC_" ghép với 6 số ngẫu nhiên sinh ra từ thời gian thực
            String autoId = "SVC_" + (System.currentTimeMillis() % 1000000);

            item.setServiceId(autoId);
            item.setServiceName(name);
            item.setPrice(price);
            item.setStockQuantity(stock);
            item.setServiceType("Đang bán");

            // 👉 LƯU TÊN ẢNH VÀO ENTITY
            item.setImagePath(imagePath);

            dao.create(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMenu(String id, String name, float price, int stock, String imagePath) {
        try {
            ServiceItem item = dao.findById(id);
            if (item != null) {
                item.setServiceName(name);
                item.setPrice(price);
                item.setStockQuantity(stock);

                if (imagePath != null && !imagePath.isEmpty()) {
                    item.setImagePath(imagePath);
                }

                dao.update(item);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteMenu(String id) {
        try {
            ServiceItem item = dao.findById(id);
            if (item != null) {
                // ÁP DỤNG SOFT DELETE (XÓA MỀM)
                item.setServiceType("Ngừng bán");
                item.setStockQuantity(0);

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