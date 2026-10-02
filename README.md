# ResumePortal

ResumePortal is a full-stack backend application for managing user profiles and resumes.

The application provides secure authentication, profile management, PDF resume upload, AWS S3 storage, and PostgreSQL persistence.

---

## 🚀 Features

- User registration
- User login
- JWT authentication
- BCrypt password hashing
- User profile management
- Create profile
- View profile
- Update profile
- Delete profile
- Resume PDF upload
- Resume validation
- 5 MB upload limit
- AWS S3 resume storage
- Resume metadata stored in PostgreSQL
- View user's resumes
- Delete resumes
- RESTful APIs
- Global exception handling
- Docker support
- Maven build system

---

## 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Security
- Spring Data JPA
- Hibernate
- Jakarta Validation

### Database

- PostgreSQL 18

### Authentication

- JWT
- BCrypt

### Cloud Storage

- AWS S3
- AWS SDK for Java

### Build & Deployment

- Maven
- Docker
- Git
- GitHub

---

## 📁 Project Structure

```text
ResumePortal
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ranajeet
│   │   │           └── resumeportal
│   │   │               │
│   │   │               ├── ResumePortalApplication.java
│   │   │               │
│   │   │               ├── config
│   │   │               │   ├── AwsConfig.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── controller
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── ProfileController.java
│   │   │               │   └── ResumeController.java
│   │   │               │
│   │   │               ├── dto
│   │   │               │   ├── AuthResponse.java
│   │   │               │   ├── LoginRequest.java
│   │   │               │   └── RegisterRequest.java
│   │   │               │
│   │   │               ├── exception
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               │
│   │   │               ├── model
│   │   │               │   ├── User.java
│   │   │               │   ├── Profile.java
│   │   │               │   └── Resume.java
│   │   │               │
│   │   │               ├── repository
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── ProfileRepository.java
│   │   │               │   └── ResumeRepository.java
│   │   │               │
│   │   │               ├── security
│   │   │               │   ├── JwtService.java
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   └── CustomUserDetailsService.java
│   │   │               │
│   │   │               └── service
│   │   │                   ├── AuthService.java
│   │   │                   ├── ProfileService.java
│   │   │                   ├── ResumeService.java
│   │   │                   └── S3Service.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│
├── Dockerfile
├── .gitignore
├── pom.xml
└── README.md