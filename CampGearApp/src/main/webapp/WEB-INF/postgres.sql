CREATE TABLE camping_gear (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100),
    quantity INTEGER CHECK (quantity >= 0),
    memo TEXT
);