-- Create the sequence manually
CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

-- Create Category Table
CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY DEFAULT nextval('category_seq'),
    name TEXT NOT NULL UNIQUE,
    description TEXT
);

-- Create Product Table
CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    description TEXT,
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    category_id BIGINT REFERENCES category(id) ON DELETE CASCADE
);

-- Index foreign key for performance
CREATE INDEX IF NOT EXISTS idx_product_category_id ON product (category_id);