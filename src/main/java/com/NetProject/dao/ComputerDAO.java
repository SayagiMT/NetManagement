package com.NetProject.dao;

import com.NetProject.entity.Computer;
import java.util.Collections;
import java.util.List;

public class ComputerDAO extends GenericDAO<Computer, String> {

    public ComputerDAO() {
        super(Computer.class);
    }

    // Lấy máy tính theo trạng thái (Trống/Đang dùng...)
    public List<Computer> getComputersByStatus(String status) {
        List<Computer> result = executeQuery(session ->
                session.createQuery("FROM Computer c WHERE c.status = :trangThai", Computer.class)
                        .setParameter("trangThai", status)
                        .list()
        );
        return result != null ? result : Collections.emptyList();
    }
}