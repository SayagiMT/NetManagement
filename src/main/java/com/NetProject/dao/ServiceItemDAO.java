package com.NetProject.dao;

import com.NetProject.entity.ServiceItem;
import java.util.Collections;
import java.util.List;

public class ServiceItemDAO extends GenericDAO<ServiceItem, String> {

    public ServiceItemDAO() {
        super(ServiceItem.class);
    }

    // Lọc thực đơn theo loại (Đồ ăn/Thức uống)
    public List<ServiceItem> getServicesByType(String type) {
        List<ServiceItem> result = executeQuery(session ->
                session.createQuery("FROM ServiceItem s WHERE s.serviceType = :loai", ServiceItem.class)
                        .setParameter("loai", type)
                        .list()
        );
        return result != null ? result : Collections.emptyList();
    }
}