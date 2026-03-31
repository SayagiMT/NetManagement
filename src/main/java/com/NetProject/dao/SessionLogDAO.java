package com.NetProject.dao;

import com.NetProject.entity.SessionLog;

public class SessionLogDAO extends GenericDAO<SessionLog, String> {
    public SessionLogDAO() {
        super(SessionLog.class);
    }
}