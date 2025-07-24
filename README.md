# 💃 Reservation System - Backend

A modern reservation system **engineered with Domain-Driven Design (DDD) and Clean Architecture** powered by **Java 21** and **Spring Boot 3**, featuring RBAC + ABAC, membership management, class scheduling, and many more.

[![Last Commit](https://img.shields.io/github/last-commit/Dance-reservation-system/Reservation-system?style=flat-square&color=darkgreen&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/commits)

[![Open Issues](https://img.shields.io/github/issues/Dance-reservation-system/Reservation-system?style=flat-square&color=red&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/issues)

[![Open PRs](https://img.shields.io/github/issues-pr/Dance-reservation-system/Reservation-system?style=flat-square&color=teal&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/pulls)

[![BE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/Reservation-system?style=flat-square&color=darkgreen&label=BE%20Contributors&logo=github)](https://github.com/Dance-reservation-system/Reservation-system/graphs/contributors)
[![FE Contributors](https://img.shields.io/github/contributors/Dance-reservation-system/reservation-system-web?style=flat-square&color=blue&label=FE%20Contributors&logo=github)](https://github.com/Dance-reservation-system/reservation-system-web/graphs/contributors)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Dance-reservation-system_Kamann-modular&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Dance-reservation-system_Kamann-modular)

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

## 🌟 Features by Role

<details>
<summary>Click to expand detailed feature list per system role</summary>

---

### 🛡️ Owner

- Create and manage system users (Instructor, Receptionist, Client) using Keycloak ID
- Assign and modify user roles within domain constraints
- Configure global studio settings:
  - Business hours
  - Cancellation policy
  - Studio contact information
- Manage studio halls:
  - Create and update rooms
  - Set capacity
  - Deactivate rooms for maintenance
- Define membership card types (e.g. 1-entry, 4-entry, monthly)
- Set rules for entry limits and expiration policies
- Override or delete class sessions
- Generate reports on attendance and instructor activity

---

### 🧾 Receptionist

- View today’s full session list with instructors, rooms, and booking stats
- Check in clients at the front desk (manual attendance logging)
- Cancel client reservations before session start (according to policy)
- Verify membership validity at check-in (via membership module)
- Search for clients and view reservation details
- Mark sessions as "in progress" when the instructor arrives
- Assist clients with on-site reservation management

---

### 🕺 Instructor

- Create class sessions (title, type, room, capacity, time range)
- Modify own upcoming sessions (if no clients are registered)
- Cancel sessions that haven’t started
- View teaching schedule (past and upcoming)
- Assign available halls for their own sessions

---

### 💃 Client

- Browse available sessions by date, instructor, or class type
- Reserve sessions (only with valid membership card)
- Cancel reservations before cancellation window closes
- Receive confirmation and alerts for booking and membership status
- View full history of past and future reservations
- Track active membership card (expiration, entries left)

</details>

---

### 📂 Project Structure

This project follows a **Modular Monolith** architecture based on **DDD Bounded Contexts**.  
Each module encapsulates a specific business capability and exposes only its application layer.


| Module          | Description |
|------------------|-------------|
| `useraccess`     | Manages system users, roles, and identity integration via Keycloak |
| `client`         | Handles client identity, reservation rights, and membership access |
| `instructor`     | Contains instructor details and teaching-related data |
| `owner`          | Studio administrator: manages rooms, membership types, and policies |
| `event`          | Manages class sessions and occurrences (acts as the scheduling domain) |
| `reservation`    | Encapsulates the reservation lifecycle and constraints |
| `attendance`     | Tracks client attendance per session occurrence |
| `membership`     | Issues and manages membership cards with entry limits and expiration |
| `payment`        | Handles payments for reservations, including lifecycle and events |
| `reception`      | Records receptionist actions (e.g. manual check-ins, cancellations) |



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
**[https://github.com/Dance-reservation-system/reservation-system-web](https://github.com/Dance-reservation-system/reservation-system-web)**

### 📝 Ideas

🎫 Membership expiration reminders

🕒 Schedule conflict detection

📊 PDF report generation

📧 SMTP integration for notifications

📈 Prometheus/Grafana monitoring

🔍 Query performance optimization

### 📜 License

This project is licensed under the MIT License.
