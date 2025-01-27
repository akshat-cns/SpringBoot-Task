# Restaurant Table Booking system using Spring-Boot

## Overview
This is a Spring Boot-based backend application for managing restaurant table bookings. It provides RESTful APIs to perform CRUD operations on tables, including fetching, adding, updating, and deleting table data.

## Features
- GET /api/tables - Retrieve all tables.
- PATCH /api/tables/{id} - Update table availability.
- POST /api/tables - Add a new table.
- PUT /api/tables/{id} - Update table details.
- DELETE /api/tables/{id} - Delete a table.
  
Handles non-existent table IDs with a custom exception (TableNotFoundException).

## Technologies Used
Java 17,
Spring Boot,
Spring Web,
Spring Data JPA,
H2 Database (in-memory),
Lombok,
Maven for dependency management
