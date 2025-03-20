# NPP Village Management System

## Overview
This application was developed for the NPP political party of our village. It allows administrators to add and manage villagers' details efficiently. The system is built using **Spring Boot** for the backend and **Firebase Firestore** as the database.

## Features
- **Admin Dashboard**: Secure access for administrators to manage villagers' data.
- **Villager Management**: Add, and view details of villagers.
- **Firestore Database**: Fast and scalable data storage.
- **Spring Boot Backend**: Robust API implementation.

## Technologies Used
- **Backend**: Spring Boot
- **Database**: Firebase Firestore
- **Build Tool**: Maven
- **Version Control**: Git

## Installation & Setup
### Prerequisites
Ensure you have the following installed on your system:
- Java (JDK 11 or later)
- Maven
- Firebase Account (with Firestore enabled)

### Steps to Run
1. Clone the repository:
   ```sh
   https://github.com/sandaru-sdm/NPP.git
   cd NPP
   ```
2. Configure Firebase Firestore:
   - Set up a Firebase project.
   - Enable Firestore Database.
   - Update the Firebase credentials in `application.properties`.
3. Build and run the project:
   ```sh
   mvn spring-boot:run
   ```
4. Access the application via:
   ```sh
   http://localhost:8080
   ```
## License
This project is licensed under the MIT License. See `LICENSE` for more details.
