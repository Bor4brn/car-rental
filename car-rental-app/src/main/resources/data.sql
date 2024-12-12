
INSERT INTO locations (code, name, address) VALUES ('BASE_LOC_1', 'İstanbul Airport Base', 'Base Address 1');
INSERT INTO locations (code, name, address) VALUES ('BASE_LOC_2', 'İstanbul Sabiha Gökçen Airport Base', 'Base Address 2');


INSERT INTO equipments (name, price) VALUES ('GPS_BASE', 15.00);
INSERT INTO equipments (name, price) VALUES ('Child Seat Base', 10.00);


INSERT INTO member (name, address, email, phone, driving_license_number)
VALUES ('John Doe Base', '123 Main St Base', 'john.doe.base@example.com', '555-1234', 'DL123456_BASE');

INSERT INTO cars (
    barcode_number,
    license_plate_number,
    passenger_capacity,
    brand,
    model,
    mileage,
    transmission_type,
    daily_price,
    status,
    car_type
) VALUES (
    'CAR_BASE_123',
    '34ABC34',
    4,
    'ToyotaBase',
    'CorollaBase',
    10000,
    'AUTOMATIC',
    150.00,
    'AVAILABLE',
    'ECONOMY'
);

INSERT INTO reservations (
    day_count,
    creation_date,
    pick_up_date_time,
    drop_off_date_time,
    return_date,
    status,
    member_id,
    car_id,
    pick_up_location_id,
    drop_off_location_id,
    reservation_number
) VALUES (
    2,
    CURRENT_TIMESTAMP,
    DATEADD('DAY', 1, CURRENT_TIMESTAMP),
    DATEADD('DAY', 3, CURRENT_TIMESTAMP),
    NULL,
    'ACTIVE',
    1,
    1,
    1,
    2,
    'RESB1234'
);

INSERT INTO reservation_equipment (reservation_id, equipment_id)
VALUES (1, 1), (1, 2);
