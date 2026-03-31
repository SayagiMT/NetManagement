package com.NetProject.dto;

public class ComputerDTO {
    private String computerId;
    private String computerName;
    private String status;
    private String zoneName;   // Lấy từ Zone
    private Float hourlyRate;  // Lấy từ Zone

    public ComputerDTO() {
    }

    public ComputerDTO(String computerId, String computerName, String status, String zoneName, Float hourlyRate) {
        this.computerId = computerId;
        this.computerName = computerName;
        this.status = status;
        this.zoneName = zoneName;
        this.hourlyRate = hourlyRate;
    }

    // Getters
    public String getComputerId() { return computerId; }
    public String getComputerName() { return computerName; }
    public String getStatus() { return status; }
    public String getZoneName() { return zoneName; }
    public Float getHourlyRate() { return hourlyRate; }

    // Setters
    public void setComputerId(String computerId) { this.computerId = computerId; }
    public void setComputerName(String computerName) { this.computerName = computerName; }
    public void setStatus(String status) { this.status = status; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public void setHourlyRate(Float hourlyRate) { this.hourlyRate = hourlyRate; }
}