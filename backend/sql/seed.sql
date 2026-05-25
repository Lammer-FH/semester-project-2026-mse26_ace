-- Seed data to test out the app
-- TODO: Add images into the images folder/maybe also edit the file name

INSERT INTO extras (id, name, icon_name, description) VALUES
    (1, 'Wi-Fi',       'wifi',   'Free high speed internet'),
    (2, 'Breakfast',   'coffee', 'Continental breakfast included'),
    (3, 'Parking',     'car',    'On site parking'),
    (4, 'Smart TV',    'tv',     '50 inch smart TV with streaming'),
    (5, 'Air con',     'wind',   'Individual climate control'),
    (6, 'Spa access',  'spa',    'Use of sauna and steam room');

INSERT INTO rooms (id, title, description, price_per_night, capacity, bed_configuration, size_sqm) VALUES
    (1, 'Deluxe Double',
        'Second floor, courtyard side. Linen drapes, oak boards that creak in the right places, and a desk angled at the lemon tree downstairs.',
        129.00, 2, 'King bed', 32),
    (2, 'Superior Suite',
        'The corner unit on the third. Two windows, deep tub, and a reading nook the architect insisted on. Quietest room in the house after 22:00.',
        189.00, 3, 'King + sofa bed', 48),
    (3, 'Classic Single',
        'Small, honest, north facing. Built for one traveller and a notebook. Tram 31 hums past at 06:12, earplugs are in the drawer.',
        89.00, 1, 'Single bed', 18),
    (4, 'Garden Studio',
        'Ground floor with its own door onto the garden. Steps to the espresso bar, figs in August if the birds leave any.',
        149.00, 2, 'Queen bed', 28),
    (5, 'Junior Twin',
        'Two iron frame beds, a shared writing table between them. Booked most often by siblings and old colleagues.',
        109.00, 2, 'Two singles', 24),
    (6, 'Penthouse Loft',
        'Top floor under the original 1928 trusses. Skylights over the bath, terrace facing the cathedral. The room people stay in twice.',
        289.00, 2, 'King + lounge', 62);

INSERT INTO room_images (room_id, file_path, sort_order, is_primary) VALUES
    (1, '/images/rooms/1-main.jpg',   0, TRUE),
    (1, '/images/rooms/1-bed.jpg',    1, FALSE),
    (1, '/images/rooms/1-bath.jpg',   2, FALSE),
    (1, '/images/rooms/1-window.jpg', 3, FALSE),
    (2, '/images/rooms/2-main.jpg',   0, TRUE),
    (2, '/images/rooms/2-bed.jpg',    1, FALSE),
    (2, '/images/rooms/2-bath.jpg',   2, FALSE),
    (3, '/images/rooms/3-main.jpg',   0, TRUE),
    (3, '/images/rooms/3-bed.jpg',    1, FALSE),
    (4, '/images/rooms/4-main.jpg',   0, TRUE),
    (4, '/images/rooms/4-garden.jpg', 1, FALSE),
    (5, '/images/rooms/5-main.jpg',   0, TRUE),
    (5, '/images/rooms/5-beds.jpg',   1, FALSE),
    (6, '/images/rooms/6-main.jpg',   0, TRUE),
    (6, '/images/rooms/6-bath.jpg',   1, FALSE),
    (6, '/images/rooms/6-view.jpg',   2, FALSE);

INSERT INTO room_extras (room_id, extra_id) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2), (2, 6),
    (3, 1), (3, 4),
    (4, 1), (4, 2), (4, 5),
    (5, 1), (5, 2), (5, 4),
    (6, 1), (6, 2), (6, 5), (6, 6);

-- Pre-baked unavailability for July 2026 to mirror the prototype
INSERT INTO bookings (room_id, check_in, check_out) VALUES
    (1, '2026-07-02', '2026-07-05'),
    (1, '2026-07-17', '2026-07-19'),
    (1, '2026-07-25', '2026-07-27');
