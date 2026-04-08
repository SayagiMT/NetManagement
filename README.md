# Hệ Thống Quản Lý Quán Net (Net Management)

Ứng dụng desktop quản lý phòng máy toàn diện được phát triển dựa trên kiến trúc **Java Swing MVC + 3-Tier** kết hợp **Hibernate/JPA**. Giao diện phần mềm được thiết kế phẳng (Flat Design) hiện đại bằng thư viện **FlatLaf**.

Hệ thống được tổ chức phân tầng chặt chẽ (Entity / DTO / DAO / Service / Controller / View) giúp dễ dàng bảo trì và mở rộng.

---

## Yêu cầu môi trường

| Thành phần | Phiên bản gợi ý | Ghi chú |
| :--- | :--- | :--- |
| **JDK** | 17+ | Java Development Kit |
| **MySQL** | 8.x | Cơ sở dữ liệu chính |
| **Maven** | 3.8+ | Công cụ quản lý thư viện (pom.xml) |


---

##  Cài đặt nhanh

### 1. Chuẩn bị Cơ sở dữ liệu
Mở công cụ quản lý MySQL (Workbench, Navicat, hoặc cmd) và tạo một query mới, sau đó dán file netproject.sql vào và chạy. Hệ thống sẽ tạo ra 1 cơ sở dữ liệu cho dự án.

### 2. Biên dịch và chạy
Hệ thống hỗ trợ 2 phương pháp khởi chạy tùy thuộc vào môi trường của bạn:

**Cách 1: Chạy bằng dòng lệnh Maven (Dành cho Terminal / CMD)**
Mở terminal tại thư mục gốc của project (nơi chứa file `pom.xml`) và thực thi câu lệnh sau:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.NetProject.MainApp"
```

**Cách 2: Chạy trực tiếp (MainApp.java)**
Mở project bằng IDE, sau đó tìm và chạy file MainApp.java
```bash
com.NetProject.MainApp
```
Sau khi khởi chạy thành công, hệ thống sẽ tự động nạp giao diên FlatLaf, kết nối Hibernate Session và hiển thị Form đăng nhập

## Cấu trúc thư mục
```bash
NetManagement/
├── src/
│   └──  main/
│       ├── java/
│       │   └── com/NetProject/
│       │       ├── controller/       # Tầng Điều khiển (C trong MVC)
│       │       │   ├── CustomerController.java
│       │       │   ├── EmployeeController.java
│       │       │   ├──LoginController.java
│       │       │   ├── MenuController.java
│       │       │   ├── OrderController.java
│       │       │   └──ReportController.java
│       │       ├── dao/              # Tầng Truy cập Dữ liệu (DAO - Hibernate)
│       │       │   ├── AccountDAO.java
│       │       │   ├── ComputerDAO.java
│       │       │   ├── CustomerDAO.java
│       │       │   ├── DepositTransactionDAO.java
│       │       │   ├── EmployeeDAO.java
│       │       │   ├── GenericDAO.java
│       │       │   ├── InvoiceDAO.java
│       │       │   ├── InvoiceDetailDAO.java
│       │       │   ├── ServiceItemDAO.java
│       │       │   ├── SessionLogDAO.java
│       │       │   └── ZoneDAO.java
│       │       ├── dto/              # Đối tượng vận chuyển dữ liệu (Data Transfer Object)
│       │       │   ├── AccountDTO.java
│       │       │   ├── CartItemDTO.java
│       │       │   ├── ComputerDTO.java
│       │       │   ├── CustomerDTO.java
│       │       │   ├── MenuItemDTO.java
│       │       │   └── RevenueDTO.java
│       │       ├── entity/           # Các thực thể JPA (Ánh xạ Database)
│       │       │   ├── Account.java
│       │       │   ├── Computer.java
│       │       │   ├── Customer.java
│       │       │   ├── DepositTransaction.java
│       │       │   ├── Employee.java
│       │       │   ├── Invoice.java
│       │       │   ├── InvoiceDetail.java
│       │       │   ├── InvoiceDetailId.java
│       │       │   ├── ServiceItem.java
│       │       │   ├── SessionLog.java
│       │       │   └── Zone.java
│       │       ├── service/          # Tầng Xử lý nghiệp vụ (Business Logic)
│       │       │   ├── AccountService.java
│       │       │   ├── AccountServiceImp.java
│       │       │   ├── ComputerService.java
│       │       │   ├── ComputerServiceImp.java
│       │       │   ├── CustomerService.java
│       │       │   ├── CustomerServiceImp.java
│       │       │   ├── EmployeeService.java
│       │       │   ├── EmployeeServiceImp.java
│       │       │   ├── MenuService.java
│       │       │   ├── MenuServiceImp.java
│       │       │   ├── OrderService.java
│       │       │   ├── OrderServiceImp.java
│       │       │   ├── ReportService.java
│       │       │   └── ReportServiceImp.java
│       │       ├── util/             
│       │       │   ├── HibernateUtil.java
│       │       ├── view/             # Tầng Giao diện (V trong MVC - Swing)
│       │       │   ├── frmCustomer.java
│       │       │   ├── frmEmployee.java
│       │       │   ├── frmLogin.java
│       │       │   ├── frmMain.java
│       │       │   ├── frmMenu.java
│       │       │   ├── frmOrder.java
│       │       │   └── frmReport.java
│       │       └── MainApp.java      # Điểm khởi chạy chương trình (Main class)
│       └── resources/                # Chứa cấu hình và tài nguyên
│           ├── images/               # Logo, Icon, Banner của ứng dụng
│           ├── netproject.sql        # Cơ sở dữ liệu của dự án
│           └── hibernate.cfg.xml     # Cấu hình kết nối MySQL & Hibernate
│   
│       
│       
├── pom.xml                           # Quản lý thư viện Maven (Hibernate, MySQL, FlatLaf)
└── README.md                         
```
> [!NOTE]
> **Tài khoản Đăng nhập Mặc định:**
> 
> Khi tạo xong cơ sở dữ liệu, hệ thống đã tự động cấp phát một tài khoản Quản trị viên mẫu để trải nghiệm nhanh:
> 
> **1. Tài khoản Quản lý (Admin/ Employee):**
> - **Username:** `admin` 
> - **Password:** `admin@2026`
> 
> **2. Tài khoản hội viên (Member):**
> - **Username:** `thanh_vip` 
> - **Password:** `tungpro99`
> 
> *Để xem danh sách toàn bộ tài khoản Nhân viên và Hội viên khác, vui lòng kiểm tra dữ liệu trực tiếp tại bảng `Account` trong Database.*

> [!WARNING]
> **QUAN TRỌNG: Cấu hình tài khoản MySQL trước khi chạy!**
> 
> Do cấu hình MySQL trên mỗi máy tính là khác nhau, trước khi chạy chương trình, mở file `src/main/resources/hibernate.cfg.xml` và sửa lại 2 dòng sau cho khớp với username và password trên máy: 
> 
> ```xml
> > <property name="hibernate.connection.username">root</property>
> 
> > <property name="hibernate.connection.password">123456</property>
> ```
