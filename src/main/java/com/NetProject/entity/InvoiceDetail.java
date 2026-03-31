package com.NetProject.entity;

import jakarta.persistence.*;

// Thực thể trung gian: CHITIETHOADON
// Là thực thể yếu giải quyết mối quan hệ N-N giữa Hóa đơn và Dịch vụ.
@Entity
public class InvoiceDetail {
    private InvoiceDetailId id;
    private Integer quantity;
    private Float sellingPrice;

    private Invoice invoice;
    private ServiceItem serviceItem;

    public InvoiceDetail() {
    }

    public InvoiceDetail(InvoiceDetailId id, Integer quantity, Float sellingPrice, Invoice invoice, ServiceItem serviceItem) {
        this.id = id;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.invoice = invoice;
        this.serviceItem = serviceItem;
    }

    // Mã hóa đơn và Mã dịch vụ (Khóa chính/Khóa ngoại - PK, FK)
    @EmbeddedId
    public InvoiceDetailId getId() {
        return id;
    }

    public void setId(InvoiceDetailId id) {
        this.id = id;
    }

    // Số lượng: int (Thuộc tính mô tả)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Đơn giá bán: float (Thuộc tính mô tả)
    public Float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    // Map khóa ngoại Mã Hóa Đơn vào thuộc tính của id
    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoiceId")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    // Map khóa ngoại Mã Dịch Vụ vào thuộc tính của id
    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "serviceId")
    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }
}