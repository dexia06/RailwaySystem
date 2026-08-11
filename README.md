# SmartRail: Intelligent Railway Level Crossing Safety & Accident Prevention System

## 1. Project Overview

SmartRail is a full-stack railway safety management system designed to improve the safety of railway level crossings.

The system monitors approaching trains, level crossing occupancy, railway gate status, and safety alerts. It provides an intelligent software-based workflow to reduce the risk of accidents at railway level crossings.

The initial version is a software prototype where train detection, crossing occupancy, and gate status are simulated through the application.

---

## 2. Problem Statement

Railway level crossings can become dangerous when a train approaches while the crossing gate is still open or when vehicles and pedestrians are still inside the crossing area.

SmartRail monitors approaching trains and level crossing conditions. When a train reaches the configured 1.5 km detection point, the system initiates the gate-closing workflow and checks whether the crossing area is occupied.

If the crossing is occupied, the system provides a warning and a 10-second countdown. At the 1 km verification point, the system checks whether the gate is CLOSED. If the gate is not closed, a critical alert is generated for the railway officer.

---

## 3. User Roles

### Railway Administrator

- Manage users and roles
- Manage railway stations and level crossings
- Manage railway gate information
- View alerts and system activities
- View fault and maintenance records

### Railway Officer / Signal Controller

- Monitor approaching trains
- Monitor train distance and status
- Monitor level crossing and gate status
- View vehicle/person detection status
- Receive and acknowledge critical alerts
- Report gate-related faults

### Maintenance Staff

- View assigned gate fault reports
- Update maintenance status
- Add repair details
- Mark faults as resolved
- View maintenance history

---

## 4. Features / Modules

- User Authentication and Role Management
- Train Monitoring
- Level Crossing Management
- Railway Gate Management
- Vehicle and Person Detection
- Safety Warning and 10-Second Countdown
- Critical Alert Management
- Fault Reporting
- Maintenance Management
- Detection and Alert History
- System Monitoring Dashboard

---

## 5. Technology Stack

| Component | Technology |
|---|---|
| Frontend | React.js |
| Backend | Spring Boot 3.x |
| Programming Language | Java 17 |
| Database | MySQL 8 |
| ORM | Spring Data JPA + Hibernate |
| Authentication | Spring Security + JWT |
| Build Tool | Maven |
| API Documentation | Swagger / OpenAPI |
| Real-Time Communication | WebSocket / STOMP |
| Testing | JUnit 5 |

---

## 6. Project Structure

```text
RailwaySystem/
├── frontend/
├── backend/
├── database/
│
├── doc/
│   └── diagrams/
│       ├── ER Diagram.png
│       ├── Class Diagram.png
│       └── System Architecture.png
│
├── problem_statement.md
├── README.md
├── .gitignore
├── .env.example
├── CHANGELOG.md
└── LICENSE
## 8. How to Run Locally

### Prerequisites

- Java 17
- Maven
- Node.js and npm
- MySQL 8
- Git

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```
## 9. Core Workflow

```text
Approaching Train
       ↓
     1.5 km
       ↓
Check Crossing Occupancy
       ↓
Warning + 10-Second Countdown
       ↓
Gate Closing
       ↓
     1 km
       ↓
Verify Gate Status
       ↓
 ┌─────┴─────┐
 ↓           ↓
Closed    Not Closed
 ↓           ↓
Safe    Critical Alert
             ↓
      Railway Officer
```
## 10. Project Scope

The initial version of SmartRail is a software prototype intended for demonstration and testing.

The system simulates train detection, crossing occupancy, and railway gate status through the application.

The system does not directly control real railway gates, trains, signalling systems, or railway infrastructure.
## 11. Success Criteria

The project will be considered successful when:

- Users can securely log in according to their assigned roles.
- The system can simulate an approaching train at the 1.5 km detection point.
- The system can simulate vehicle/person detection at a level crossing.
- A 10-second warning countdown is provided when the crossing is occupied.
- The railway gate status can be updated and monitored.
- The system performs gate-status verification at the 1 km point.
- A critical alert is generated when the gate is not closed.
- Railway officers can view and acknowledge critical alerts.
- At least two major workflows operate from the React frontend through the Spring Boot backend to the MySQL database.
- The system maintains proper relationships between the core database entities.
- The application provides a foundation for future sensor and AI integration.
# 12. Expected Outcome

The SmartRail system is expected to:

* Improve safety at railway level crossings by providing timely warnings.
* Detect approaching trains and crossing occupancy through simulated inputs.
* Provide a 10-second warning countdown before a potential crossing accident.
* Monitor and verify the railway gate status at critical detection points.
* Generate critical alerts when the gate remains open during an approaching train.
* Help railway officers respond quickly to dangerous situations.
* Provide role-based access for administrators, railway officers, and maintenance staff.
* Maintain reliable communication between the React frontend, Spring Boot backend, and MySQL database.
* Provide a scalable foundation for integrating real-time sensors, IoT devices, and AI-based prediction in the future.
* Reduce the risk of accidents caused by human error or delayed gate operation.
# 13. Future Enhancement

The SmartRail system can be enhanced in the future with the following features:

* Integration of real-time railway sensors for accurate train detection.
* IoT-based level crossing monitoring using smart sensors and devices.
* AI and Machine Learning for accident prediction and risk analysis.
* Automatic railway gate control based on real-time train movement.
* GPS-based real-time train tracking and location monitoring.
* Mobile application for railway officers and maintenance staff.
* Real-time notifications through SMS, email, or mobile alerts.
* CCTV and computer vision integration for automatic vehicle and person detection.
* Cloud-based data storage and monitoring for centralized railway management.
* Advanced analytics and reports for identifying accident-prone level crossings.
