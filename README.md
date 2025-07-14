# 🗓️ Scheduling App

A Spring Boot-based scheduling system that allows users to:

- Add persons
- Schedule 1-hour meetings (on-the-hour)
- View individual schedules
- Suggest common available time slots for participants

---

## 📦 Features

✅ Create Persons  
✅ Create Meetings with One or More Persons  
✅ Enforce 1-Hour Meetings at the Hour Mark  
✅ View Person's Schedule  
✅ Suggest Available Timeslots  
✅ Global Exception Handling  
✅ In-memory H2 Database  
✅ Unit and Integration Test Coverage

---

## 🚀 Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 In-Memory Database
- Maven
- JUnit 5 + MockMvc

---

## 🔧 Prerequisites

Ensure the following are installed:

- Java 17+
- Maven 3.6+
- Git (optional)
- Postman (for testing REST APIs)

---

## 🛠️ How to Build and Run

### Clone the Repository
git clone https://github.com/your-username/scheduler-app.git
cd scheduler-app

## Build The project 
mvn clean install

### Run the Application
Using Maven Spring Boot Plugin
mvn spring-boot:run

### Testing Run all unit and integration tests:
mvn test