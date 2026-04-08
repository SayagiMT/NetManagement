    package com.NetProject.entity;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Id;

    // Xây dựng lớp đối tượng dịch vụ (Thực thể: DICHVU)
    @Entity
    public class ServiceItem {
        private String serviceId;
        private String serviceName;
        private String serviceType;
        private Float price;
        private Integer stockQuantity;
        @Column(name = "imagePath")
        private String imagePath;

        public ServiceItem() {
        }

        public ServiceItem(String serviceId, String serviceName, String serviceType, Float price, Integer stockQuantity, String imagePath) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.serviceType = serviceType;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.imagePath = imagePath;
        }

        // Mã dịch vụ: String (Thuộc tính khóa - Primary Key)
        @Id
        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        // Tên dịch vụ: String (Thuộc tính mô tả)
        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        // Loại dịch vụ: String <Đồ ăn, Thức uống, Thẻ game> (Thuộc tính mô tả)
        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        // Đơn giá: float (Thuộc tính mô tả)
        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        // Số lượng tồn: int (Thuộc tính mô tả)
        public Integer getStockQuantity() {
            return stockQuantity;
        }

        public void setStockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }