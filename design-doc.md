# Financial management application design document

## Overview

The purpose of the application is to be a simple tool for tracking personal finances. Users can record income and expenses, categorize transactions, and view summaries of their financial activity. Users can also set a budget, and based on their expenses, the application will count monthly, weekly, and daily budgets for them.

The system is a web application that has a RESTful backend and runs a separate React frontend.

## Tech Stack

- Backend: Spring Boot
- Frontend: React with Vite
- Database: PostgreSQL
- Authentication: Spring Security
- Deployment: On a Hetzner VPS that is hosting a Coolify instance
- Containerization: Docker

## Architecture

- Frontend (React)
  - The user interacts with the React frontend that communicates with the backend
- Backend (Java, Spring Boot)
  - Backend will handle all business logic, authentication and data
- Database (PostgreSQL)
  - Data is stored into a PostgreSQL database

## Core features (MVP-version)

### Auth

- User registration
- User login

### Transactions

- Create transactions (income and expense)
- View transactions
- Update transactions
- Delete transactions

### Categories

- Create categories
- Delete category
- Assign category to transaction
- view Transactions by category

### Filtering

- View transactions by date range
- View Transactions by category
- View transactions by amount

## Extended features (after core features are done)

- Recurring transactions (rent, mortgage, car payments etc.)
- Budget tracking based on target savings set by users
- Monthly or weekly summaries
- When a new user signs up create some kind of step by step prompting phase where their initial data is asked so that the using the application becomes easier

---

## Data Model

The application uses a relational database (PostgreSQL). The data is structured into the following entities.

### User

User is a authenticated user who has created a account to use the application.

- id (Long, primary key)
- username (String, unique)
- password (String, hashed)
- email (String, unique)

### Category

Represents a group or a category that a transaction can be classified in.

- id (Long, primary key)
- name (String)
- user_id (foreign key)

### Transaction

- id (Long, primary key)
- amount (Decimal)
- date (Date)
- description (String)
- user_id (Foreign key)
- category_id (Foreign key, nullable)
  - Users can input transactions without classified categories
- type (Enum: INCOME, EXPENSE)
  - Type defines if the money is coming in or out.

## Relationships

### User and Transaction

- One user can have many transactions
- Each transaction belongs to one user

### Users and category

- One user can have many categories.
- Each category belongs to one user

### Category and transaction

- One category can be assigned to many transactions
- Each transaction can belong to one category

## API Design

Below are specifications of the RESTful API endpoints that the backend exposes.

### Authentication

- POST /api/auth/register
  - Registers a new user to the application
- POST /api/auth/login
  - Authenticates user and starts a session

### Transactions

- GET /api/transactions
  - Returns all transactions for the authenticated user
- POST /api/transactions
  - Creates a new transaction
- PUT /api/transactions/{id}
  - Updates an existing transaction
- DELETE /api/transactions/{id}
  - Deletes a transaction

### Categories

- GET /api/categories
  - Returns all categories for the authenticated user
- POST /api/categories
  - Creates a new category
- DELETE /api/categories/{id}
  - Deletes a category

## Data transfer object (DTO) design

How data is transferred between frontend and backend

### TransactionRequest

Used when creating or updating transactions.

- amount (decimal)
- date (LocalDate)
- Description (string)
- type (INCOME or EXPENSE)
- categoryId (Long, optional)

### TransactionResponse

Returned when fetching transactions.

- id (Long)
- amount (decimal)
- date (LocalDate)
- description (String)
- type (INCOME or EXPENSE)
- categoryName (String, nullable)

### CategoryRequest

- name (String)

### CategoryResponse

- id (long)
- name (string)

## Validation

Input validation handled using Spring Bean validation

### Examples:

- Transaction Amounts must be positive.
- Username must not be empty
- email must be valid.

Validation happens in controller methods.

## Project plan

The plan is to implement the project by one phase at a time and perform incremental testing after each phase is done.

### 1. Initialize project

- Create project via Spring Initializr

- Add dependencies.
  - Spring Web
  - Spring Data JPA
  - PostgreSQL Driver
  - Spring Security
  - Validation

- Project Structure Creation
  - entity
  - repository
  - service
  - controller
  - dto

- Set up PostgreSQL server locally
- Configure application.properties
- Test that the app runs and can connect to the database

### 2. Database layer

- Create user entity
- Create category entity
- Create transaction entity
- Define entity relationships
  - user -> transaction (OneToMany)
  - User -> category (OneToMany)
  - Transaction -> category (ManyToOne, nullable)
    Add enums:
  - TransactionType (INCOME, EXPENSE)
- Verify that the tables are created
- Create some mock data to test the database

### 3. Repositories

- Create UserRepository
- Create CategoryRepository
- Create TransactionRepository
- Add and test basic CRUD queries.

### 4. DTO ?

### 5. Services

- TransactionService
  - create transaction
  - get transaction
  - update transaction
  - delete transaction

- CategoryService
  - create Category
  - get categories
  - delete category

### 6. Controller Layer

- Create AuthController
- Register
- Login

- Create TransactionController
  - GET
  - POST
  - PUT
  - DELETE

- Create CategoryController
  - GET
  - POST
  - DELETE

### 7. Security

- Configure spring security
- Implement Auth
- Password hashing with BCrypt
- Restrict endpoints for authenticated users
- Make sure only users can access their own data.

### 8. Validation

- Add validation to all controllers
- Test invalid inputs

### 9. Manual testing

- Test all the endpoints using Postman
- Test:
  - All CRUD operations
  - Authentication
  - Filtering

### 10. Frontend

- Init React Project (Vite)
- TODO ....

### 11. Deployment

- Dockerize the application in the following way

```plaintext
/root
├── backend/
│   └── Dockerfile
├── frontend/
│   └── Dockerfile
└── docker-compose.yml
```

- Set up a PostgreSQL database on the server
- Deploy the application
- Configure env variables
- Test that everything is running.
- Configure domain and DNS settings.

## Other possible things to include

- @ControllerAdvice for global exception handling
- Pagination ?
- Scheduled tasks for monthly etc transactions if they are included
- Logging?
