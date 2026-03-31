package com.NetProject.dao;

import com.NetProject.entity.Zone;

public class ZoneDAO extends GenericDAO<Zone, String> {
    public ZoneDAO() {
        super(Zone.class);
    }
}