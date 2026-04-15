# Project Status:

"This project is still on progress for building some features like jobs timeline, application for jobs, and as HR/as Applicant modules. The documentation only covers the early stage of the project built using v0, a collaborative AI Assistant owned by Vercel ". -Stanley Panag

# Applicant Management System (AMS)

A Spring Boot web application for managing applicants with user authentication, registration, and password recovery features.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Version Information](#version-information)
- [Dependencies](#dependencies)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Environment Variables](#environment-variables)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Applicant Management System (AMS) is a full-stack web application built with Spring Boot that provides user authentication, registration, and a dashboard for managing applicants. It features secure password handling, email-based password recovery, and a responsive UI using Thymeleaf templates with Tailwind CSS.

## Tech Stack

| Category   | Technology                 |
| ---------- | -------------------------- |
| Backend    | Java 17, Spring Boot 4.0.4 |
| Frontend   | Thymeleaf, Tailwind CSS    |
| Database   | PostgreSQL                 |
| Security   | Spring Security, BCrypt    |
| Email      | Spring Mail (Gmail SMTP)   |
| Build Tool | Maven                      |

## Version Information

| Component         | Version                  |
| ----------------- | ------------------------ |
| Java              | 17                       |
| Spring Boot       | 4.0.4                    |
| Maven             | 3.x                      |
| PostgreSQL Driver | (managed by Spring Boot) |

## Dependencies

### Core Dependencies

| Dependency                       | Purpose                                       |
| -------------------------------- | --------------------------------------------- |
| `spring-boot-starter-webmvc`     | Spring MVC for building web applications      |
| `spring-boot-starter-thymeleaf`  | Thymeleaf template engine integration         |
| `spring-boot-starter-data-jpa`   | JPA for database operations and ORM           |
| `spring-boot-starter-security`   | Authentication and authorization              |
| `spring-boot-starter-validation` | Bean validation for DTOs                      |
| `spring-boot-starter-mail`       | Email sending capabilities                    |
| `spring-security-crypto`         | Cryptographic operations for token generation |

### Runtime Dependencies

| Dependency             | Purpose                                |
| ---------------------- | -------------------------------------- |
| `postgresql`           | PostgreSQL JDBC driver                 |
| `spring-boot-devtools` | Development-time features (hot reload) |

### Test Dependencies

| Dependency                           | Purpose                        |
| ------------------------------------ | ------------------------------ |
| `spring-boot-starter-thymeleaf-test` | Testing Thymeleaf templates    |
| `spring-boot-starter-webmvc-test`    | Testing Spring MVC controllers |

## Project Structure

```
src/
├── main/
│   ├── java/com/lab/narra/ams/
│   │   ├── AmsApplication.java           # Main application entry point
│   │   ├── config/
│   │   │   └── SecurityConfig.java       # Spring Security configuration
│   │   ├── controller/
│   │   │   ├── user/
│   │   │   │   └── DashboardController.java
│   │   │   └── visitor/
│   │   │       ├── HomeController.java
│   │   │       ├── LoginController.java
│   │   │       └── RegisterController.java
│   │   ├── mapper/
│   │   │   ├── TokenMapper.java
│   │   │   └── UserMapper.java
│   │   ├── model/
│   │   │   ├── dto/
│   │   │   │   ├── TokenDTO.java
│   │   │   │   └── UserDTO.java
│   │   │   └── entity/
│   │   │       ├── Token.java
│   │   │       └── User.java
│   │   ├── repository/
│   │   │   ├── TokenRepository.java
│   │   │   └── UserRepository.java
│   │   └── service/
│   │       ├── email/
│   │       │   └── EmailService.java
│   │       ├── token/
│   │       │   ├── TokenCleanupScheduler.java
│   │       │   └── TokenServiceImp.java
│   │       └── user/
│   │           ├── CustomUserDetailsService.java
│   │           ├── UserService.java
│   │           └── UserServiceImp.java
│   └── resources/
│       ├── application.yml                # Application configuration
│       ├── static/
│       │   ├── css/
│       │   │   └── header.css
│       │   └── js/
│       │       ├── menu.js
│       │       ├── passwordVisibilityToggle.js
│       │       ├── registrationForm.js
│       │       ├── resetPassword.js
│       │       ├── tailwind.js
│       │       └── toastNotification.js
│       └── templates/
│           ├── email/                     # Email templates
│           ├── error/                     # Error pages
│           ├── fragments/                 # Reusable template fragments
│           └── module/                    # Page layouts
└── test/
    └── java/com/lab/narra/ams/
        ├── AmsApplicationTests.java
        └── service/user/
            └── UserServiceImpTest.java
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.x**
- **PostgreSQL** database server
- **Gmail account** with App Password enabled (for email functionality)

## Environment Variables

Create a `.env` file or set the following environment variables:

| Variable              | Description                 | Example                                   |
| --------------------- | --------------------------- | ----------------------------------------- |
| `DB_URL`              | PostgreSQL connection URL   | `jdbc:postgresql://localhost:5432/ams_db` |
| `GOOGLE_APP_PASSWORD` | Gmail App Password for SMTP | `xxxx xxxx xxxx xxxx`                     |

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/stan-asian/applicant-management-system.git
   cd applicant-management-system
   ```

2. **Set up PostgreSQL database**

   ```sql
   CREATE DATABASE ams_db;
   ```

3. **Configure environment variables**

   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/ams_db
   export GOOGLE_APP_PASSWORD=your-google-app-password
   ```

4. **Install dependencies**
   ```bash
   ./mvnw clean install
   ```

## Running the Application

### Development Mode

```bash
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`

### Production Build

```bash
./mvnw clean package
java -jar target/ams-0.0.1-SNAPSHOT.jar
```

## Features

- **User Registration** - Create new user accounts with email and password
- **User Authentication** - Secure login/logout with Spring Security
- **Password Recovery** - Email-based password reset with secure tokens
- **Dashboard** - Authenticated user dashboard
- **Token Management** - Automatic cleanup of expired tokens via scheduled tasks
- **Responsive UI** - Tailwind CSS for modern, responsive design

## API Endpoints

### Visitor (Public) Routes

| Method | Endpoint            | Description          |
| ------ | ------------------- | -------------------- |
| GET    | `/visitor/`         | Home page            |
| GET    | `/visitor/login`    | Login page           |
| POST   | `/login`            | Process login        |
| GET    | `/visitor/register` | Registration page    |
| POST   | `/visitor/register` | Process registration |

### User (Authenticated) Routes

| Method | Endpoint          | Description    |
| ------ | ----------------- | -------------- |
| GET    | `/user/dashboard` | User dashboard |

### Static Resources

| Path      | Description      |
| --------- | ---------------- |
| `/css/**` | CSS stylesheets  |
| `/js/**`  | JavaScript files |

## Database Schema

### Users Table (`users`)

| Column     | Type      | Constraints                 |
| ---------- | --------- | --------------------------- |
| id         | BIGINT    | PRIMARY KEY, AUTO_INCREMENT |
| email      | VARCHAR   | NOT NULL, UNIQUE            |
| password   | VARCHAR   | NOT NULL                    |
| first_name | VARCHAR   | -                           |
| last_name  | VARCHAR   | -                           |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW()     |

### Token Table (`token_tbl`)

| Column      | Type      | Constraints                 |
| ----------- | --------- | --------------------------- |
| id          | BIGINT    | PRIMARY KEY, AUTO_INCREMENT |
| token       | VARCHAR   | NOT NULL, UNIQUE            |
| expiry_date | TIMESTAMP | NOT NULL                    |
| user_id     | BIGINT    | FOREIGN KEY -> users(id)    |

## Security

- **Password Encoding** - BCrypt hashing for secure password storage
- **Session Management** - Spring Security session handling
- **CSRF Protection** - Enabled by default
- **Route Protection** - Role-based access control
  - `/visitor/**` - Public access
  - `/user/**` - Authenticated users only

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

**Developed by** [stan-asian](https://github.com/stan-asian)
