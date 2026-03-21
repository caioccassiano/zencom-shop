# Zencom Shop

A modern, modular e-commerce platform built with Spring Boot and Clean Architecture principles.

## 📋 Overview

Zencom Shop is a full-featured e-commerce backend application that implements a comprehensive shopping experience. The project follows Hexagonal Architecture (Ports and Adapters) and Domain-Driven Design (DDD) principles, providing a scalable and maintainable codebase.

## 🏗️ Architecture

The application is organized into modular bounded contexts:

- **Catalog Module**: Product and inventory management with real-time stock control
- **Cart Module**: Shopping cart operations with item management
- **Orders Module**: Order processing and lifecycle management
- **Payments Module**: Payment processing with multiple payment method support
- **Checkout Module**: Orchestrates the checkout flow between cart, orders, inventory, and payments
- **Notification Module**: Multi-channel notification system with deduplication
- **Users Module**: User authentication and authorization
- **Shared Module**: Cross-cutting concerns including security, domain events, and common utilities

### Architecture Principles

- **Hexagonal Architecture**: Clear separation between domain logic and external adapters
- **Domain-Driven Design**: Rich domain models with business logic encapsulation
- **CQRS Pattern**: Separation of read and write operations where applicable
- **Event-Driven**: Domain events for inter-module communication
- **Clean Architecture**: Dependency inversion with ports and adapters

## 🚀 Features

### Core Functionality

- **User Management**
  - User registration and authentication
  - JWT-based security
  - Role-based access control

- **Product Catalog**
  - Product creation and management
  - Product activation/deactivation
  - Product status tracking
  - Inventory management with reservations
  - Stock quantity tracking and validation

- **Shopping Cart**
  - Add/update/remove items
  - Cart abandonment tracking
  - Active cart management
  - Clear cart functionality

- **Order Management**
  - Order creation and processing
  - Order status tracking
  - Order history
  - Order cancellation and updates

- **Payment Processing**
  - Multiple payment methods support
  - Payment status tracking
  - Payment event handling
  - Transaction management

- **Checkout Flow**
  - Integrated checkout process
  - Price calculation and validation
  - Stock reservation
  - Payment processing orchestration

- **Notifications**
  - Multi-channel notifications (configurable)
  - Deduplication mechanism
  - Notification status tracking
  - Integration with order and payment events

## 🛠️ Technology Stack

- **Framework**: Spring Boot 4.0.1
- **Language**: Java 21
- **Build Tool**: Gradle
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT (JJWT)
- **Validation**: Jakarta Bean Validation
- **Utilities**: Lombok
- **Testing**: JUnit 5, Mockito

## 📦 Prerequisites

- Java 21 or higher
- Docker and Docker Compose (for PostgreSQL and RabbitMQ)
- Gradle 8.x (or use the included wrapper)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd zencom-shop
```

### 2. Configure Environment Variables

Create a `.env` file in the project root (see `.env.example` if available):

```env
POSTGRES_DB=zencom
POSTGRES_USER=zencom
POSTGRES_PASSWORD=zencom
RABBIT_USER=guest
RABBIT_PASSWORD=guest
```

### 3. Start Infrastructure

```bash
docker-compose up -d
```

This will start:
- **PostgreSQL** on port `5435`
- **RabbitMQ** on port `5672` (management UI at `http://localhost:15672`)

### 4. Build the Application

```bash
./gradlew build
```

### 5. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## 🔧 Configuration

### Application Properties

Configure the application via `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5435/zencom
spring.datasource.username=zencom
spring.datasource.password=zencom

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# JWT Security
security.jwt.secret=<your-secret-key>
security.jwt.expirationSeconds=3600
```

### Environment Variables

You can override configuration using environment variables:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SECURITY_JWT_SECRET`
- `SECURITY_JWT_EXPIRATIONSECONDS`

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Cart Endpoints

All cart endpoints require authentication.

#### Get or Create Cart
```http
GET /api/v1/cart
Authorization: Bearer <token>
```

#### Add Item
```http
POST /api/v1/cart/items
Authorization: Bearer <token>
Content-Type: application/json

{
  "product_id": "uuid",
  "quantity": 2
}
```

#### Update Item Quantity
```http
PATCH /api/v1/cart/items/{productId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 3
}
```
> Send `quantity: 0` to remove the item.

#### Clear Cart
```http
DELETE /api/v1/cart
Authorization: Bearer <token>
```

### Protected Endpoints

All endpoints except `/api/v1/auth/**` require a valid JWT token:

```http
Authorization: Bearer <your-jwt-token>
```

## 🧪 Testing

### Run All Tests

```bash
./gradlew test
```

### Run Tests with Coverage

```bash
./gradlew test jacocoTestReport
```

## 📁 Project Structure

```
zencom-shop/
├── src/
│   ├── main/
│   │   ├── java/com/example/zencom/zencom_shop/
│   │   │   ├── modules/
│   │   │   │   ├── cart/
│   │   │   │   │   ├── domain/          # Domain entities, value objects, exceptions
│   │   │   │   │   ├── application/     # Use cases, ports, DTOs, mappers
│   │   │   │   │   ├── adapters/        # Infrastructure implementations
│   │   │   │   │   └── config/          # Module configuration
│   │   │   │   ├── catalog/
│   │   │   │   ├── checkout/
│   │   │   │   ├── notification/
│   │   │   │   ├── orders/
│   │   │   │   ├── payments/
│   │   │   │   ├── users/
│   │   │   │   └── shared/              # Cross-cutting concerns
│   │   │   └── ZencomShopApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── build.gradle
├── settings.gradle
├── docker-compose.yml
└── README.md
```

### Module Structure Pattern

Each module follows a consistent structure:

```
module/
├── domain/                 # Pure business logic
│   ├── entities/          # Domain entities and aggregate roots
│   ├── enums/             # Domain enumerations
│   ├── events/            # Domain events
│   ├── exceptions/        # Domain exceptions
│   └── vo/                # Value objects
├── application/           # Application layer
│   ├── dtos/              # Data transfer objects (input/ and output/)
│   ├── ports/             # Port interfaces
│   │   ├── in/           # Input ports (use case interfaces)
│   │   └── out/          # Output ports (repositories, external services)
│   ├── usecases/         # Use case implementations (@Service @Transactional)
│   ├── mappers/          # Application-level mappers (domain → DTO)
│   └── exceptions/       # Application exceptions
└── adapters/             # Infrastructure layer
    ├── in/
    │   ├── web/          # REST controllers, request/response DTOs, HTTP mappers
    │   └── rabbitmq/     # RabbitMQ listeners and mappers
    └── out/
        ├── <entity>/     # JpaEntity, JpaRepository, PersistenceMapper, RepositoryAdapter
        └── <module>/     # Adapters implementing ports to other modules
```

## 🔐 Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control (RBAC)
- Stateless session management
- CSRF protection (configured for REST APIs)

## 🌟 Key Design Patterns

- **Hexagonal Architecture**: Ports and adapters pattern for flexibility
- **Use Case Pattern**: Each business operation is encapsulated in a use case
- **Repository Pattern**: Data access abstraction
- **Mapper Pattern**: Clean separation between layers
- **Domain Events**: Asynchronous communication between modules
- **Aggregate Root**: DDD tactical pattern for consistency boundaries
- **Value Objects**: Immutable domain concepts
- **Command/Query Separation**: Distinct DTOs for inputs and outputs

