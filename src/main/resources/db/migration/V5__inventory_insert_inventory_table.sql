-- Datos iniciales basados en los productos existentes
INSERT INTO inventory (id, product_id, available_stock, last_updated)
SELECT
    gen_random_uuid(),
    id,
    stock,
    CURRENT_TIMESTAMP
FROM products;