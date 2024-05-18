CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    table_number INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    serving_code VARCHAR(255) NOT NULL,
    phone_number_client VARCHAR(255) NOT NULL,
    client_name VARCHAR(255) NOT NULL,
    deposit NUMERIC(10, 2) NOT NULL,
    manager_code VARCHAR(255) NOT NULL
);;
