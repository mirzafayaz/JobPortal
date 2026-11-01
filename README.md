JobPortal – Job Management Backend
A RESTful Job Portal backend application built using Java, Spring Boot, Spring Data JPA, and MySQL. This project provides APIs to create, manage, update, delete, search, filter, and retrieve job postings.
🚀 Technologies Used

Java
Spring Boot
Spring Data JPA
Hibernate
MySQL
Maven
REST API
Swagger / OpenAPI
JUnit 5
Mockito
IntelliJ IDEA
Postman

✨ Features

Create new job postings
Retrieve all available jobs
Retrieve a job using its ID
Update existing job details
Delete job postings
Search jobs by title
Search jobs by company
Search jobs by location
Filter jobs using salary range
Advanced search with multiple filters
Pagination support
Sorting support
Combined pagination and sorting
DTO-based request and response handling
Input validation
Global exception handling
Swagger API documentation
Unit testing with JUnit and Mockito

## 📁 Project Structure

```text
JobPortal/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/jobportal/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── JobPortalApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/example/jobportal/
│           ├── JobServiceTest.java
│           ├── JobControllerTest.java
│           └── JobPortalApplicationTests.java
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md

🔗 API Endpoints
Base URL: http://localhost:8080



Method
Endpoint
Description




POST
/api/jobs
Create a job


GET
/api/jobs
Get all jobs


GET
/api/jobs/{id}
Get job by ID


PUT
/api/jobs/{id}
Update a job


DELETE
/api/jobs/{id}
Delete a job


GET
/api/jobs/search?title=Java
Search by title


GET
/api/jobs/search/company?company=Google
Search by company


GET
/api/jobs/search/location?location=Bangalore
Search by location


GET
/api/jobs/search/salary?minSalary=500000&maxSalary=2000000
Filter by salary


GET
/api/jobs/advanced-search
Advanced job search


GET
/api/jobs/page?page=0&size=5
Pagination


GET
/api/jobs/sort?field=salary&direction=asc
Sorting


GET
/api/jobs/page-sort?page=0&size=3&field=salary&direction=desc
Pagination and sorting


GET
/api/jobs/{id}/dto
Get job response using DTO



🗄️ Database Configuration
This project uses MySQL.
Create the database:
CREATE DATABASE job_portal_db;

Configure your database credentials using environment variables.
In application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

Set the DB_PASSWORD environment variable to your local MySQL password before starting the application.
⚙️ How to Run the Project
1. Clone the repository
   git clone https://github.com/mirzafayaz/JobPortal.git

2. Open the project
   Open the cloned project in IntelliJ IDEA.
3. Configure MySQL
   Create the job_portal_db database and configure your database password.
4. Run the application
   On Windows:
   .\mvnw.cmd spring-boot:run

5. Access the application
   http://localhost:8080

📘 Swagger API Documentation
Swagger UI is available at:
http://localhost:8080/swagger-ui/index.html

Use Swagger UI to explore and test the available REST APIs.
🧪 Testing
The project includes unit tests using:

JUnit 5
Mockito
Spring Boot Test

Current test status: 21 tests passed.
Run tests using:
.\mvnw.cmd test

🎯 Project Objective
The objective of this project is to understand and implement backend development concepts using Spring Boot, REST APIs, database integration, JPA, exception handling, validation, API documentation, and unit testing.
👨‍💻 Author
Mirza Fayaz
GitHub: @mirzafayaz

⭐ If you find this project useful, feel free to explore the repository and provide feedback.
