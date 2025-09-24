-- Tabla de inventario
CREATE TABLE inventory (
                           id UUID PRIMARY KEY,
                           product_id UUID NOT NULL UNIQUE,
                           available_stock INTEGER NOT NULL DEFAULT 0,
                           reserved_stock INTEGER NOT NULL DEFAULT 0,
                           last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices
CREATE INDEX idx_inventory_product_id ON inventory(product_id);
CREATE INDEX idx_inventory_stock ON inventory(available_stock);