package com.NetProject.test;

import com.NetProject.dao.*;
import com.NetProject.entity.*;
import com.NetProject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseInitializer {

    public static void main(String[] args) {
        System.out.println("Đang kết nối Database và dọn dẹp dữ liệu cũ...");
        clearOldData();

        System.out.println("Đang khởi tạo dữ liệu Master mới...");
        seedData();

        System.out.println("✅ HOÀN TẤT! Dữ liệu đã được nạp thành công.");
        System.out.println("Bây giờ bạn có thể chạy file MainApp.java để sử dụng phần mềm.");

        // Tắt Hibernate để dừng chương trình
        HibernateUtil.shutdown();
    }

    /**
     * Dùng Native SQL của Hibernate để tắt khóa ngoại và quét sạch toàn bộ bảng
     */
    private static void clearOldData() {
        Transaction tr = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tr = session.beginTransaction();

            // 1. Tắt kiểm tra khóa ngoại (chỉ áp dụng cho MySQL)
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0", void.class).executeUpdate();

            // 2. Xóa sạch dữ liệu các bảng
            session.createNativeQuery("DELETE FROM invoicedetail", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM invoice", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM deposittransaction", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM sessionlog", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM customer", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM employee", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM computer", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM zone", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM serviceitem", void.class).executeUpdate();
            session.createNativeQuery("DELETE FROM account", void.class).executeUpdate();

            // 3. Bật lại khóa ngoại
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1", void.class).executeUpdate();

            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
            System.err.println("Lỗi khi xóa dữ liệu cũ!");
        }
    }

    /**
     * Sử dụng chính các DAO đã viết để thêm dữ liệu mẫu (Đúng chuẩn cấu trúc MVC/DAO)
     */
    private static void seedData() {
        // Khởi tạo các DAO
        ZoneDAO zoneDAO = new ZoneDAO();
        AccountDAO accDAO = new AccountDAO();
        EmployeeDAO empDAO = new EmployeeDAO();
        CustomerDAO cusDAO = new CustomerDAO();
        ComputerDAO compDAO = new ComputerDAO();
        ServiceItemDAO svcDAO = new ServiceItemDAO();

        // ==========================================
        // 1. TẠO KHU VỰC (ZONE)
        // ==========================================
        Zone z1 = new Zone("Z01", "Khu Thường", 8000f);
        Zone z2 = new Zone("Z02", "Khu VIP", 15000f);
        Zone z3 = new Zone("Z03", "Khu Thi Đấu (Esports)", 20000f);
        zoneDAO.create(z1);
        zoneDAO.create(z2);
        zoneDAO.create(z3);

        // ==========================================
        // 2. TẠO TÀI KHOẢN (ACCOUNT)
        // ==========================================
        Account aAdmin = new Account("ACC_ADMIN", "admin", "123", 0f, "Admin");
        Account aEmp = new Account("ACC_EMP01", "nhanvien1", "123", 0f, "Employee");
        Account aMem1 = new Account("ACC_MEM01", "nhan_sv_ute", "123", 50000f, "Member"); // Khách có 50k
        Account aMem2 = new Account("ACC_MEM02", "hcmute_sv", "123", 15000f, "Member");  // Khách có 15k
        accDAO.create(aAdmin);
        accDAO.create(aEmp);
        accDAO.create(aMem1);
        accDAO.create(aMem2);

        // ==========================================
        // 3. TẠO NHÂN VIÊN VÀ KHÁCH HÀNG
        // ==========================================
        Employee emp1 = new Employee("EMP_001", "Trần Tiến Đạt", "Full-time", aAdmin);
        Employee emp2 = new Employee("EMP_002", "Nhân Nguyễn", "Ca Sáng", aEmp);
        empDAO.create(emp1);
        empDAO.create(emp2);

        Customer cus1 = new Customer("CUS_001", "Nguyễn Hiếu Kì Nhân", "0901234567", aMem1);
        Customer cus2 = new Customer("CUS_002", "Lí Hào Kiệt", "0987654321", aMem2);
        cusDAO.create(cus1);
        cusDAO.create(cus2);

        // ==========================================
        // 4. TẠO SƠ ĐỒ MÁY TÍNH
        // ==========================================
        compDAO.create(new Computer("PC_01", "Máy Thường 01", "Available", z1));
        compDAO.create(new Computer("PC_02", "Máy Thường 02", "Available", z1));
        compDAO.create(new Computer("PC_03", "Máy Thường 03", "Available", z1));

        compDAO.create(new Computer("PC_04", "Máy VIP 01", "Available", z2));
        compDAO.create(new Computer("PC_05", "Máy VIP 02", "Available", z2));
        compDAO.create(new Computer("PC_06", "Máy VIP 03", "Available", z2));

        compDAO.create(new Computer("PC_07", "Máy Thi Đấu 01", "Available", z3));
        compDAO.create(new Computer("PC_08", "Máy Thi Đấu 02", "Available", z3));
        compDAO.create(new Computer("PC_09", "Máy Thi Đấu 03", "Available", z3));

        // ==========================================
        // 5. TẠO THỰC ĐƠN ĐỒ ĂN/THỨC UỐNG
        // ==========================================
        svcDAO.create(new ServiceItem("SVC_01", "Nước Tăng Lực Sting", "Thức uống", 15000f, 100));
        svcDAO.create(new ServiceItem("SVC_02", "Nước Lọc Aquafina", "Thức uống", 10000f, 50));
        svcDAO.create(new ServiceItem("SVC_03", "Mì Tôm Trứng Xúc Xích", "Đồ ăn", 25000f, 30));
        svcDAO.create(new ServiceItem("SVC_04", "Cơm Chiên Dương Châu", "Đồ ăn", 35000f, 20));
        svcDAO.create(new ServiceItem("SVC_05", "Thẻ Garena 50k", "Thẻ game", 50000f, 200));
    }
}