-- V1__create_initial_schema.sql
-- Esquema inicial para el módulo products con CQRS

-- =====================================================
-- LADO COMMAND: Modelos para escritura (normalizados)
-- =====================================================

-- Tabla principal de productos
CREATE TABLE products (
                          id UUID PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          category VARCHAR(100),
                          price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                          stock INTEGER NOT NULL CHECK (stock >= 0),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de reviews (relación 1:N con products)
CREATE TABLE reviews (
                         id UUID PRIMARY KEY,
                         product_id UUID NOT NULL REFERENCES products(id),
                         vote INTEGER NOT NULL CHECK (vote >= 1 AND vote <= 5),
                         comment TEXT,
                         author VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para el lado command
CREATE INDEX idx_reviews_product_id ON reviews(product_id);
CREATE INDEX idx_products_category ON products(category);

-- =====================================================
-- LADO QUERY: Modelos para lectura (desnormalizados)
-- =====================================================

-- Vista de productos optimizada para consultas
CREATE TABLE product_views (
                               id UUID PRIMARY KEY,
                               name VARCHAR(255),
                               description TEXT,
                               category VARCHAR(100),
                               price DECIMAL(10,2),
                               stock INTEGER,

    -- Campos desnormalizados para performance
                               average_rating DECIMAL(3,2) DEFAULT 0.0,
                               review_count INTEGER DEFAULT 0,

                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices optimizados para consultas comunes
CREATE INDEX idx_product_views_category ON product_views(category);
CREATE INDEX idx_product_views_rating ON product_views(average_rating DESC);
CREATE INDEX idx_product_views_price ON product_views(price);

-- =====================================================
-- DATOS DE EJEMPLO PARA TESTING
-- =====================================================

-- Insertar productos de ejemplo
INSERT INTO products (id, name, description, category, price, stock) VALUES
                                                                         ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'iPhone 15 Pro', 'Latest iPhone with titanium design', 'Electronics', 999.99, 50),
                                                                         ('6ba7b810-9dad-11d1-80b4-00c04fd430c8', 'MacBook Air M3', 'Powerful laptop with M3 chip', 'Electronics', 1199.99, 25),
                                                                         ('6ba7b811-9dad-11d1-80b4-00c04fd430c8', 'AirPods Pro', 'Wireless earbuds with noise cancellation', 'Electronics', 249.99, 100);

-- Insertar reviews de ejemplo
INSERT INTO reviews (id, product_id, vote, comment, author) VALUES
                                                                ('r1', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 5, 'Amazing phone, love the titanium!', 'John Doe'),
                                                                ('r2', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 4, 'Great camera quality', 'Jane Smith'),
                                                                ('r3', '6ba7b810-9dad-11d1-80b4-00c04fd430c8', 5, 'Best laptop I have ever used', 'Tech Reviewer');

-- Copiar datos a la vista de lectura
INSERT INTO product_views (id, name, description, category, price, stock, average_rating, review_count)
SELECT
    p.id,
    p.name,
    p.description,
    p.category,
    p.price,
    p.stock,
    COALESCE(r.avg_rating, 0.0) as average_rating,
    COALESCE(r.review_count, 0) as review_count
FROM products p
         LEFT JOIN (
    SELECT
        product_id,
        ROUND(AVG(vote::numeric), 2) as avg_rating,
        COUNT(*) as review_count
    FROM reviews
    GROUP BY product_id
) r ON p.id = r.product_id;