package com.NetProject.service;

import com.NetProject.dao.AccountDAO;
import com.NetProject.dao.ComputerDAO; // Thêm import này
import com.NetProject.dao.InvoiceDAO;
import com.NetProject.dao.InvoiceDetailDAO;
import com.NetProject.dao.ServiceItemDAO;
import com.NetProject.dto.CartItemDTO;
import com.NetProject.dto.MenuItemDTO;
import com.NetProject.entity.*;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private final ServiceItemDAO serviceItemDAO;
    private final InvoiceDAO invoiceDAO;
    private final InvoiceDetailDAO invoiceDetailDAO;
    private final AccountDAO accountDAO;
    private final ComputerDAO computerDAO; // 1. Khai báo thêm ComputerDAO

    public OrderService() {
        this.serviceItemDAO = new ServiceItemDAO();
        this.invoiceDAO = new InvoiceDAO();
        this.invoiceDetailDAO = new InvoiceDetailDAO();
        this.accountDAO = new AccountDAO();
        this.computerDAO = new ComputerDAO(); // 2. Khởi tạo ComputerDAO
    }

    /**
     * Lấy danh sách tất cả các món ăn/thức uống đang có trong kho để hiển thị lên Menu
     */
    public List<MenuItemDTO> getAllMenuItems() {
        List<ServiceItem> items = serviceItemDAO.findAll();
        List<MenuItemDTO> listDTOs = new ArrayList<>();

        if (items != null) {
            for (ServiceItem item : items) {
                listDTOs.add(new MenuItemDTO(
                        item.getServiceId(),
                        item.getServiceName(),
                        item.getPrice(),
                        item.getStockQuantity()
                ));
            }
        }
        return listDTOs;
    }

    /**
     * Xử lý logic Chốt đơn hàng và Trừ kho
     */
    public boolean checkout(List<CartItemDTO> cartList, Float totalAmount, String accountId, String computerId) {
        try {
            Account acc = accountDAO.findById(accountId);

            Invoice invoice = new Invoice();

            // 3. Khai báo biến invoiceId rõ ràng trước khi dùng
            String invoiceId = "INV_" + System.currentTimeMillis();
            invoice.setInvoiceId(invoiceId);

            invoice.setCreatedAt(LocalDateTime.now());
            invoice.setTotalAmount(totalAmount);
            invoice.setAccount(acc);

            // Bắt buộc gắn máy và ghi nợ
            Computer pc = computerDAO.findById(computerId);
            invoice.setComputer(pc);
            invoice.setStatus("Chưa thanh toán");

            invoiceDAO.create(invoice);

            // Tạo Chi tiết hóa đơn và Trừ tồn kho
            for (CartItemDTO item : cartList) {
                ServiceItem serviceItem = serviceItemDAO.findById(item.getServiceId());
                if (serviceItem != null) {

                    // Lưu Chi tiết
                    InvoiceDetailId detailId = new InvoiceDetailId(invoiceId, serviceItem.getServiceId());
                    InvoiceDetail detail = new InvoiceDetail(detailId, item.getQuantity(), item.getPrice(), invoice, serviceItem);
                    invoiceDetailDAO.update(detail);

                    // Trừ Tồn kho
                    if (serviceItem.getStockQuantity() != null) {
                        int newStock = serviceItem.getStockQuantity() - item.getQuantity();
                        serviceItem.setStockQuantity(newStock);
                        serviceItemDAO.update(serviceItem);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}