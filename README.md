# 💃 Reservation System - Backend

A modern reservation system **engineered with Domain-Driven Design (DDD) and Clean Architecture** powered by **Java 21** and **Spring Boot 3**, featuring RBAC + ABAC, membership management, class scheduling, and many more.

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven)
![JWT](https://img.shields.io/badge/JWT-000000?logo=jsonwebtokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?logo=swagger&logoColor=black)


![FE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/Kamann-web?color=blue&label=Active-FE)
![BE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/Kamann-modular?color=green&label=Active-BE)

[![Build with Tests](https://github.com/Dance-reservation-system/Kamann-modular/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/Dance-reservation-system/Kamann/actions/workflows/ci-cd.yml)
![GitHub last commit](https://img.shields.io/github/last-commit/Dance-reservation-system/Kamann-modular?color=green)
![Deploy - Railway](https://github.com/Dance-reservation-system/Kamann-modular/actions/workflows/main.yml/badge.svg)


## 🌟 Features

### 🛡️ Admin
- Full event lifecycle management
- User administration with activation/deactivation
- Financial reporting (weekly/monthly/yearly)
- Attendance analytics dashboard

### 🕺 Instructor
- Personal schedule management
- Real-time attendance tracking
- Membership validation system
- Class cancellation notifications

### 💃 Client
- Calendar for booking events
- Class booking system with membership integration
- Membership usage tracking
- Advanced event search filters

## 🛠️ Technologies

- **Architecture**: Domain-Driven Design • Clean Architecture
- **Core**: Java 21 • Spring Boot 3
- **Database**: PostgreSQL 16
- **Identity & Access**: Keycloak (central IdP — OpenID Connect, OAuth 2.0, RBAC + ABAC policies)
- **Security**: JWT tokens issued by Keycloak
- **API Docs**: Swagger / OpenAPI 3.0
- **CI/CD**: GitHub Actions
- **Deploy**: Railway (Cloud PaaS)


## 🚀 Installation

### 1. Clone Repository and change into the project directory
  ```bash
  git clone https://github.com/Dance-reservation-system/kamann-modular.git
  cd kamann/backend
  ```

### 2. Create the .env file out of .env-example
```bash
  mv .env-example .env
```

### 3. Containers Setup (App + PostgreSQL 16)
The containers contains:
- java application
- postgresql database

which means there is no need to setup anything more

```bash
docker compose up -d --build
```

## 📚 API Documentation
  Access interactive Swagger UI at:
  **http://localhost:8080/swagger-ui.html**

####  Registration Endpoint:
  **POST /api/v1/auth/register-customer** (requires confirm email)
  
  **POST /api/v1/auth/register-instructor** (requires admin confirmation)
  
  Request Body (using RegisterRequest):
```json
{
  "email": "customer@example.com",
  "password": "password",
  "firstName": "John",
  "lastName": "Doe",
  "phone": 123456789
}
```
  
####  Authorization: 
  Use JWT token from
  **POST /api/v1/auth/login**
  
  Request Body:
  ```json
  {
  "email": "customer@example.com",
  "password": "password"
  }
```


### 🚦 CI/CD Pipeline
  - Integration testing with H2 in-memory database (GithubActions Workflow)
  - Production deploys via Railway (Dedicated release branch)
  - Production secrets managed through GitHub Secrets + Railway Secrets
  

### 🖥️ Frontend
  Client available at:
**[https://github.com/Dance-reservation-system/Kamann-web](https://github.com/Dance-reservation-system/Kamann-web)**

### 📝 Ideas

🎫 Membership expiration reminders

🕒 Schedule conflict detection

📊 PDF report generation

📧 SMTP integration for notifications

📈 Prometheus/Grafana monitoring

🔍 Query performance optimization

### 📜 License

This project is licensed under the MIT License.
