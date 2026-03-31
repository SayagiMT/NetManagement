package com.NetProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Thực thể: KHUVUC
@Entity
public class Zone {
    private String zoneId;
    private String zoneName;
    private Float hourlyRate;

    public Zone() {
    }

    public Zone(String zoneId, String zoneName, Float hourlyRate) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.hourlyRate = hourlyRate;
    }

    // Mã khu vực: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    // Tên khu vực: String (Thuộc tính mô tả)
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    // Đơn giá giờ: float (Thuộc tính mô tả)
    public Float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}