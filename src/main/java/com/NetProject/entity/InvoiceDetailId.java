package com.NetProject.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// Class đại diện cho khóa chính phức hợp của Chi tiết hóa đơn
@Embeddable
public class InvoiceDetailId implements Serializable {
    private String invoiceId;
    private String serviceId;

    public InvoiceDetailId() {}

    public InvoiceDetailId(String invoiceId, String serviceId) {
        this.invoiceId = invoiceId;
        this.serviceId = serviceId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDetailId that = (InvoiceDetailId) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, serviceId);
    }
}