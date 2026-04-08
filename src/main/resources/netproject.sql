-- 0. KHỞI TẠO DATABASE
CREATE DATABASE IF NOT EXISTS `netproject` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `netproject`;

-- 1. DỌN DẸP DỮ LIỆU CŨ
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `sessionlog`, `invoicedetail`, `invoice`, `deposittransaction`,
    `customer`, `employee`, `computer`, `zone`, `serviceitem`, `account`;

-- 2. TẠO BẢNG
CREATE TABLE `account` (
                           `accountId` varchar(255) PRIMARY KEY,
                           `balance` float DEFAULT 0,
                           `password` varchar(255),
                           `role` varchar(255),
                           `username` varchar(255)
);

CREATE TABLE `zone` (
                        `zoneId` varchar(255) PRIMARY KEY,
                        `hourlyRate` float,
                        `zoneName` varchar(255)
);

CREATE TABLE `serviceitem` (
                               `serviceId` varchar(255) PRIMARY KEY,
                               `price` float,
                               `serviceName` varchar(255),
                               `serviceType` varchar(255),
                               `stockQuantity` int,
                               `imagePath` varchar(255)
);

CREATE TABLE `employee` (
                            `employeeId` varchar(255) PRIMARY KEY,
                            `employeeName` varchar(255),
                            `shift` varchar(255),
                            `accountId` varchar(255),
                            FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`)
);

CREATE TABLE `customer` (
                            `customerId` varchar(255) PRIMARY KEY,
                            `customerName` varchar(255),
                            `phoneNumber` varchar(255),
                            `accountId` varchar(255),
                            FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`)
);

CREATE TABLE `computer` (
                            `computerId` varchar(255) PRIMARY KEY,
                            `computerName` varchar(255),
                            `status` varchar(255),
                            `zoneId` varchar(255),
                            FOREIGN KEY (`zoneId`) REFERENCES `zone` (`zoneId`)
);

CREATE TABLE `invoice` (
                           `invoiceId` varchar(255) PRIMARY KEY,
                           `createdAt` datetime(6),
                           `status` varchar(255),
                           `totalAmount` float,
                           `accountId` varchar(255),
                           `computerId` varchar(255),
                           FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
                           FOREIGN KEY (`computerId`) REFERENCES `computer` (`computerId`)
);

CREATE TABLE `invoicedetail` (
                                 `invoiceId` varchar(255) NOT NULL,
                                 `serviceId` varchar(255) NOT NULL,
                                 `quantity` int,
                                 `sellingPrice` float,
                                 PRIMARY KEY (`invoiceId`, `serviceId`),
                                 FOREIGN KEY (`invoiceId`) REFERENCES `invoice` (`invoiceId`),
                                 FOREIGN KEY (`serviceId`) REFERENCES `serviceitem` (`serviceId`)
);

