# ================
# 1. Build Stage
# ================
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# ================
# 2. Run Stage
# ================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
