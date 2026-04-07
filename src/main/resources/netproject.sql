-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: netproject
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `accountId` varchar(255) NOT NULL,
  `balance` float DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('ACC_ADMIN',0,'admin@2026','Admin','admin'),('ACC_EMP01',0,'nv1@pass','Employee','nhanvien1'),('ACC_EMP02',0,'nv2@pass','Employee','nhanvien2'),('ACC_EMP03',0,'nv3@pass','Employee','nhanvien3'),('ACC_MEM01',50000,'nhan@2026','Member','nhan_sv_ute'),('ACC_MEM02',15000,'kiet!ute','Member','hcmute_sv'),('ACC_MEM03',100000,'tungpro99','Member','thanh_vip'),('ACC_MEM04',20000,'hoang1234','Member','hoang_pro'),('ACC_MEM05',0,'dung_tk99','Member','dung_tk'),('ACC_MEM06',35000,'linhlinh','Member','linh_ute'),('ACC_MEM07',50000,'nam2000','Member','nam_deptrai'),('ACC_MEM08',150000,'khanhpro','Member','khanh_gamer'),('ACC_MEM09',10000,'phuclol_1','Member','phuc_lol'),('ACC_MEM10',80000,'tuananh99','Member','tuan_anh99'),('ACC_MEM11',40000,'huygaming','Member','huy_gaming'),('ACC_MEM12',60000,'ngocngoc','Member','bao_ngoc'),('ACC_MEM13',0,'haiquang!','Member','quang_hai'),('ACC_MEM14',25000,'khoi2026','Member','minh_khoi'),('ACC_MEM15',120000,'thangduc','Member','duc_thang'),('ACC_MEM16',30000,'nhungthao','Member','thao_nhung'),('ACC_MEM17',15000,'dattien1','Member','tien_dat'),('ACC_MEM18',45000,'danghai2','Member','hai_dang'),('ACC_MEM19',90000,'baoquoc3','Member','quoc_bao'),('ACC_MEM20',20000,'tuanh456','Member','anh_tu');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `computer`
--

