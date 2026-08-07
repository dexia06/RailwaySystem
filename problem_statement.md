# Problem Statement

## 1. Title

SmartRail: Intelligent Railway Level Crossing Safety & Accident Prevention System

## 2. Domain

Transportation / Railway Safety / Smart Transportation

## 3. Who is the user?

### 1. Railway Administrator
Responsible for managing railway stations, level crossings, users, railway gates, system configurations, alerts, and monitoring overall system activities.

### 2. Railway Officer / Signal Controller
Responsible for monitoring approaching trains, level crossing status, gate status, crossing occupancy, safety alerts, and responding to critical situations.

### 3. Maintenance Staff
Responsible for handling reported gate faults, updating maintenance status, recording repair activities, and marking issues as resolved.

## 4. What problem are we solving?

Railway level crossings can become dangerous when a train approaches while the crossing gate is still open or when vehicles and pedestrians are still passing through the crossing. Closing the gate immediately without checking whether the crossing area is clear can also create a risk of vehicles or pedestrians being trapped inside the crossing area.
The proposed system aims to provide an intelligent software-based safety platform that monitors approaching trains and level crossing conditions. When a train reaches the configured 1.5 km detection point, the system automatically initiates the gate-closing workflow and checks whether vehicles or pedestrians are present in the crossing area.
If the crossing is occupied, the system provides a loud warning and a 10-second countdown to allow vehicles and pedestrians to clear the crossing safely. When the train reaches the 1 km verification point, the system again checks whether the gate has actually reached the CLOSED state. If the gate is not closed, a critical alert is generated and sent to the responsible railway officer for immediate attention.
The system is designed as a software prototype where train detection, crossing occupancy, and gate status can be simulated through the application. It can be extended in the future to receive data from certified railway sensors and hardware systems.

## 5. Proposed Solution

SmartRail will provide a centralized full-stack platform for intelligent railway level crossing safety management.
The system will:
- Simulate train detection at a configurable 1.5 km distance.
- Automatically initiate the gate-closing workflow when an approaching train is detected.
- Check whether vehicles or pedestrians are present in the crossing area before gate closure.
- Generate an audible warning when the crossing is occupied.
- Provide a 10-second countdown for vehicles and pedestrians to clear the crossing.
- Automatically update the gate status after the safety-check process.
- Perform a second gate-status verification when the train reaches 1 km.
- Generate a critical alert if the gate is not closed during the 1 km verification.
- Provide real-time monitoring of train, gate, and crossing status through a dashboard.
- Allow railway officers to view and acknowledge critical alerts.
- Provide fault reporting and maintenance tracking for gate-related problems.
- Maintain a complete history of detection events, alerts, faults, and maintenance activities.
- Provide role-based access for administrators, railway officers, and maintenance staff.
- Support future integration with real railway sensor data and AI-based risk/fault prediction.

## 6. Core Entities / Database Tables

The system will contain the following core database tables:
1. User
2. RailwayStation
3. Train
4. LevelCrossing
5. RailwayGate
6. DetectionEvent
7. Alert
8. FaultReport
9. MaintenanceRecord
These entities will maintain relationships between railway users, stations, trains, level crossings, gates, detection events, alerts, faults, and maintenance activities.

## 7. User Roles & Permissions

### Admin

- Manage users and their roles.
- Manage railway stations and level crossings.
- Manage railway gate information.
- View all train and crossing activities.
- View and manage alerts.
- View fault and maintenance records.
- Monitor overall system activity.

### Railway Officer / Signal Controller

- Monitor approaching trains.
- Monitor train distance and status.
- Monitor level crossing and gate status.
- View vehicle/person detection status.
- Monitor the 10-second warning countdown.
- Receive and acknowledge critical gate alerts.
- Report gate-related faults.
- View operational safety events.

### Maintenance Staff

- View assigned gate fault reports.
- Update maintenance status.
- Add maintenance and repair details.
- Mark faults as resolved.
- View previous maintenance history.

## 8. Success Criteria

The project will be considered successful when:
- Users can securely log in and access features according to their assigned roles.
- The system can simulate an approaching train and trigger the gate-closing workflow at 1.5 km.
- The system can simulate and record vehicle/person detection at a level crossing.
- The system provides a 10-second warning countdown when the crossing is occupied.
- The system can update and display the railway gate status.
- The system performs a second gate-status verification at 1 km.
- The system generates a critical alert when the gate is not closed during the 1 km verification.
- At least two major workflows operate successfully from React frontend through Spring Boot backend to MySQL database.
- The system maintains proper relationships between at least five database entities.
- Railway officers can view critical alerts through the dashboard.
- The application can be deployed and accessed through a public URL by Review-II.
- The system provides a clear foundation for future sensor and AI integration.

## 9. Out of Scope

The following features are outside the scope of the initial software version:
- Direct control of real railway gates or signalling hardware.
- Direct connection to live Indian Railways signalling infrastructure.
- Physical installation of railway sensors, cameras, motors, or gates.
- Real-world train control or emergency braking.
- Physical detection of vehicles and pedestrians using hardware sensors.
- Complete railway timetable and reservation management.
- Large-scale integration with the national railway network.
- Replacement of certified railway safety and signalling systems.
The initial version will use software-simulated train, gate, and crossing detection data for demonstration and testing.

## 10. Chosen Track

Track: Java (Spring Boot)
Frontend: React.js
Backend: Spring Boot 3.x with Java 17
Database: MySQL 8
ORM: Spring Data JPA + Hibernate
Authentication: Spring Security + JWT
Build Tool: Maven
API Documentation: Swagger / OpenAPI
Real-Time Communication: WebSocket / STOMP
Testing: JUnit 5
Third-Party Integration: Email notification service for critical officer alerts
CI/CD: GitHub Actions