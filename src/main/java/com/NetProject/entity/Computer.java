package com.NetProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Thực thể: MAYTINH
@Entity
public class Computer {
    private String computerId;
    private String computerName;
    private String status;
    private Zone zone;

    public Computer() {
    }

    public Computer(String computerId, String computerName, String status, Zone zone) {
        this.computerId = computerId;
        this.computerName = computerName;
        this.status = status;
        this.zone = zone;
    }

    // Mã máy: String (Thuộc tính khóa - Primary Key)
    @Id
    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    // Tên máy: String (Thuộc tính mô tả)
    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    // Trạng thái: String <Trống, Đang dùng, Bảo trì> (Thuộc tính mô tả)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Mã khu vực <tham chiếu đến đối tượng khu vực>: (Khóa ngoại - Foreign Key)
    // Quan hệ N-1 với Khu vực
    @ManyToOne
    @JoinColumn(name = "zoneId")
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}