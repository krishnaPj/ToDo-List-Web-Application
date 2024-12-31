# To-Do List Web Application

A feature-rich To-Do List web application designed for managing tasks efficiently. This project demonstrates the implementation of CRUD operations, authentication, and email-based password recovery using a Java-based backend with Jakarta Servlets and Hibernate.

## Features

- **User Authentication**: Secure login and registration using password hashing.
- **Task Management**: Add, view, and manage tasks in a centralized interface.
- **Password Recovery**: Reset forgotten passwords via email.
- **Responsive Design**: Optimized for all screen sizes.
- **Automated Cleanup**: Expired, unconfirmed user accounts are removed daily.

---

## Technologies Used

- **Frontend**: 
  - HTML5, CSS3, Bootstrap 5
  - Animate.css for animations
  - jQuery for interactivity

- **Backend**:
  - Java (Jakarta Servlets)
  - Hibernate ORM
  - MySQL Database

- **Email**: JavaMail API for sending emails.

- **Tools**:
  - Maven for dependency management.
  - SLF4J and Log4j for logging.

---

## Getting Started

Follow these steps to set up and run the project on your local environment.

### Prerequisites

1. Java Development Kit (JDK) 8 or higher.
2. Apache Maven.
3. MySQL database server.
4. An email account for sending recovery emails (e.g., Gmail).

---

### Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-username/todo-list.git
    cd todo-list
    ```

2. **Configure the Database**:
   - Update the database credentials in `persistence.xml` and `bundle.properties`:
     ```properties
     uriMySql=jdbc:mysql://127.0.0.1:3306/example?createDatabaseIfNotExist=true
     userMySql=root
     pswMySql=your-password
     ```

3. **Set Email Credentials**:
   - Update the email credentials in `bundle.properties`:
     ```properties
     userMail=your-email@example.com
     pswMail=your-email-password
     ```

4. **Build the Project**:
    ```bash
    mvn clean install
    ```

5. **Deploy on Server**:
   - Deploy the `.war` file on a servlet container like Apache Tomcat.

6. **Run the Application**:
   - Access the application at `http://localhost:8080/todo-list`.

---

## Usage

### 1. **Register and Login**:
   - Register a new account and log in to access your personal task list.

### 2. **Task Management**:
   - Add, view, and manage tasks seamlessly.

### 3. **Forgot Password**:
   - Recover your account by entering your registered email address.

### 4. **Admin Cleanup**:
   - Expired accounts are automatically removed by the `ExpiredUsersWorker`.

---

## Folder Structure

```plaintext
src/main
├── java/net
│   ├── core/             # Core utilities (e.g., Persistence, ExpiredUsersWorker)
│   ├── entities/         # JPA entities
│   ├── utils/            # Helper classes (e.g., PasswordManager, ApplicationProperties)
│   ├── web/              # Servlets for handling web requests
├── resources/
│   ├── META-INF/         # persistence.xml for JPA configuration
│   ├── bundle.properties # Email and database credentials
├── webapp/
│   ├── WEB-INF/          # web.xml for servlet configuration
│   ├── *.jsp             # Frontend pages (login, register, etc.)
├── pom.xml               # Maven build file
