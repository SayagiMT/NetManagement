package com.NetProject.dao;

import com.NetProject.entity.Invoice;
import java.util.Collections;
import java.util.List;

public class InvoiceDAO extends GenericDAO<Invoice, String> {

    public InvoiceDAO() {
        super(Invoice.class);
    }

    // Lấy hóa đơn theo trạng thái (VD: Chưa thanh toán)
    public List<Invoice> getInvoicesByStatus(String status) {
        List<Invoice> result = executeQuery(session ->
                session.createQuery("FROM Invoice i WHERE i.status = :trangThai", Invoice.class)
                        .setParameter("trangThai", status)
                        .list()
        );
        return result != null ? result : Collections.emptyList();
    }

    // Cập nhật nhanh trạng thái hóa đơn
    public void updateInvoiceStatus(String invoiceId, String newStatus) {
        executeTransaction(session -> {
            Invoice inv = session.find(Invoice.class, invoiceId);
            if (inv != null) {
                inv.setStatus(newStatus);
                session.merge(inv);
            }
        });
    }
}