CREATE TABLE `deposittransaction` (
                                      `transactionId` varchar(255) PRIMARY KEY,
                                      `amount` float,
                                      `depositTime` datetime(6),
                                      `accountId` varchar(255),
                                      `employeeId` varchar(255),
                                      FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
                                      FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`)
);

CREATE TABLE `sessionlog` (
                              `sessionId` varchar(255) PRIMARY KEY,
                              `startTime` datetime(6),
                              `endTime` datetime(6),
                              `deductedAmount` float,
                              `accountId` varchar(255),
                              `computerId` varchar(255),
                              FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
                              FOREIGN KEY (`computerId`) REFERENCES `computer` (`computerId`)
);

-- 3. CHÈN DỮ LIỆU

-- Chèn Zone
INSERT INTO `zone` VALUES ('Z01', 8000, 'Khu Thường'), ('Z02', 15000, 'Khu VIP'), ('Z03', 20000, 'Khu Thi Đấu (Esports)');

-- Chèn Account
INSERT INTO `account` (`accountId`, `balance`, `password`, `role`, `username`) VALUES
-- Nhóm Quản lý & Nhân viên
('ACC_ADMIN', 0, 'admin@2026', 'Admin', 'admin'),
('ACC_EMP01', 0, 'nv1@pass', 'Employee', 'nhanvien1'),
('ACC_EMP02', 0, 'nv2@pass', 'Employee', 'nhanvien2'),
('ACC_EMP03', 0, 'nv3@pass', 'Employee', 'nhanvien3'),

-- Nhóm Khách hàng (Member)
('ACC_MEM01', 50000, 'nhan@2026', 'Member', 'nhan_sv_ute'),
('ACC_MEM02', 15000, 'kiet!ute', 'Member', 'hcmute_sv'),
('ACC_MEM03', 100000, 'tungpro99', 'Member', 'thanh_vip'),
('ACC_MEM04', 20000, 'hoang1234', 'Member', 'hoang_pro'),
('ACC_MEM05', 0, 'dung_tk99', 'Member', 'dung_tk'),
('ACC_MEM06', 35000, 'linhlinh', 'Member', 'linh_ute'),
('ACC_MEM07', 50000, 'nam2000', 'Member', 'nam_deptrai'),
('ACC_MEM08', 150000, 'khanhpro', 'Member', 'khanh_gamer'),
('ACC_MEM09', 10000, 'phuclol_1', 'Member', 'phuc_lol'),
('ACC_MEM10', 80000, 'tuananh99', 'Member', 'tuan_anh99'),
('ACC_MEM11', 40000, 'huygaming', 'Member', 'huy_gaming'),
('ACC_MEM12', 60000, 'ngocngoc', 'Member', 'bao_ngoc'),
('ACC_MEM13', 0, 'haiquang!', 'Member', 'quang_hai'),
('ACC_MEM14', 25000, 'khoi2026', 'Member', 'minh_khoi'),
('ACC_MEM15', 120000, 'thangduc', 'Member', 'duc_thang'),
('ACC_MEM16', 30000, 'nhungthao', 'Member', 'thao_nhung'),
('ACC_MEM17', 15000, 'dattien1', 'Member', 'tien_dat'),
('ACC_MEM18', 45000, 'danghai2', 'Member', 'hai_dang'),
('ACC_MEM19', 90000, 'baoquoc3', 'Member', 'quoc_bao'),
('ACC_MEM20', 20000, 'tuanh456', 'Member', 'anh_tu');

-- Chèn Employee & Customer
INSERT INTO `employee` (`employeeId`, `employeeName`, `shift`, `accountId`) VALUES
                                                                                ('EMP_001', 'Trần Tiến Đạt', 'Quản lý', 'ACC_ADMIN'),
                                                                                ('EMP_002', 'Nhân Nguyễn', 'Ca Sáng', 'ACC_EMP01'),
                                                                                ('EMP_003', 'Lê Thị Thu Thảo', 'Ca Chiều', 'ACC_EMP02'),
                                                                                ('EMP_004', 'Phạm Hoàng Long', 'Ca Tối', 'ACC_EMP03');

-- Chèn khách hàng tương ứng Account
INSERT INTO `customer` (`customerId`, `customerName`, `phoneNumber`, `accountId`) VALUES
                                                                                      ('CUS_01', 'Nguyễn Hiếu Kì Nhân', '0901000001', 'ACC_MEM01'),
                                                                                      ('CUS_02', 'Lí Hào Kiệt', '0901000002', 'ACC_MEM02'),
                                                                                      ('CUS_03', 'Lê Thanh Tùng', '0901000003', 'ACC_MEM03'),
                                                                                      ('CUS_04', 'Phạm Minh Hoàng', '0901000004', 'ACC_MEM04'),
                                                                                      ('CUS_05', 'Trần Trí Dũng', '0901000005', 'ACC_MEM05'),
                                                                                      ('CUS_06', 'Nguyễn Thùy Linh', '0901000006', 'ACC_MEM06'),
                                                                                      ('CUS_07', 'Lê Hoài Nam', '0901000007', 'ACC_MEM07'),
                                                                                      ('CUS_08', 'Bùi Quốc Khánh', '0901000008', 'ACC_MEM08'),
                                                                                      ('CUS_09', 'Vũ Hoàng Phúc', '0901000009', 'ACC_MEM09'),
                                                                                      ('CUS_10', 'Đặng Tuấn Anh', '0901000010', 'ACC_MEM10'),
                                                                                      ('CUS_11', 'Đỗ Gia Huy', '0901000011', 'ACC_MEM11'),
                                                                                      ('CUS_12', 'Nguyễn Bảo Ngọc', '0901000012', 'ACC_MEM12'),
                                                                                      ('CUS_13', 'Hồ Quang Hải', '0901000013', 'ACC_MEM13'),
                                                                                      ('CUS_14', 'Ngô Minh Khôi', '0901000014', 'ACC_MEM14'),
                                                                                      ('CUS_15', 'Trương Đức Thắng', '0901000015', 'ACC_MEM15'),
                                                                                      ('CUS_16', 'Lý Thảo Nhung', '0901000016', 'ACC_MEM16'),
                                                                                      ('CUS_17', 'Đoàn Tiến Đạt', '0901000017', 'ACC_MEM17'),
                                                                                      ('CUS_18', 'Vương Hải Đăng', '0901000018', 'ACC_MEM18'),
                                                                                      ('CUS_19', 'Mai Quốc Bảo', '0901000019', 'ACC_MEM19'),
                                                                                      ('CUS_20', 'Trịnh Anh Tú', '0901000020', 'ACC_MEM20');

-- Chèn máy tính theo 3 Zone (Z01, Z02, Z03)
INSERT INTO `computer` (`computerId`, `computerName`, `status`, `zoneId`) VALUES
-- Khu Thường (Z01): 10 máy, PC_04 và PC_09 bảo trì
('PC_01', 'Máy Thường 01', 'Available', 'Z01'),
('PC_02', 'Máy Thường 02', 'Available', 'Z01'),
('PC_03', 'Máy Thường 03', 'Available', 'Z01'),
('PC_04', 'Máy Thường 04', 'Maintenance', 'Z01'),
('PC_05', 'Máy Thường 05', 'Available', 'Z01'),
('PC_06', 'Máy Thường 06', 'Available', 'Z01'),
('PC_07', 'Máy Thường 07', 'Available', 'Z01'),
('PC_08', 'Máy Thường 08', 'Available', 'Z01'),
('PC_09', 'Máy Thường 09', 'Maintenance', 'Z01'),
('PC_10', 'Máy Thường 10', 'Available', 'Z01'),

-- Khu VIP (Z02): 6 máy, PC_13 bảo trì
('PC_11', 'Máy VIP 01', 'Available', 'Z02'),
('PC_12', 'Máy VIP 02', 'Available', 'Z02'),
('PC_13', 'Máy VIP 03', 'Maintenance', 'Z02'),
('PC_14', 'Máy VIP 04', 'Available', 'Z02'),
('PC_15', 'Máy VIP 05', 'Available', 'Z02'),
('PC_16', 'Máy VIP 06', 'Available', 'Z02'),

-- Khu Thi Đấu (Z03): 4 máy, PC_18 bảo trì
('PC_17', 'Máy Thi Đấu 01', 'Available', 'Z03'),
('PC_18', 'Máy Thi Đấu 02', 'Maintenance', 'Z03'),
('PC_19', 'Máy Thi Đấu 03', 'Available', 'Z03'),
('PC_20', 'Máy Thi Đấu 04', 'Available', 'Z03');

-- Chèn Thực đơn
INSERT INTO `serviceitem` VALUES
                              ('SVC_01', 15000, 'Nước Tăng Lực Sting', 'Thức uống', 100, 'sting.jpg'),
                              ('SVC_02', 10000, 'Nước Lọc Aquafina', 'Thức uống', 50, 'aquafina.jpg'),
                              ('SVC_03', 25000, 'Mì Tôm Trứng Xúc Xích', 'Đồ ăn', 30, 'mi.jpg'),
                              ('SVC_04', 35000, 'Cơm Chiên Dương Châu', 'Đồ ăn', 20, 'comchien.jpg'),
                              ('SVC_05', 50000, 'Thẻ Garena 50k', 'Thẻ game', 200, 'garena50K.jpg');

SET FOREIGN_KEY_CHECKS = 1;