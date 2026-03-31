package com.NetProject.dto;

public class CartItemDTO {
    private String serviceId;
    private String serviceName;
    private Integer quantity;
    private Float price;

    public CartItemDTO(String serviceId, String serviceName, Integer quantity, Float price) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.price = price;
    }

    // Tính thành tiền của món này (Số lượng * Đơn giá)
    public Float getTotalAmount() {
        return this.quantity * this.price;
    }

    public String getServiceId() { return serviceId; }
    public String getServiceName() { return serviceName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Float getPrice() { return price; }
}