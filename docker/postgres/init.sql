CREATE TABLE dim_customer (
    customer_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    age INT,
    email VARCHAR(100) UNIQUE,
    country VARCHAR(50),
    postal_code VARCHAR(20),
    pet_type VARCHAR(50),
    pet_name VARCHAR(50),
    pet_breed VARCHAR(50)
);

CREATE TABLE dim_product (
    product_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price DECIMAL(10,2),
    weight DECIMAL(10,2),
    color VARCHAR(50),
    size VARCHAR(20),
    brand VARCHAR(100),
    material VARCHAR(100),
    description TEXT,
    rating DECIMAL(3,2),
    expiry_date DATE
);

CREATE TABLE dim_time (
    time_id SERIAL PRIMARY KEY,
    sale_date DATE UNIQUE NOT NULL,
    day INT NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    quarter INT NOT NULL
);

CREATE TABLE fact_sales (
    sale_id BIGSERIAL PRIMARY KEY,
    customer_id INT REFERENCES dim_customer(customer_id),
    product_id INT REFERENCES dim_product(product_id),
    time_id INT REFERENCES dim_time(time_id),
    quantity INT CHECK (quantity > 0),
    total_price DECIMAL(10,2) CHECK (total_price >= 0)
);

CREATE INDEX idx_fact_time ON fact_sales(time_id);
CREATE INDEX idx_fact_customer ON fact_sales(customer_id);