DROP TABLE IF EXISTS `computer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `computer` (
  `computerId` varchar(255) NOT NULL,
  `computerName` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `zoneId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`computerId`),
  KEY `FKotrdfrsqcdxikse2uvaccepoh` (`zoneId`),
  CONSTRAINT `FKotrdfrsqcdxikse2uvaccepoh` FOREIGN KEY (`zoneId`) REFERENCES `zone` (`zoneId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computer`
--

LOCK TABLES `computer` WRITE;
/*!40000 ALTER TABLE `computer` DISABLE KEYS */;
INSERT INTO `computer` VALUES ('PC_01','Máy Thường 01','Available','Z01'),('PC_02','Máy Thường 02','Available','Z01'),('PC_03','Máy Thường 03','Available','Z01'),('PC_04','Máy Thường 04','Maintenance','Z01'),('PC_05','Máy Thường 05','Available','Z01'),('PC_06','Máy Thường 06','Available','Z01'),('PC_07','Máy Thường 07','Available','Z01'),('PC_08','Máy Thường 08','Available','Z01'),('PC_09','Máy Thường 09','Maintenance','Z01'),('PC_10','Máy Thường 10','Available','Z01'),('PC_11','Máy VIP 01','Available','Z02'),('PC_12','Máy VIP 02','Available','Z02'),('PC_13','Máy VIP 03','Maintenance','Z02'),('PC_14','Máy VIP 04','Available','Z02'),('PC_15','Máy VIP 05','Available','Z02'),('PC_16','Máy VIP 06','Available','Z02'),('PC_17','Máy Thi Đấu 01','Available','Z03'),('PC_18','Máy Thi Đấu 02','Maintenance','Z03'),('PC_19','Máy Thi Đấu 03','Available','Z03'),('PC_20','Máy Thi Đấu 04','Available','Z03');
/*!40000 ALTER TABLE `computer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerId` varchar(255) NOT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `UK_71r4tn1xm2612t4kkgpp79o3n` (`accountId`),
  CONSTRAINT `FK94bqsq44lb8q1t7yg3p4xbw7r` FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('CUS_01','Nguyễn Hiếu Kì Nhân','0901000001','ACC_MEM01'),('CUS_02','Lí Hào Kiệt','0901000002','ACC_MEM02'),('CUS_03','Lê Thanh Tùng','0901000003','ACC_MEM03'),('CUS_04','Phạm Minh Hoàng','0901000004','ACC_MEM04'),('CUS_05','Trần Trí Dũng','0901000005','ACC_MEM05'),('CUS_06','Nguyễn Thùy Linh','0901000006','ACC_MEM06'),('CUS_07','Lê Hoài Nam','0901000007','ACC_MEM07'),('CUS_08','Bùi Quốc Khánh','0901000008','ACC_MEM08'),('CUS_09','Vũ Hoàng Phúc','0901000009','ACC_MEM09'),('CUS_10','Đặng Tuấn Anh','0901000010','ACC_MEM10'),('CUS_11','Đỗ Gia Huy','0901000011','ACC_MEM11'),('CUS_12','Nguyễn Bảo Ngọc','0901000012','ACC_MEM12'),('CUS_13','Hồ Quang Hải','0901000013','ACC_MEM13'),('CUS_14','Ngô Minh Khôi','0901000014','ACC_MEM14'),('CUS_15','Trương Đức Thắng','0901000015','ACC_MEM15'),('CUS_16','Lý Thảo Nhung','0901000016','ACC_MEM16'),('CUS_17','Đoàn Tiến Đạt','0901000017','ACC_MEM17'),('CUS_18','Vương Hải Đăng','0901000018','ACC_MEM18'),('CUS_19','Mai Quốc Bảo','0901000019','ACC_MEM19'),('CUS_20','Trịnh Anh Tú','0901000020','ACC_MEM20');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deposittransaction`
--

DROP TABLE IF EXISTS `deposittransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deposittransaction` (
  `transactionId` varchar(255) NOT NULL,
  `amount` float DEFAULT NULL,
  `depositTime` datetime(6) DEFAULT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  `employeeId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`transactionId`),
  KEY `FKcl2h1x2c1o5v8ai49emwp38fm` (`accountId`),
  KEY `FKp6q0do6s147iuysvv8snneyeg` (`employeeId`),
  CONSTRAINT `FKcl2h1x2c1o5v8ai49emwp38fm` FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
  CONSTRAINT `FKp6q0do6s147iuysvv8snneyeg` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposittransaction`
--

LOCK TABLES `deposittransaction` WRITE;
/*!40000 ALTER TABLE `deposittransaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposittransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` varchar(255) NOT NULL,
  `employeeName` varchar(255) DEFAULT NULL,
  `shift` varchar(255) DEFAULT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `UK_cnxvi51679x9nhusv8dqlwdpt` (`accountId`),
  CONSTRAINT `FKcs7jiub5myswnmgqtnp1uj1fi` FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('EMP_001','Trần Tiến Đạt','Quản lý','ACC_ADMIN'),('EMP_002','Nhân Nguyễn','Ca Sáng','ACC_EMP01'),('EMP_003','Lê Thị Thu Thảo','Ca Chiều','ACC_EMP02'),('EMP_004','Phạm Hoàng Long','Ca Tối','ACC_EMP03');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `invoiceId` varchar(255) NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `totalAmount` float DEFAULT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  `computerId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`invoiceId`),
  KEY `FKee9aq8vkiqi9xerfh1n901yk` (`accountId`),
  KEY `FKmhlwjt2ll4e2gnxrx2i3yfljd` (`computerId`),
  CONSTRAINT `FKee9aq8vkiqi9xerfh1n901yk` FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
  CONSTRAINT `FKmhlwjt2ll4e2gnxrx2i3yfljd` FOREIGN KEY (`computerId`) REFERENCES `computer` (`computerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicedetail`
--

DROP TABLE IF EXISTS `invoicedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoicedetail` (
  `quantity` int DEFAULT NULL,
  `sellingPrice` float DEFAULT NULL,
  `invoiceId` varchar(255) NOT NULL,
  `serviceId` varchar(255) NOT NULL,
  PRIMARY KEY (`invoiceId`,`serviceId`),
  KEY `FKskw96644dyjvd2rrso37unlys` (`serviceId`),
  CONSTRAINT `FKb5m8jjhsq5jxvhdbwty3d05sq` FOREIGN KEY (`invoiceId`) REFERENCES `invoice` (`invoiceId`),
  CONSTRAINT `FKskw96644dyjvd2rrso37unlys` FOREIGN KEY (`serviceId`) REFERENCES `serviceitem` (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoicedetail`
--

LOCK TABLES `invoicedetail` WRITE;
/*!40000 ALTER TABLE `invoicedetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoicedetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceitem`
--

DROP TABLE IF EXISTS `serviceitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceitem` (
  `serviceId` varchar(255) NOT NULL,
  `price` float DEFAULT NULL,
  `serviceName` varchar(255) DEFAULT NULL,
  `serviceType` varchar(255) DEFAULT NULL,
  `stockQuantity` int DEFAULT NULL,
  PRIMARY KEY (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceitem`
--

LOCK TABLES `serviceitem` WRITE;
/*!40000 ALTER TABLE `serviceitem` DISABLE KEYS */;
INSERT INTO `serviceitem` VALUES ('SVC_01',15000,'Nước Tăng Lực Sting','Thức uống',100),('SVC_02',10000,'Nước Lọc Aquafina','Thức uống',50),('SVC_03',25000,'Mì Tôm Trứng Xúc Xích','Đồ ăn',30),('SVC_04',35000,'Cơm Chiên Dương Châu','Đồ ăn',20),('SVC_05',50000,'Thẻ Garena 50k','Thẻ game',200);
/*!40000 ALTER TABLE `serviceitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessionlog`
--

DROP TABLE IF EXISTS `sessionlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessionlog` (
  `sessionId` varchar(255) NOT NULL,
  `deductedAmount` float DEFAULT NULL,
  `endTime` datetime(6) DEFAULT NULL,
  `startTime` datetime(6) DEFAULT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  `computerId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sessionId`),
  KEY `FK3klni0xhkf4up15kljc20dp6d` (`accountId`),
  KEY `FKecx42ve25olnvskojxd1q7fc2` (`computerId`),
  CONSTRAINT `FK3klni0xhkf4up15kljc20dp6d` FOREIGN KEY (`accountId`) REFERENCES `account` (`accountId`),
  CONSTRAINT `FKecx42ve25olnvskojxd1q7fc2` FOREIGN KEY (`computerId`) REFERENCES `computer` (`computerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessionlog`
--

LOCK TABLES `sessionlog` WRITE;
/*!40000 ALTER TABLE `sessionlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessionlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zone`
--

DROP TABLE IF EXISTS `zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zone` (
  `zoneId` varchar(255) NOT NULL,
  `hourlyRate` float DEFAULT NULL,
  `zoneName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`zoneId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zone`
--

LOCK TABLES `zone` WRITE;
/*!40000 ALTER TABLE `zone` DISABLE KEYS */;
INSERT INTO `zone` VALUES ('Z01',8000,'Khu Thường'),('Z02',15000,'Khu VIP'),('Z03',20000,'Khu Thi Đấu (Esports)');
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-05 16:20:16
