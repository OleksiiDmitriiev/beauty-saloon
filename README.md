
# Beauty Saloon 💇‍♀️

## Description

**Beauty Saloon** is a personal pet project designed as a platform for beauty salon masters to showcase their expertise and services, and for clients to discover and make orders seamlessly.

### Features

#### Web Interface

To access the web interface of Beauty Saloon, open your web browser and visit [http://localhost:8080/](http://localhost:8080/). Here, you can explore the user-friendly interface, browse saloon masters, saloon services and make orders for various saloon services.

<div style="text-align:center;">
  <img src="src/main/resources/static/images/interface-preview.JPG" width="1000px" alt="Database UML Diagram">
</div>

#### REST API

The Beauty Saloon also provides a RESTful API for programmatic access to its features. You can use tools like Postman to interact with the API.

- API Base URL: [http://localhost:8080/api/v1](http://localhost:8080/api/v1)

Explore the API by sending HTTP requests to the available endpoints. Refer to the Swagger documentation or the section below for more details on available endpoints and usage.

- **Masters' Profiles**: Salon masters can create detailed profiles, showcasing their skills, experience, and a portfolio of their work.

- **Service Listings**: Masters can list their services, complete with descriptions, pricing, and availability.

- **Client Accessibility**: Clients can easily browse through a curated selection of salon masters and their offerings.

- **Appointment Booking**: Clients can make orders with their preferred salon masters.

Whether you're a salon professional looking to expand your client base or a client seeking expert beauty services, **Beauty Saloon** is the bridge that connects skilled salon masters with beauty-conscious individuals.

Please note that this project is a personal endeavor, continually evolving and improving. We welcome your feedback and contributions to make **Beauty Saloon** even more delightful for its users.

## Navigation

- [Technology Stack](#technology-stack)
- [Database Overview](#database-overview)
- [REST Endpoints](#rest-endpoints)
- [Getting Started](#getting-started)


## Technology Stack

- **Java**: Version 17

- **Spring Framework**: Version 6 (Spring Boot 3.0.5)

- **Thymeleaf**: A modern server-side Java template engine for both web and standalone environments.

- **PostgreSQL**: The chosen relational database management system.

- **MapStruct**: Used for generating type-safe mapping code to map DTOs to entities and vice versa.

- **ModelMapper**: A flexible, convention-based object mapping library for Java.

- **Maven**: Our build tool of choice, Maven simplifies project management and dependency management. It's responsible for compiling, testing, packaging, and deploying our application, ensuring smooth development and deployment processes.

### Supporting Libraries and Tools

- **Lombok**: A library to reduce boilerplate code in Java classes.

- **Mockito**: A testing framework for Java to create and configure mock objects.

- **Jakarta Validation API**: Provides a mechanism for bean validation.
  
- **Spring Boot Validation**: Provides validation-related features for your Spring Boot application.



## Database Overview

Our Beauty Saloon project employs a PostgreSQL relational database to manage various aspects of our saloon business. We are using PostgreSQL version 15.2. Below is an overview of the key entities in our database, along with their descriptions and relationships.


<div style="text-align:center;">
  <img src="src/main/resources/static/images/uml-db-white.png" width="600px" alt="Database UML Diagram">
</div>

**Master Entity**
- Description: Stores information about saloon masters, including their contact details and ratings.
- Fields:
  - `master_id` (uuid): A unique identifier for each master.
  - `created_date` (timestamp): The date when the master record was created.
  - `email` (varchar): The email address of the master (Constraints: Not null, Unique, Length 3-50).
  - `name` (varchar): The name of the master (Constraints: Not null, Length 3-50).
  - `master_rating` (integer): The rating of the master.
  - `phone_number` (varchar): The phone number of the master (Constraints: Not null, Unique).
  - `updated_date` (timestamp): The date when the master record was last updated.
- Relationships: 1-to-1 with `service`.

**Client Entity**
- Description: Records data about saloon clients, including their contact details.
- Fields:
  - `client_id` (uuid): A unique identifier for each client.
  - `email` (varchar): The email address of the client (Constraints: Not null, Unique, Length 3-50).
  - `name` (varchar): The name of the client (Constraints: Not null, Length 3-50).
  - `created_date` (timestamp): The date when the client record was created.
  - `phone_number` (varchar): The phone number of the client (Constraints: Not null, Unique).
  - `updated_date` (timestamp): The date when the client record was last updated.
- Relationships: 1-to-1 with `order_table`.

**Service Entity**
- Description: Manages information about saloon services, including descriptions and ratings.
- Fields:
  - `service_id` (uuid): A unique identifier for each service.
  - `created_date` (timestamp): The date when the service record was created.
  - `description` (varchar): A description of the service (Constraints: Not null, Length 3-200).
  - `name` (varchar): The name of the service (Constraints: Not null, Length 3-50).
  - `service_rating` (integer): The rating of the service.
  - `updated_date` (timestamp): The date when the service record was last updated.
  - `master_id` (uuid): The identifier of the master associated with the service.
- Relationships: 1-to-1 with `master`, 1-to-1 with `order_table`.

**Order Entity**
- Description: Tracks saloon orders made by clients, including order status.
- Fields:
  - `order_id` (uuid): A unique identifier for each order.
  - `created_date` (timestamp): The date when the order record was created.
  - `name` (varchar): The name of the order (Constraints: Not null, Length 3-50).
  - `order_status` (varchar): The status of the order.
  - `updated_date` (timestamp): The date when the order record was last updated.
  - `client_id` (uuid): The identifier of the client associated with the order.
  - `service_id` (uuid): The identifier of the service associated with the order.
- Relationships: 1-to-1 with `client`, 1-to-1 with `service`.

## REST Endpoints

Welcome to the RESTful API endpoints section of the Beauty Saloon project. This section provides details on the available API endpoints for managing various aspects of our salon business. These endpoints allow you to interact with salon orders, services, masters, and clients, making it easy to showcase your expertise, browse services, and make orders seamlessly.

Below, you'll find a comprehensive list of the available endpoints, along with their descriptions and HTTP methods. Feel free to explore and utilize these endpoints to interact with the Beauty Saloon platform.
You can easily interact with the API using tools like Postman.

**API Documentation**: Full API documentation is available in both PDF and JSON formats:

- [bs-api-documentation.pdf](bs-api-documentation.pdf)
- [bs-documentation.json](bs-api-documentation.json)

**API Base URL**: http://localhost:8080/api/v1
### Orders
API endpoints for managing salon orders.

| HTTP Method | Endpoint             | Description                |
|-------------|----------------------|----------------------------|
| 🟢 GET      | /api/v1/orders/{id}  | Get order by ID             |
| 🟠 PUT      | /api/v1/orders/{id}  | Update order by ID          |
| 🔴 DELETE   | /api/v1/orders/{id}  | Delete order by ID          |
| 🟢 GET      | /api/v1/orders       | Get a list of all orders    |
| 🟡 POST     | /api/v1/orders       | Create a new order          |
| 🟢 GET      | /api/v1/orders/search| Get order by name           |

### Services
API endpoints for managing salon services.

| HTTP Method | Endpoint             | Description                |
|-------------|----------------------|----------------------------|
| 🟢 GET      | /api/v1/services/{id}| Get service by ID           |
| 🟠 PUT      | /api/v1/services/{id}| Update service by ID        |
| 🔴 DELETE   | /api/v1/services/{id}| Delete service by ID        |
| 🟢 GET      | /api/v1/services     | Get a list of all services  |
| 🟡 POST     | /api/v1/services     | Create a new service        |

### Masters
API endpoints for managing salon masters.

| HTTP Method | Endpoint             | Description                |
|-------------|----------------------|----------------------------|
| 🟢 GET      | /api/v1/masters/{id} | Get master by ID            |
| 🟠 PUT      | /api/v1/masters/{id} | Update master by ID         |
| 🔴 DELETE   | /api/v1/masters/{id} | Delete master by ID         |
| 🟡 POST     | /api/v1/masters/save | Create a new master         |
| 🟢 GET      | /api/v1/masters      | Get a list of all masters   |
| 🟢 GET      | /api/v1/masters/search| Get master by name          |

### Clients
API endpoints for managing salon clients.

| HTTP Method | Endpoint             | Description                |
|-------------|----------------------|----------------------------|
| 🟢 GET      | /api/v1/clients/{id} | Get client by ID           |
| 🟠 PUT      | /api/v1/clients/{id} | Update client by ID        |
| 🔴 DELETE   | /api/v1/clients/{id} | Delete client by ID        |
| 🟡 POST     | /api/v1/clients/save | Create a new client         |
| 🟢 GET      | /api/v1/clients      | Get a list of all clients   |
| 🟢 GET      | /api/v1/clients/search| Search for a client by name |


## Getting Started

Follow these steps to set up and run the Beauty Saloon project on your local machine.

### Prerequisites

- **Java Development Kit (JDK):** Ensure that you have JDK 17 or a compatible version installed on your system. You can download it from the official Oracle website or use a distribution like OpenJDK.

- **PostgreSQL Database:** You need a PostgreSQL database to run this project. If you don't have PostgreSQL installed, download and install it from the official website: [PostgreSQL Downloads](https://www.postgresql.org/download/).

### Clone the Repository

First, clone the Beauty Saloon repository to your local machine:

```bash
https://github.com/OleksiiDmitriiev/beauty-saloon.git
```

### Database Setup

1. **Create a PostgreSQL Database:**

   Create a new PostgreSQL database for Beauty Saloon. You can do this using the `createdb` command or a PostgreSQL client like pgAdmin.

2. **Restore the Database:**

   Once the database is created, you'll need to restore the database schema and data from an SQL dump file. We've provided a sample SQL dump file (`beauty_saloon_db.sql`) in the project repository.

   Use the following command to restore the database from the SQL dump file:

   ```bash
   psql -U postgres -d beauty_saloon_db -a -f beauty_saloon_db.sql
   ```
