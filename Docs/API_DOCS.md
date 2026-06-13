# API Documentation

The API is documented with springdoc-openapi. The documentation is
generated from the controllers and stays in sync with the code.

## Where to find it

Start the backend with `./mvnw spring-boot:run` and open:

- **Swagger UI** (interactive, clickable): http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** (machine readable): http://localhost:8080/api-docs

## Why this approach

The previous [PDF documentation](Docs/ACE_Escapes_Hotel_Booking_API_v2.0.pdf) went out of sync with the code, which
was raised in the review feedback. Generating the docs from the
controllers solves this for good, since the docs cannot drift from
the implementation.

## Endpoints overview

A short list for orientation. Open Swagger UI for the full details
including request and response schemas.

- `GET /api/rooms` returns all rooms
- `GET /api/rooms/{id}` returns one room
- `GET /api/rooms/availability` returns availability for all rooms in a given period
- `POST /api/bookings` creates a new booking
- `GET /api/bookings/{id}` returns one booking
