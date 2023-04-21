CREATE TABLE product (
    id SERIAL,
    name VARCHAR(20),
    price DECIMAL(10,2),
    CONSTRAINT product_pk PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX name_un_idx ON product(name);

INSERT INTO product(name, price) VALUES ('Soccer ball', 100.22);
INSERT INTO product(name, price) VALUES ('Basket ball', 11.35);
INSERT INTO product(name, price) VALUES ('Volley ball', 1.99);