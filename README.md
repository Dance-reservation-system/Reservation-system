# 💃 Reservation System - Backend

A modern reservation system **engineered with Domain-Driven Design (DDD) and Clean Architecture** powered by **Java 21** and **Spring Boot 3**, featuring RBAC + ABAC, membership management, class scheduling, and many more.

[![Last Commit](https://img.shields.io/github/last-commit/Dance-reservation-system/Reservation-system?style=flat-square&color=green&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/commits)

[![Open Issues](https://img.shields.io/github/issues/Dance-reservation-system/Reservation-system?style=flat-square&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/issues)

[![Open PRs](https://img.shields.io/github/issues-pr/Dance-reservation-system/Reservation-system?style=flat-square&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/pulls)

[![BE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/Reservation-system?style=flat-square&color=green&label=BE%20Contributors&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/graphs/contributors)
[![FE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/reservation-system-web?style=flat-square&color=blue&label=FE%20Contributors&logo=github)](https://github.com/Dance-reservation-system/reservation-system-web/graphs/contributors)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)

## ⚙️ Tech Stack

<details>
<summary>Click to expand full stack</summary>

### Core
![Java](https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.0-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

### Architecture
![DDD](https://img.shields.io/badge/DDD-Domain--Driven--Design-blueviolet?style=flat-square)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-lightgrey?style=flat-square)

### DB + Migration
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-10.13.0-CC0200?style=flat-square&logo=flyway&logoColor=white)

### Security & Access
![Keycloak](https://img.shields.io/badge/Keycloak-IdP_Authz_Identity-0075A8?style=flat-square&logo=keycloak&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-Enabled-orange?style=flat-square&logo=oauth)
![OpenID](https://img.shields.io/badge/OpenID%20Connect-Supported-orange?style=flat-square&logo=openid)
![JWT](https://img.shields.io/badge/JWT-Authorization-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)

### Docs & DevOps
![Swagger](https://img.shields.io/badge/OpenAPI-3.0-6BA539?style=flat-square&logo=swagger&logoColor=black)
![CI/CD](https://img.shields.io/badge/GitHub_Actions-CI%2FCD-2088FF?style=flat-square&logo=githubactions&logoColor=white)
![Railway](https://img.shields.io/badge/Deployed%20to-Railway-0B0D0E?style=flat-square&logo=railway&logoColor=white)

</details>

---

## 🌟 Features

<details>
<summary>Click to view role-specific features</summary>

### 🛡️ Owner
- Manage user accounts (create, update, deactivate, assign roles)
- Configure studio settings (business hours, cancellation policy, contact info)
- Manage rooms (create, deactivate, assign to sessions)
- Define membership card types (entry limits, expiration rules)
- View schedule of all sessions and generate attendance reports
- Override or delete sessions (even if booked)

### 🧾 Receptionist
- View today’s session list with instructor, room, and number of bookings
- Assign or change rooms for upcoming sessions (if available)
- Cancel sessions according to policy
- Verify client membership card validity at check-in
- Search for clients and manage their reservations on request
- Mark a session as “in progress” when it starts

### 🕺 Instructor
- Create class sessions with title, type, date, time, duration, and hall assigned
- Edit upcoming sessions (start time, capacity, room)
- Cancel own sessions if no clients are registered
- View personal teaching schedule

### 💃 Client
- Browse upcoming sessions with filters (instructor, type, date)
- Reserve a session if a valid membership card is held
- Cancel reservations before the allowed deadline
- Receive booking confirmations and expiration alerts
- View full history of past and upcoming reservations

</details>

---

## 🚀 Installation

### 1. Clone Repository and Change into the project directory
  ```bash
  git clone https://github.com/Dance-reservation-system/Reservation-system.git
  cd Reservation-system
  ```

### See detailed environment setup instructions:
See [Keycloak Setup Guide](./docs/keycloak/manual-client-secret-setup.md)

### See contribution guidelines:
See [Contribution Guide](./docs/contribution-en.md)

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
