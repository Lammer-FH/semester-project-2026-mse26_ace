# Engineering Standards and Implementation Rules

These are the rules we follow when working on this project. Keep them in mind when writing code or reviewing a pull request.

## General Guidelines

### Git Workflow and Branching

We use three kinds of branches:

- `main` holds releases only. Final tags live here (V1.0.0 after Milestone 1).
- `dev` is the active integration branch. Everything goes through here first.
- `feature/<name>` is where actual work happens. One branch per user story (for example `feature/u4-booking-backend`).

Rules:

- Always branch off `dev`, never off `main`.
- Every user story gets its own feature branch.
- Merges into `dev` or `main` go through a pull request with at least one review. No direct pushes.

### Commit Messages

Use imperative mood (English) and describe what the commit does, not what you did.

- `add booking creation endpoint for U4` is good.
- `added some stuff to booking` is not good.

If a commit needs more context, leave a blank line and write a short paragraph below.

### AI Usage

AI tools are allowed and even encouraged, but every meaningful use must be documented in `AI_USAGE.md`. What goes in there:

- Which tool (Claude, ChatGPT, Copilot, etc.).
- What we asked or what task we were on.
- What we accepted, modified, or rejected and why.

---

## Backend

### Clean Architecture (Robert Martin)

Dependencies flow inward, toward the domain. Outer layers know about inner ones, never the other way around.

- Domain is the core. It knows nothing about Spring, JPA, or HTTP.
- Business rules live in domain models and services, not in controllers or repositories.
- The domain must be testable without spinning up a database or a web server.
- Framework annotations (Spring, JPA, Lombok) are not allowed in domain models. They are fine in DTOs, JPA entities, mappers, and config classes.

### Backend Layers

Four layers, with clear responsibilities:

- `domain` contains models and ports (interfaces). No framework code.
- `service` contains use cases. This is where business logic lives.
- `infrastructure` contains JPA entities, repositories, and adapters. Adapters implement the domain ports.
- `web` contains controllers, DTOs, web mappers, and the global exception handler.

Keep controllers thin. They handle HTTP and delegate to services. Services handle the actual logic.

### REST API and Richardson Level 2

- Use plural nouns for resources (`/api/rooms`, `/api/bookings`).
- Pick HTTP verbs that make sense (GET to read, POST to create, PUT or PATCH to update, DELETE to remove).
- Status codes carry meaning:
  - `200 OK` for a successful read.
  - `201 Created` after a successful POST that creates something.
  - `204 No Content` after a DELETE.
  - `400 Bad Request` for invalid input.
  - `404 Not Found` when a resource does not exist.
  - `409 Conflict` when the request is valid but conflicts with current state (for example a room already booked).
- No RPC-style endpoints. `/api/bookings/createBooking` is wrong, `POST /api/bookings` is right.

### Validation and Error Handling

- Validate request DTOs at the controller boundary using Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@NotNull`).
- Throw domain exceptions for business rule violations (`InvalidBookingException`, `RoomNotAvailableException`, `BookingNotFoundException`).
- Map exceptions to HTTP status codes in one central place using `@RestControllerAdvice`. Do not catch them in controllers.
- Error responses use a consistent JSON shape, for example `{"error": "Room is not available for the selected period"}`.

### DTOs and Mappers

- Never return JPA entities directly from a controller. Always map to a DTO first.
- Use Java records for DTOs since they are pure data carriers.
- Keep mappers as static utility classes unless they need other beans injected.
- Two web mappers per resource: one for "domain to response DTO", one for "request DTO to domain".
- Entity mappers (entity to domain) live in `infrastructure/mapper`.

### Database

- Snake_case for tables and columns (`check_in`, `first_name`, `room_extras`).
- Always declare the mapping explicitly in entities when names differ from defaults (`@Column(name = "first_name")`).
- Add indexes on columns used in filters or joins.
- Use `ddl-auto=validate` in `application.properties`. The schema is the source of truth, not Hibernate.
- Keep schema and seed in `backend/sql/`. The seed should be enough to manually click through the app.

### Transactions

Define transaction boundaries in the service layer. Do not put `@Transactional` on controllers or repositories.

### Testing

- Each user story needs at least one unit test and one integration test.
- A story is only "done" when code is reviewed, tests pass and the feature works on mobile.

---

## Frontend

### Stack and Conventions

We use Vue 3 with Ionic, Pinia for state, Vue Router for navigation, and Axios for API calls. Stick with the conventions these tools come with, do not invent new patterns.

### Atomic Design

Organize components into four buckets:

- `atoms` are the smallest building blocks (buttons, icons, inputs, chips).
- `molecules` combine atoms into useful pieces (a date picker, a room card, a form field with label and validation).
- `organisms` are larger sections (a page header, a paginated room list).
- `templates` and `pages` are full views (Rooms page, Booking page, Confirmation page).

### Vue Conventions

- Use the Composition API with `<script setup>` for new components. Options API only if it is clearly simpler in a specific case.
- One component per file, PascalCase for component names.
- Multi-word component names to avoid collisions with HTML elements.
- Base components used app-wide get a `Base` prefix (`BaseButton`, `BaseModal`).
- Single-instance layout components get a `The` prefix (`TheHeader`).
- Tightly coupled children inherit the parent name (`RoomCardExtras` belongs with `RoomCard`).
- Name files general to specific, so they sort together (`BookingFormDateDialog`, `BookingFormReviewStep`).
- Use named routes everywhere, never hardcoded path strings in `router.push` or `<router-link>`.
- Props are read-only. Mutating a prop is a bug.
- Always set a stable `:key` on `v-for`. Array index as key is not stable.

### Pinia State Management

- Use Pinia for state that needs to survive across components or pages (selected room, booking draft, current filter).
- API calls go in Pinia actions, not in components.
- Every async action sets a loading flag and an error flag, so the UI can react.
- Clear booking-related state when the user leaves the booking flow.

### API Calls and Axios

- One Axios instance configured centrally with `baseURL` pointing at the backend.
- All API logic goes through a service layer (for example `services/roomService.ts`), not directly from components.
- Handle errors close to the call. Show user-friendly messages, not stack traces.

### UX and UI

- Mobile-first. Design for a phone screen, then expand for tablet and desktop.
- Semantic HTML. Use `<button>` for buttons, `<form>` for forms, `<label>` for labels.
- Visible focus states for keyboard users.
- Consistent icon set across all pages (Ionicons for Ionic).
- Provide clear feedback for every async action (loading spinner, success message, error toast).

### UI Implementation Specifics

- Rooms page shows exactly 5 rooms per page. Pagination uses a button group.
- Every room extra has a meaningful icon next to it (WiFi, Breakfast, Parking, etc.).
- Booking flow uses a date dialog for selecting check-in and check-out.
- Booking has a review step before final submission, where the user can go back and change details.
- Confirmation page is printable on A4 directly from the browser. No PDF generation needed, just clean print CSS.

---

## Code Review Checklist

Before requesting a review, check:

- The branch is up to date with `dev` (no merge conflicts).
- The code compiles and the app starts.
- Manual test: the new feature actually works.
- No unrelated changes in the PR (no formatting passes on untouched files, no committed IDE settings).
- Sensitive files are not committed (`application-local.properties`, `.env`, `.DS_Store`).
- AI usage for this PR is documented in `AI_USAGE.md`.

Before approving someone else's PR:

- Pull the branch and run it. Do not approve code you have not seen running.
- Read the changes, not just the file count. Ask yourself if you could maintain this code.
- Check that tests are present and that they actually test something.
- Leave at least one specific comment, even if it is just praise for a good choice.
