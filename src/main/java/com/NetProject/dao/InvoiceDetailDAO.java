package com.NetProject.dao;

import com.NetProject.entity.InvoiceDetail;
import com.NetProject.entity.InvoiceDetailId;

public class InvoiceDetailDAO extends GenericDAO<InvoiceDetail, InvoiceDetailId> {
    public InvoiceDetailDAO() {
        super(InvoiceDetail.class);
    }
}