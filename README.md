# Spring Boot URL Shortener
## Overview
This project implements a URL shortening service that converts long URLs into shorter, shareable links. The service supports both guest and authenticated users, applies expiration rules to shortened URLs, and enforces access control for private URLs.

## Functional Requirements
### URL Creation
- Accept a long URL and generate a shortened version
- Ensure uniqueness of generated short URLs
- Optional validation of input URL format
- Guest users can create public shortened URLs with a default expiration period
- Authenticated users can:
   - Create public or private shortened URLs
   - Specify custom expiration time
   - View and delete URLs they have created

### URL Redirection
- Redirect shortened URLs to the original URL
- Return appropriate responses for invalid or expired URLs

### Analytics
- Track access count for each shortened URL

### User Management
- Support user registration and login
- Admin users can view all shortened URLs, including private ones

## Notes
The application is built using Spring Boot and is developed incrementally.Implementation details and setup instructions will be added as features are completed.
