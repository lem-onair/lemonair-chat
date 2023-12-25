CREATE TABLE chat
(
    id SERIAL PRIMARY KEY,
    sender VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    room_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);