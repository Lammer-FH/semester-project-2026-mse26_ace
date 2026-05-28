
# ACE Escapes
A hotel booking web app for the Boutique Hotel Technikum built with Spring Boot and Ionic + Vue 3.
 
## Team
See [team.md](team.md) for team members.
 
## Docs
- [API Specification](Docs/ACE_Escapes_Hotel_Booking_API.pdf) using Swagger.io OpenAPI 3.0
- [ER Diagram](Docs/ACEEscapes_diagram.png) Database model using Draw.io:
![ER Diagram](Docs/ACEEscapes_diagram.png)

The ER diagram represents the database structure of ACE Escapes and was updated based on the feedback we received. The model keeps the core entities for rooms, guests and bookings with clear primary and foreign key notation, typed columns and timestamps.

Based on the feedback, the former comma-separated `extras` field was normalized into a separate `extras` table and a `room_extras` junction table. This avoids slow and error-prone text searches, enables referential integrity and makes room features easier to maintain. In addition, monetary values such as `price_per_night` are modeled with `DECIMAL` instead of `FLOAT` to avoid rounding issues. The booking model also includes a `status` field to support booking states such as confirmed or cancelled without deleting historical records.

- [Web Protoype](Docs/ACE_FirstPrototype.pdf)
- [Mobile Prototype](Docs/ACEEscapes_Mobile_Prototype.pdf)

## Tech Stack

**Backend**
- Java mit Spring Boot 3
- MySQL als Datenbank
- Clean Architecture nach Robert C. Martin
- REST API auf Richardson Maturity Level 2

**Frontend**
- Vue 3 mit Ionic Framework
- Pinia für State Management
- Axios für API Calls
- Atomic Design für Komponentenstruktur

**Tooling**
- GitHub Classroom
- GitHub Projects (Kanban)
- Figma und Claude Design für Prototypen
