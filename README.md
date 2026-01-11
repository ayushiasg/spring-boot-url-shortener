# Spring Boot URL Shortener

## Overview
This project implements a URL shortening service that converts long URLs into shorter, shareable links.  
The system is built with a backend-first approach and is being expanded gradually to include user access control, expiration rules, and analytics.

## Functional Requirements

### URL Creation (Current)
- Accept a long URL and generate a shortened version
- Ensure uniqueness of generated short URLs
- Optional validation of input URL format
- Provide a REST endpoint for URL creation

### URL Redirection (Current)
- Redirect shortened URLs to the original destination
- Return appropriate responses for invalid short codes

### Planned Enhancements
These features are part of the system design and will be implemented as the project evolves:

#### Expiration & Privacy Controls
- Apply default expiration for guest-created URLs
- Support private URLs accessible only to their creators
- Allow authenticated users to specify custom expiration

#### Analytics
- Track the number of accesses for each shortened URL

#### User Management & Access Control
- Support user registration and login
- Restrict private URL access based on ownership
- Admin users can view all shortened URLs

## Technical Details

### Architecture
Client → REST API → Service Layer → Persistence (JPA) → PostgreSQL (Flyway)

Current backend concerns:
- Clean separation between layers
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
```


Schema will evolve as new features are added (e.g., expiration, privacy, click tracking).

### Tech Stack
- Java 21
- Spring Boot
- Spring Web (REST)
- Spring Data JPA
- PostgreSQL
- Flyway (migrations)
- Maven

## API Endpoints (Current)

**Create Short URL**
POST /shorten?url=<long-url>


**Redirect Short URL**
GET /{shortCode}

## Development Approach
The service is being developed incrementally with a focus on backend behavior first.  
Core URL shortening and persistence were prioritized initially; additional features such as expiration, privacy, analytics, and authentication will be layered in without breaking existing functionality or schema.

## Status
Active development. Functional portions and documentation will continue to be updated as the system evolves.