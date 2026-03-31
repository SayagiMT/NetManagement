package com.NetProject.dto;

public class ServiceItemDTO {

    private String serviceId;
    private String serviceName;
    private String serviceType;
    private Float price;
    private Integer stockQuantity;


    public ServiceItemDTO() {
    }


    public ServiceItemDTO(String serviceId, String serviceName, String serviceType, Float price, Integer stockQuantity) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}