-- VXXX__order_items_remove_id_for_element_collection.sql
ALTER TABLE order_items DROP CONSTRAINT IF EXISTS order_items_pkey;
ALTER TABLE order_items DROP COLUMN IF EXISTS id;

-- Definir PK compuesta (ajusta si necesitas permitir mismo producto repetido)
ALTER TABLE order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (order_id, product_id);
