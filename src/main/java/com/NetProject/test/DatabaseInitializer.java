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

            session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0", void.class).executeUpdate();

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
        // 2. TẠO TÀI KHOẢN VÀ NHÂN VIÊN (1 Admin, 3 Nhân viên)
        // ==========================================
        Account aAdmin = new Account("ACC_ADMIN", "admin", "123", 0f, "Admin");
        Account aEmp1 = new Account("ACC_EMP01", "nhanvien1", "123", 0f, "Employee");
        Account aEmp2 = new Account("ACC_EMP02", "nhanvien2", "123", 0f, "Employee");
        Account aEmp3 = new Account("ACC_EMP03", "nhanvien3", "123", 0f, "Employee");

        accDAO.create(aAdmin);
        accDAO.create(aEmp1);
        accDAO.create(aEmp2);
        accDAO.create(aEmp3);

        empDAO.create(new Employee("EMP_001", "Trần Tiến Đạt", "Quản lý", aAdmin));
        empDAO.create(new Employee("EMP_002", "Nhân Nguyễn", "Ca Sáng", aEmp1));
        empDAO.create(new Employee("EMP_003", "Lê Thị Thu Thảo", "Ca Chiều", aEmp2));
        empDAO.create(new Employee("EMP_004", "Phạm Hoàng Long", "Ca Tối", aEmp3));

        // ==========================================
        // 3. TẠO 20 KHÁCH HÀNG (HỘI VIÊN) BẰNG MẢNG
        // ==========================================
        String[][] memberData = {
                {"ACC_MEM01", "nhan_sv_ute", "Nguyễn Hiếu Kì Nhân", "50000"},
                {"ACC_MEM02", "hcmute_sv", "Lí Hào Kiệt", "15000"},
                {"ACC_MEM03", "thanh_vip", "Lê Thanh Tùng", "100000"},
                {"ACC_MEM04", "hoang_pro", "Phạm Minh Hoàng", "20000"},
                {"ACC_MEM05", "dung_tk", "Trần Trí Dũng", "0"},
                {"ACC_MEM06", "linh_ute", "Nguyễn Thùy Linh", "35000"},
                {"ACC_MEM07", "nam_deptrai", "Lê Hoài Nam", "50000"},
                {"ACC_MEM08", "khanh_gamer", "Bùi Quốc Khánh", "150000"},
                {"ACC_MEM09", "phuc_lol", "Vũ Hoàng Phúc", "10000"},
                {"ACC_MEM10", "tuan_anh99", "Đặng Tuấn Anh", "80000"},
                {"ACC_MEM11", "huy_gaming", "Đỗ Gia Huy", "40000"},
                {"ACC_MEM12", "bao_ngoc", "Nguyễn Bảo Ngọc", "60000"},
                {"ACC_MEM13", "quang_hai", "Hồ Quang Hải", "0"},
                {"ACC_MEM14", "minh_khoi", "Ngô Minh Khôi", "25000"},
                {"ACC_MEM15", "duc_thang", "Trương Đức Thắng", "120000"},
                {"ACC_MEM16", "thao_nhung", "Lý Thảo Nhung", "30000"},
                {"ACC_MEM17", "tien_dat", "Đoàn Tiến Đạt", "15000"},
                {"ACC_MEM18", "hai_dang", "Vương Hải Đăng", "45000"},
                {"ACC_MEM19", "quoc_bao", "Mai Quốc Bảo", "90000"},
                {"ACC_MEM20", "anh_tu", "Trịnh Anh Tú", "20000"}
        };

        for (int i = 0; i < memberData.length; i++) {
            String accId = memberData[i][0];
            String user = memberData[i][1];
            String name = memberData[i][2];
            float bal = Float.parseFloat(memberData[i][3]);

            // Tạo mã ID động: CUS_01 -> CUS_20
            String cusId = String.format("CUS_%02d", i + 1);
            String phone = String.format("0901%06d", i + 1); // Số điện thoại giả lập

            Account aMem = new Account(accId, user, "123", bal, "Member");
            accDAO.create(aMem);

            Customer cus = new Customer(cusId, name, phone, aMem);
            cusDAO.create(cus);
        }

        // ==========================================
        // 4. TẠO 20 SƠ ĐỒ MÁY TÍNH BẰNG VÒNG LẶP
        // ==========================================
        // Khu Thường: 10 máy (PC_01 -> PC_10)
        for (int i = 1; i <= 10; i++) {
            compDAO.create(new Computer(String.format("PC_%02d", i), "Máy Thường " + String.format("%02d", i), "Available", z1));
        }

        // Khu VIP: 6 máy (PC_11 -> PC_16)
        for (int i = 11; i <= 16; i++) {
            compDAO.create(new Computer(String.format("PC_%02d", i), "Máy VIP " + String.format("%02d", i - 10), "Available", z2));
        }

        // Khu Thi Đấu: 4 máy (PC_17 -> PC_20)
        for (int i = 17; i <= 20; i++) {
            compDAO.create(new Computer(String.format("PC_%02d", i), "Máy Thi Đấu " + String.format("%02d", i - 16), "Available", z3));
        }

        // ==========================================
        // 5. TẠO THỰC ĐƠN ĐỒ ĂN/THỨC UỐNG (GIỮ NGUYÊN)
        // ==========================================
        svcDAO.create(new ServiceItem("SVC_01", "Nước Tăng Lực Sting", "Thức uống", 15000f, 100));
        svcDAO.create(new ServiceItem("SVC_02", "Nước Lọc Aquafina", "Thức uống", 10000f, 50));
        svcDAO.create(new ServiceItem("SVC_03", "Mì Tôm Trứng Xúc Xích", "Đồ ăn", 25000f, 30));
        svcDAO.create(new ServiceItem("SVC_04", "Cơm Chiên Dương Châu", "Đồ ăn", 35000f, 20));
        svcDAO.create(new ServiceItem("SVC_05", "Thẻ Garena 50k", "Thẻ game", 50000f, 200));
    }
}