# ER Diagram

Visual reference:
![ER Diagram](Docs/ACEEscapes_ER_v2.0.png)

## Tables

### rooms

The catalog of hotel rooms.

| Column            | Type         | Notes                           |
|-------------------|--------------|---------------------------------|
| id                | bigint       | PK, auto increment              |
| title             | varchar      | room name shown in the UI       |
| description       | text         | longer prose description        |
| price_per_night   | decimal(10,2)| price for one night             |
| capacity          | int          | max number of guests            |
| bed_configuration | varchar(100) | textual description of the beds |
| size_sqm          | int          | room size in square meters      |

### extras

Things a room can offer, like Wi-Fi or breakfast.

| Column      | Type    | Notes                              |
|-------------|---------|------------------------------------|
| id          | bigint  | PK, auto increment                 |
| name        | varchar | short name shown in the UI         |
| icon_name   | varchar | name of the icon used by the UI    |
| description | text    | longer description of the extra    |

### room_extras

Join table for the many-to-many relationship between rooms and extras.

| Column   | Type   | Notes              |
|----------|--------|--------------------|
| room_id  | bigint | PK and FK to rooms |
| extra_id | bigint | PK and FK to extras|

### room_images

Multiple images per room with ordering.

| Column     | Type    | Notes                            |
|------------|---------|----------------------------------|
| id         | bigint  | PK, auto increment               |
| room_id    | bigint  | FK to rooms                      |
| file_path  | varchar | relative path of the image       |
| sort_order | int     | order in which images are shown  |
| is_primary | boolean | flag for the main image          |

### bookings

A guest booking for one room.

| Column            | Type          | Notes                                    |
|-------------------|---------------|------------------------------------------|
| id                | bigint        | PK, auto increment                       |
| room_id           | bigint        | FK to rooms                              |
| check_in          | date          | first night of the stay                  |
| check_out         | date          | day the guest leaves                     |
| price_at_booking  | decimal(10,2) | total price, calculated by the backend   |
| breakfast         | boolean       | whether breakfast is included            |
| status            | enum          | CONFIRMED or CANCELLED                   |
| email             | varchar       | guest email                              |
| first_name        | varchar       | guest first name                         |
| last_name         | varchar       | guest last name                          |
| created_at        | timestamp     | set by the database on insert            |

## Relationships

- A **room** is booked in many **bookings** (`bookings.room_id -> rooms.id`).
- A **room** has many **room_images** (`room_images.room_id -> rooms.id`).
- A **room** offers many **extras** through **room_extras** (many-to-many).

## Design notes

- The guest is stored directly in the booking row, not in a separate guests table.
  For the scope of U1 to U5 there is no user account or login, so a separate
  guests table would add complexity without value.
- `price_at_booking` is the total price, not the nightly rate. The backend
  calculates it as `price_per_night * number_of_nights` so the frontend
  cannot manipulate the price.
- `status` uses soft delete: cancelled bookings stay in the database but are
  marked as `CANCELLED`. This keeps booking history intact and lets the
  availability check ignore cancelled rows.
- `created_at` is set by the database with `DEFAULT CURRENT_TIMESTAMP`.
  In JPA we use `@CreationTimestamp` so Hibernate populates the field on
  insert without an extra reload from the database.
- Naming follows snake_case in the database. JPA entities map between
  snake_case columns and camelCase Java fields with `@Column(name = ...)`.
