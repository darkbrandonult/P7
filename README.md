# Poseidon Capital Solutions Trading Platform

## 🚀 Project Description

Poseidon Capital Solutions Trading Platform is a robust web application for trading management, offering a comprehensive solution for managing bids, curve points, ratings, transactions, custom rules, and users.

## 🛠 Technologies Used

- **Backend**:
  - Java 21
  - Spring Boot 3.4.13
  - Spring Security
  - Spring Data JPA
  - MapStruct
  - Lombok

- **Frontend**:
  - Thymeleaf
  - Bootstrap 4
  - HTML5

- **Database**:
  - MySQL (production)
  - H2 (tests)

- **Testing Tools**:
  - JUnit 5
  - Mockito
  - Spring Test

- **API Documentation**:
  - SpringDoc OpenAPI (Swagger)

## 🏗️ Architecture Overview

The application follows a monolithic architecture organized in layers according to the MVC (Model-View-Controller) pattern:

### Architectural Layers

1. **Presentation Layer**:
   - Controllers handle HTTP requests
   - Thymeleaf templates render the UI
   - DTOs (Data Transfer Objects) for data exchange with client

2. **Service Layer**:
   - Business logic implementation
   - Transaction management
   - Data validation and processing

3. **Data Access Layer**:
   - Spring Data JPA repositories
   - Entity models representing database tables
   - Data persistence operations

4. **Cross-Cutting Concerns**:
   - Security
   - Exception handling
   - Logging
   - Configuration

### Design Patterns Used

- **DTO Pattern**: Separate data transfer objects from domain models
- **Repository Pattern**: Abstract data access operations
- **Dependency Injection**: Spring's IoC container manages component dependencies
- **MVC Pattern**: Separation of concerns between Models, Views, and Controllers
- **Mapper Pattern**: MapStruct for type-safe bean mapping between DTOs and entities

## 🔐 Security Architecture

The application implements a comprehensive security model using Spring Security:

### Authentication

- Form-based authentication with username and password
- BCrypt password encoding for secure storage
- Custom UserDetailsService implementation that loads user data from the database

### Authorization

- Role-based access control with distinct ADMIN and USER roles
- Method-level security using `@EnableMethodSecurity`
- URL-based security patterns in SecurityFilterChain configuration

### Security Rules

1. **ADMIN Role**:
   - Full access to user management (`/user/**`)
   - Full access to all features and operations (CRUD)

2. **USER Role**:
   - Access to all entity management pages (bids, curve points, ratings, trades, rules)

3. **Security Configuration**:
   - Custom security filter chain
   - Login page at `/app/login`
   - Protected endpoints except login, error, and static resources
   - CSRF protection enabled
   - Session management with logout and cookie deletion
   - Custom 403 access-denied page

## ✨ Key Features

1. **User Management**
   - Authentication and authorization
   - User creation, modification, and deletion
   - Strong password validation (8+ chars, uppercase, number, symbol)
   - Different roles (ADMIN, USER)

2. **Bid List Management**
   - Bid creation and tracking
   - Complete CRUD operations

3. **Curve Points**
   - Financial curve point management
   - Data tracking and analysis

4. **Ratings**
   - Management of Moody's, S&P, and Fitch ratings
   - Financial ranking tracking

5. **Transactions**
   - Trade recording and tracking
   - Comprehensive trade details

6. **Custom Rules**
   - Business rule creation and management
   - Flexibility for specific configurations

## 📦 Project Structure

```
src
├── main
│   ├── java
│   │   └── com/poseidoncapitalsolutions/poseidon
│   │       ├── config           # Spring Configuration
│   │       │   ├── SecurityConfig.java          # Security settings
│   │       │   ├── SwaggerConfig.java           # API documentation
│   │       │   ├── UserDetailsServiceImpl.java  # Authentication service
│   │       │   └── UserDetailsImpl.java         # User details implementation
│   │       ├── controller       # Spring MVC Controllers
│   │       ├── dto              # Data Transfer Objects
│   │       ├── exception        # Custom Exception Handling
│   │       ├── mapper           # MapStruct Mappers
│   │       ├── model            # JPA Entities
│   │       ├── repository       # Spring Data Repositories
│   │       └── service          # Business Services
│   └── resources
│       ├── static               # Static Resources (Bootstrap CSS)
│       ├── templates            # Thymeleaf Templates
│       └── application.properties
└── test                         # Unit and Integration Tests
```

## 🚀 Installation and Configuration

### Prerequisites
- Java 21
- Maven
- MySQL

### Installation Steps

1. Clone the repository
```bash
git clone https://github.com/darkbrandonult/P7.git
cd P7
```

2. Configure the Database
- Create a MySQL database named `demo` (or update `application.properties`)
- The default configuration expects `root` / `root` credentials
- Initialize the schema using the SQL script in `doc/data.sql`

3. Compile and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

4. Access the application at `http://localhost:8080`

## 🧪 Testing

- Unit and integration testing with JUnit 5 and Mockito
- H2 in-memory database for isolated test runs
- Test coverage managed by JaCoCo
- Run tests:
```bash
mvn test
```

## 📊 API Documentation

- Swagger UI accessible at: `/swagger-ui.html`
- OpenAPI documentation at: `/api-docs`

## 🔒 Default Credentials

- **Admin**:
  - Username: admin
  - Password: 123123

- **Standard User**:
  - Username: user
  - Password: 123123

## 🌟 Best Practices

- **Clean Code**: Following SOLID principles and clean code practices
- **Security**: Implementing defense in depth with multiple security layers
- **Testing**: Comprehensive unit and integration tests
- **Documentation**: Well-documented code with JavaDoc
- **DTO Pattern**: Separation of entity models from data transfer objects
- **Validation**: Input validation at multiple levels