package test;

import com.NetProject.dao.*;
import com.NetProject.entity.*;

import java.util.Random;

public class TestLargeDataSeeder {
    public static void main(String[] args) {
        System.out.println("⏳ ĐANG KHỞI TẠO DỮ LIỆU LỚN CHO DỰ ÁN... VUI LÒNG ĐỢI...");

        // 1. Khởi tạo các DAO
        ZoneDAO zoneDAO = new ZoneDAO();
        ComputerDAO computerDAO = new ComputerDAO();
        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ServiceItemDAO serviceItemDAO = new ServiceItemDAO();

        Random rand = new Random();

        try {
            // ==========================================
            // BƯỚC 1: TẠO 3 KHU VỰC (ZONES)
            // ==========================================
            Zone zStandard = new Zone("Z_STD", "Khu Thường", 8000f);
            Zone zVIP = new Zone("Z_VIP", "Khu VIP", 12000f);
            Zone zStream = new Zone("Z_STR", "Phòng Stream", 25000f);

            zoneDAO.create(zStandard);
            zoneDAO.create(zVIP);
            zoneDAO.create(zStream);

            // Đưa vào mảng để lát nữa random cho máy tính
            Zone[] zones = {zStandard, zVIP, zStream};

            // ==========================================
            // BƯỚC 2: TẠO 40 MÁY TÍNH (COMPUTERS)
            // ==========================================
            for (int i = 1; i <= 40; i++) {
                // Format mã máy thành dạng PC_01, PC_02... PC_40
                String pcId = String.format("PC_%02d", i);
                String pcName = "Máy " + i;

                // Random khu vực cho máy
                Zone randomZone = zones[rand.nextInt(zones.length)];

                // Đa số là Available, thi thoảng có máy Bảo trì
                String status = (rand.nextInt(10) > 8) ? "Maintenance" : "Available";

                computerDAO.create(new Computer(pcId, pcName, status, randomZone));
            }
            System.out.println("✅ Đã tạo xong 40 Máy tính.");

            // ==========================================
            // BƯỚC 3: TẠO 20 MÓN ĂN/THỨC UỐNG (SERVICES)
            // ==========================================
            String[] foodNames = {"Mì Hảo Hảo Xào", "Cơm Rang Dưa Bò", "Bánh Mì Trứng", "Xúc Xích Nướng", "Mì Cay 7 Cấp Độ"};
            String[] drinkNames = {"Sting Dâu", "Bò Húc", "Coca Cola", "Trà Đá", "Trà Đào Cam Sả", "Cafe Đen", "Cafe Sữa"};

            int svcCount = 1;
            for (String food : foodNames) {
                serviceItemDAO.create(new ServiceItem("SVC_F" + svcCount++, food, "Đồ ăn", 25000f + rand.nextInt(20000), 50));
            }
            for (String drink : drinkNames) {
                serviceItemDAO.create(new ServiceItem("SVC_D" + svcCount++, drink, "Nước uống", 10000f + rand.nextInt(15000), 100));
            }
            System.out.println("✅ Đã tạo xong danh mục Đồ ăn & Nước uống.");

            // ==========================================
            // BƯỚC 4: TẠO TÀI KHOẢN ADMIN & NHÂN VIÊN
            // ==========================================
            Account accAdmin = new Account("ACC_ADMIN", "admin", "123", 0f, "Admin");
            Account accEmp = new Account("ACC_EMP", "nhanvien", "123", 0f, "Employee");
            accountDAO.create(accAdmin);
            accountDAO.create(accEmp);

            employeeDAO.create(new Employee("EMP_01", "Nguyễn Quản Lý", "Full-time", accAdmin));
            employeeDAO.create(new Employee("EMP_02", "Trần Ca Đêm", "Part-time", accEmp));

            // ==========================================
            // BƯỚC 5: TẠO 50 KHÁCH HÀNG (HỘI VIÊN)
            // ==========================================
            String[] ho = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh", "Phan", "Vũ", "Võ", "Đặng"};
            String[] ten = {"Anh", "Tuấn", "Minh", "Nam", "Hải", "Phong", "Linh", "Trang", "Nhi", "Hân"};

            for (int i = 1; i <= 50; i++) {
                String cusId = String.format("CUS_%03d", i);
                String username = "member" + i;
                // Tiền dư random từ 10k đến 200k
                float balance = 10000f + rand.nextInt(190) * 1000f;

                Account accCus = new Account("ACC_" + cusId, username, "123", balance, "Member");
                accountDAO.create(accCus);

                // Random họ và tên ráp lại
                String fullName = ho[rand.nextInt(ho.length)] + " " + ten[rand.nextInt(ten.length)];
                String phone = "09" + (10000000 + rand.nextInt(89999999)); // Random SĐT 10 số

                customerDAO.create(new Customer(cusId, fullName, phone, accCus));
            }
            System.out.println("✅ Đã tạo xong 50 Khách hàng (Hội viên).");

            System.out.println("\n🎉 HOÀN TẤT! DỮ LIỆU ĐÃ ĐƯỢC BƠM ĐẦY VÀO MYSQL!");
            System.out.println("Hãy mở Form Main lên để xem bản đồ 40 máy tính khổng lồ nhé!");

        } catch (Exception e) {
            System.err.println("❌ CÓ LỖI XẢY RA TRONG QUÁ TRÌNH TẠO DỮ LIỆU!");
            e.printStackTrace();
        }
    }
}