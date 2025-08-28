# Bank System: 4 sem

The multiservice banking system consists of three independent applications that interact with each other:
- **bank-system** – the core banking service with domain logic;
- **gateway** – API Gateway (authentication, authorization, and request routing);
- **storage** – event storage service for asynchronous event processing.

> The system is based on a three-layer architecture (Controller → Service → Data Access) in each service and follows RESTful principles for inter-service communication.

> Event-driven components are implemented using Kafka for asynchronous messaging.

## Technologies:
- Java 21
- Build system: Gradle
- Spring Boot + Spring Data JPA (Hibernate) + Spring Security
- Unit tests: JUnit & Mockito
- Docs: Swagger
- Database: PostgreSQL(bank-system + gateway) + MongoDB(storage)
- Kafka

## Applications

### 1. **Bank System (Core Service)**

The main application that implements *all business logic of the banking system.*

**Responsibilities:**
- Managing users (registration, updating personal data, managing friends);
- Managing accounts (creation, balance updates, transaction history);
- Processing transactions:
  - Deposit;
  - Withdraw;
  - Transfer between accounts with a flexible commission system:
    - 0% for own accounts;
    - 3% for transfers to friends;
    - 10% for.
- Providing REST endpoints for user and account management;
- *Publishing events to Kafka when users or accounts are created or updated.*

### 2. **Gateway (API Gateway)**

Spring Boot application acting as *an entry point for all HTTP requests* which *handles authentication, authorization, and routing requests to the core banking service*.

**Role-based access control:**
- *Admin:*
  - Create and manage users and admins;
  - Access all user and account data.
- *Client:*
  - Access and manage only their own data;
  - Perform transactions and manage friends;
  - Password encryption and secure authentication;
  - Protection against unauthorized access:
    - Closed registration (only admins can create new users);
    - 401 Unauthorized or 403 Forbidden responses for invalid requests.

### 3. Storage (Event Storage Service)

A microservice dedicated to asynchronous event storage. *It consumes events published by the Bank System via Kafka and stores them in its own MongoDB database.*

**Responsibilities:**
- Subscribing to Kafka topics:
  - *client-topic* — events related to user creation or updates;
  - *account-topic* — events related to account changes.
- Persisting events in separate tables;
- Decoupling event storage from the core service for better scalability and reliability;
