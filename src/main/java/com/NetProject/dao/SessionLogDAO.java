package com.NetProject.dao;

import com.NetProject.entity.SessionLog;

public class SessionLogDAO extends GenericDAO<SessionLog, String> {
    public SessionLogDAO() {
        super(SessionLog.class);
    }

    // Hàm tìm phiên chơi ĐANG HOẠT ĐỘNG của một máy cụ thể
    public SessionLog getActiveSessionByComputer(String computerId) {
        return executeQuery(session ->
                session.createQuery("FROM SessionLog s WHERE s.computer.computerId = :compId AND s.endTime IS NULL", SessionLog.class)
                        .setParameter("compId", computerId)
                        .uniqueResult()
        );
    }
}