CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE, -- User credentials
    password VARCHAR(255) NOT NULL, -- Implement hashing later
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE customers (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255) NOT NULL
);

CREATE TABLE devices (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    device_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    customer_id INTEGER NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    note VARCHAR(255) NOT NULL,
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    device_id INTEGER NOT NULL,
    FOREIGN KEY (device_id) REFERENCES devices(id),
    created_by INTEGER NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE parts_sku (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE parts_individual (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    device_id INTEGER NOT NULL,
    FOREIGN KEY (device_id) REFERENCES devices(id)
);

CREATE TABLE purchase_order
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    total_price DOUBLE PRECISION,
    status      VARCHAR(255),
    timestamp   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    retailer    VARCHAR(255),
    order_no    VARCHAR(255)

);

CREATE TABLE po_items
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    part_name VARCHAR(255) NOT NULL,
    part_number VARCHAR(255) NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    quantity  VARCHAR(255),
    po_id     INTEGER,
    FOREIGN KEY (po_id) REFERENCES purchase_order(id)
);