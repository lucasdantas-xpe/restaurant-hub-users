# 1) cache maven
FROM maven:3.9.9-eclipse-temurin-21 AS dependency-builder
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline

# 2) build app
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY --from=dependency-builder /root/.m2 /root/.m2
COPY src src
COPY pom.xml .
RUN mvn -B -DskipTests package

# 3) runtime JRE only
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s --retries=5 \
  CMD wget -qO- http://localhost:8080/actuator/health | grep '"status":"UP"' || exit 1
ENTRYPOINT ["java","-jar","/app/app.jar"]
