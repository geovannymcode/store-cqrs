-- Datos de ejemplo
INSERT INTO orders (id, created_at, status, customer_name, customer_email, customer_address)
VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
     CURRENT_TIMESTAMP,
     'CREATED',
     'John Doe',
     'john@example.com',
     '123 Main St, Springfield, IL');

INSERT INTO order_items (id, order_id, product_id, product_name, quantity, unit_price)
VALUES
    ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
     'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
     'f47ac10b-58cc-4372-a567-0e02b2c3d479',
     'iPhone 15 Pro',
     2,
     999.99);