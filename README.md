# 📚 Library Management REST API

A **Spring Boot REST API** for managing a library system with role-based access control for **Admin** and **User** roles.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming Language |
| Spring Boot 3 | Application Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database ORM |
| MySQL | Relational Database |
| Lombok | Boilerplate Reduction |
| Maven | Build Tool |

---

## 📁 Project Structure

```
src/main/java/com/prasanna/library/management/
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   └── UserController.java
├── service/
│   ├── AuthService.java
│   ├── AdminService.java
│   └── UserService.java
├── repository/
│   ├── BookRepo.java
│   ├── UserRepo.java
│   └── IssueRecordsRepo.java
├── model/
│   ├── Book.java
│   ├── User.java
│   └── IssueRecord.java
├── dto/
│   ├── BookDto.java
│   ├── UserDto.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
├── security/
│   ├── SecurityConfig.java
│   ├── PasswordConfig.java
│   ├── CustomUserDetails.java
│   └── CustomUserDetailsService.java
└── exception/
    ├── GlobalExceptionHandler.java
    ├── ErrorResponse.java
    ├── ResourceNotFoundException.java
    ├── BadRequestException.java
    ├── ConflictException.java
    └── AuthenticationException.java
```

---

## ⚙️ Features

### 👤 User
- View all available books
- View book by ID
- Search books by title or author
- Issue a book
- Return a book

### 👨‍💼 Admin
- View all books (including unavailable)
- View book by ID
- Search books by title or author
- Add a new book
- Update book details
- Delete a book (soft delete)
- View all issue records

### 🔐 Auth
- Register as User
- Register as Admin
- Login

---

## 🔐 Security

- **Spring Security** with HTTP Basic Authentication
- **BCrypt** password encoding
- **Role-based access control** — `ROLE_USER` and `ROLE_ADMIN`
- **Stateless** session management

---

## 🗄️ Database Schema

### Books Table
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary Key |
| title | VARCHAR | Book title |
| author | VARCHAR | Book author |
| quantity | BIGINT | Available quantity |
| is_issued | BOOLEAN | Whether book is fully issued |
| is_available | BOOLEAN | Whether book is available in library |

### Users Table
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary Key |
| first_name | VARCHAR | First name |
| last_name | VARCHAR | Last name |
| email | VARCHAR | Unique email |
| username | VARCHAR | Unique username |
| password | VARCHAR | BCrypt encoded password |
| role | VARCHAR | ROLE_USER or ROLE_ADMIN |

### Issue Records Table
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary Key |
| user_id | BIGINT | Foreign Key → Users |
| book_id | BIGINT | Foreign Key → Books |
| issued_date | DATE | Date of issue |
| return_date | DATE | Date of return (null if not returned) |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- MySQL
- Maven

### Setup

**1. Clone the repository:**
```bash
git clone https://github.com/prasannatalekar/library-management-rest-api.git
cd library-management-rest-api
```

**2. Configure database in `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**3. Create MySQL database:**
```sql
CREATE DATABASE library_db;
```

**4. Run the application:**
```bash
mvn spring-boot:run
```

Application runs on `http://localhost:8080`

---

## 📮 API Endpoints

### 🔓 Auth (No Authorization Required)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register/user` | Register a new user |
| POST | `/api/auth/register/admin` | Register a new admin |
| POST | `/api/auth/login` | Login |

**Register Request Body:**
```json
{
    "firstName": "Prasanna",
    "lastName": "Kumar",
    "email": "prasanna@gmail.com",
    "username": "prasanna",
    "password": "prasanna123"
}
```

**Login Request Body:**
```json
{
    "username": "prasanna",
    "password": "prasanna123"
}
```

---

### 👤 User APIs (Basic Auth — User Credentials)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/user/books` | Get all available books |
| GET | `/api/user/book/{id}` | Get book by ID |
| GET | `/api/user/searchbook?keyword=java` | Search books |
| POST | `/api/user/issuebook/{id}` | Issue a book |
| POST | `/api/user/returnbook/{id}` | Return a book |

---

### 👨‍💼 Admin APIs (Basic Auth — Admin Credentials)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/admin/books` | Get all books |
| GET | `/api/admin/book/{id}` | Get book by ID |
| GET | `/api/admin/searchbook?keyword=java` | Search books |
| POST | `/api/admin/addbook` | Add a new book |
| PUT | `/api/admin/updatebook/{id}` | Update a book |
| DELETE | `/api/admin/deletebook/{id}` | Delete a book |
| GET | `/api/admin/issuerecords` | Get all issue records |

**Add/Update Book Request Body:**
```json
{
    "title": "Java Programming",
    "author": "James Gosling",
    "quantity": 5,
    "available": true
}
```

---

## ⚠️ Error Handling

All errors return a structured response:

```json
{
    "localDateTime": "2025-01-01T10:00:00",
    "status": 404,
    "message": "Book not found!",
    "path": "/api/user/book/99"
}
```

| Exception | HTTP Status | Scenario |
|---|---|---|
| ResourceNotFoundException | 404 | Book/User/Record not found |
| BadRequestException | 400 | Book not available, already issued |
| ConflictException | 409 | Username/Email already exists |
| AuthenticationException | 401 | Invalid credentials |

---

## 📌 Postman Testing Order

1. Register admin → `POST /api/auth/register/admin`
2. Register user → `POST /api/auth/register/user`
3. Add books → `POST /api/admin/addbook` *(admin credentials)*
4. Get all books → `GET /api/user/books` *(user credentials)*
5. Issue a book → `POST /api/user/issuebook/1` *(user credentials)*
6. Check issue records → `GET /api/admin/issuerecords` *(admin credentials)*
7. Return a book → `POST /api/user/returnbook/1` *(user credentials)*

---

## 👨‍💻 Author

**Prasanna Talekar**
- GitHub: [@prasannatalekar](https://github.com/prasannatalekar)
