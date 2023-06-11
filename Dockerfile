# Sử dụng hình ảnh cơ bản của Gradle và JDK 11
FROM gradle:jdk17 as builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file build.gradle và settings.gradle vào thư mục làm việc
COPY . /app

# Xây dựng ứng dụng bằng Gradle
RUN gradle build --no-daemon

# ---------------------------------------------------

# Sử dụng hình ảnh cơ sở Java để chạy ứng dụng đã được xây dựng
FROM openjdk:11-jre-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép tệp .jar đã được xây dựng từ builder stage vào thư mục làm việc
COPY --from=builder /app/build/libs/foodrec.jar foodrec.jar

# Đặt lệnh mặc định khi chạy container
ENTRYPOINT ["java", "-jar", "foodrec.jar"]
