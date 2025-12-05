# =====================
# 1. BUILD STAGE
# =====================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy everything (pom + src)
COPY . .

# Clean + build the JAR
RUN mvn clean package -DskipTests

# =====================
# 2. RUNTIME STAGE
# =====================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
