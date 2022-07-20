CREATE SCHEMA IF NOT EXISTS ims;

USE ims ;

CREATE TABLE IF NOT EXISTS customers(
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS items (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL, 
    value DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id INT NOT NULL AUTO_INCREMENT,
    order_date DATE NOT NULL,
    fk_customer_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (fk_customer_id) REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS orderItems (
    id INT NOT NULL AUTO_INCREMENT,
    fk_order_id INT NOT NULL, 
    fk_item_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (fk_order_id) REFERENCES orders(id),
    FOREIGN KEY (fk_item_id) REFERENCES items(id)
);