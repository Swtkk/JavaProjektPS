# JavaProjektPS – Store Management System

A Spring Boot application developed as a study project to manage store-related data. It leverages modern Java technologies and a relational database for backend logic and web presentation.

## 🧰 Technologies Used

- **Java 17**
- **Spring Boot 3.2**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring DevTools
  - Spring Thymeleaf
- **Lombok**
- **MySQL** 
- **Maven**
- **JUnit 5** (for testing)

## 🚀 Features

- RESTful web application
- Web UI with Thymeleaf
- Database integration via Spring Data JPA
- Input validation (server-side)
- Environment switch between MySQL
- DevTools support for hot reload

## 📦 Getting Started

### Prerequisites

- Java 17
- Maven 3.8+
- MySQL

### Clone & Run

```bash
git clone https://github.com/Swtkk/JavaProjektPS.git
cd JavaProjektPS

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
