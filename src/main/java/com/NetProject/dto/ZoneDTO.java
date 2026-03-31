package com.NetProject.dto;

public class ZoneDTO {
    private String zoneId;
    private String zoneName;
    private Float hourlyRate; // Đơn giá giờ

    public ZoneDTO() {
    }

    public ZoneDTO(String zoneId, String zoneName, Float hourlyRate) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.hourlyRate = hourlyRate;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}