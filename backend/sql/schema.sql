-- Boutique Hotel Technikum schema
-- TODO tables for US 4-5

CREATE TABLE IF NOT EXISTS rooms (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    title              VARCHAR(200)   NOT NULL,
    description        TEXT,
    price_per_night    DECIMAL(10, 2) NOT NULL,
    capacity           INT            NOT NULL,
    bed_configuration  VARCHAR(100),
    size_sqm           INT
);

CREATE TABLE IF NOT EXISTS room_images (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id     BIGINT       NOT NULL,
    file_path   VARCHAR(500) NOT NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    is_primary  BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_room_images_room
        FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS extras (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    icon_name   VARCHAR(100),
    description TEXT
);

CREATE TABLE IF NOT EXISTS room_extras (
    room_id  BIGINT NOT NULL,
    extra_id BIGINT NOT NULL,
    PRIMARY KEY (room_id, extra_id),
    CONSTRAINT fk_room_extras_room  FOREIGN KEY (room_id)  REFERENCES rooms (id)  ON DELETE CASCADE,
    CONSTRAINT fk_room_extras_extra FOREIGN KEY (extra_id) REFERENCES extras (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id   BIGINT NOT NULL,
    check_in  DATE   NOT NULL,
    check_out DATE   NOT NULL,
    CONSTRAINT fk_bookings_room FOREIGN KEY (room_id) REFERENCES rooms (id)
);
