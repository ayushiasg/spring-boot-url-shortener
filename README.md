# Spring Boot URL Shortener

## Overview
This project implements a URL shortening service that converts long URLs into shorter, shareable links.  
The system is built with a backend-first approach and is being expanded gradually to include user access control, expiration rules, and analytics.

## Functional Requirements

### URL Creation (Current)
- Accept a long URL and generate a shortened version
- Ensure uniqueness of generated short URLs
- Optional validation of input URL format
- Apply a default expiration to newly created URLs
- Require users to be authenticated to create shortened URLs
- Provide a REST endpoint for URL creation

### URL Redirection (Current)
- Redirect shortened URLs to the original destination
- Return **410 Gone** for expired short URLs
- Return **404 Not Found** for invalid short codes

### User Registration & Authentication (Current)
- Users can register via a REST endpoint
- Passwords are stored securely using BCrypt hashing
- Login is handled via Spring Security's default form login
- Authentication is session-based
- Only authenticated users can create shortened URLs

### Planned Enhancements
These features are part of the system design and will be implemented as the project evolves:

#### Ownership & Privacy Controls
- Associate shortened URLs with their creators
- Support private URLs accessible only to their owners
- Admin users can view all shortened URLs

#### Analytics
- Track the number of accesses for each shortened URL

## Technical Details

### Architecture
Client → REST API → Service Layer → Persistence (JPA) → PostgreSQL (Flyway)

Current backend concerns:
- Clean separation between layers
- Authentication and password handling
- Explicit schema evolution
- Predictable redirect handling

### Database Schema (Current)

Schema is versioned via Flyway migrations.

```
short_urls
├── id BIGSERIAL PRIMARY KEY
├── short_code VARCHAR(20) UNIQUE NOT NULL
├── original_url TEXT NOT NULL
└── created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
└── expires_at TIMESTAMP NULL

users
├── id BIGSERIAL PRIMARY KEY
├── username VARCHAR UNIQUE NOT NULL
├── password VARCHAR NOT NULL
└── role VARCHAR NOT NULL DEFAULT 'USER'

```



Schema will evolve as new features are added (e.g., ownership, privacy, click tracking).

### Tech Stack
- Java 21
- Spring Boot
- Spring Web (REST)
- Spring Data JPA
- Spring Security
- BCrypt password hashing
- PostgreSQL
- Flyway (migrations)
- Maven

## API Endpoints (Current)

**Register User**  
POST /register  
Request Body (JSON):
{ "username": "...", "password": "..." }

**Create Short URL**  
POST /shorten?url=<long-url>

Requires authentication.

**Redirect Short URL**  
GET /{shortCode}

Response semantics:
- 302 → redirect to target
- 410 → expired URL
- 404 → unknown short code

## Development Approach
The service is being developed incrementally with a focus on backend behavior first.  
Authentication was added to support future per-user ownership, privacy, and analytics features.  
The next steps involve associating URLs with users and restricting visibility based on ownership.

## Status
Active development. Functional portions and documentation will continue to be updated as the system evolves.