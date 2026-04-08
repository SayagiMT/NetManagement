package com.NetProject.dto;

public class MenuItemDTO {
    private String serviceId;
    private String serviceName;
    private Float price;
    private Integer stockQuantity;
    private String imagePath;

    public MenuItemDTO() {}

    public MenuItemDTO(String serviceId, String serviceName, Float price, Integer stockQuantity, String imagePath) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath;
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}