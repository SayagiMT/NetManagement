package com.NetProject.dto;

import com.NetProject.entity.Zone;

public class ComputerDTO {
    private String computerId;
    private String computerName;
    private String status;
    private String zoneName;

    public ComputerDTO (){}

    public ComputerDTO(String computerId, String computerName, String status, String zoneName){
        this.computerId = computerId;
        this.computerName = computerName;
        this.status = status;
        this.zoneName = zoneName;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}