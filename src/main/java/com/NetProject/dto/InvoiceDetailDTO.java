package com.NetProject.dto;




public class InvoiceDetailDTO {
    private String id;
    private Integer quantity;
    private Float sellingPrice;
    private String invoiceId;
    private String serviceItemId;

    public InvoiceDetailDTO() {
    }

    public InvoiceDetailDTO(String id, Integer quantity, Float sellingPrice, String invoiceId, String serviceItemId) {
        this.id = id;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.invoiceId = invoiceId;
        this.serviceItemId = serviceItemId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }


    public String getServiceItemId() {
        return serviceItemId;
    }

    public void setServiceItemId(String serviceItemId) {
        this.serviceItemId = serviceItemId;
    }
}