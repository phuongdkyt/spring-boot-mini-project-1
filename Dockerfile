FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container là /app
WORKDIR /app

# Sao chép file jar đã build từ bước trước vào hình ảnh này
COPY targets/*.jar app.jar

# Định nghĩa lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
