# Restaurant Hub - Users Service (9ADJT - TC1)

## Overview

Spring Boot microservice for managing user-related operations in the Restaurant Hub platform. This initial setup includes the minimal structure, dependencies, and configurations to start development.

## Tech Stack

- Java 21
- Spring Boot 3.5.4
- Maven
- Spring Data JDBC
- PostgreSQL
- SpringDoc OpenAPI 2.8.9
- Jacoco / Surefire (testing & coverage)

## Project Structure

├─ pom.xml  
├─ src/main/java/com/example/users/UsersApplication.java  
├─ src/main/resources/application.yml  
├─ .gitignore  
├─ README.md  

## Build & Run

```bash
mvn clean package
mvn spring-boot:run
