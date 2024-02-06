# Build Stage
FROM openjdk:17-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

# Runtime Stage
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY mybatis-config.xml /app
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app/app.jar"]
