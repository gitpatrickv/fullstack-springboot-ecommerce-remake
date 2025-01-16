INSERT INTO inventory (
    product_id,
    quantity,
    price,
    color,
    size,
    created_date,
    last_modified
)
SELECT
    1, 5, 2100, 'Red', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 10, 2600, 'Red', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 0, 3200, 'Red', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 6, 2900, 'Blue', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 11, 2800, 'Blue', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 7, 3400, 'Blue', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 10, 2300, 'Black', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 15, 3000, 'Black', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 8, 3100, 'Black', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 7, 2600, 'White', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 9, 2700, 'White', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 13, 3200, 'White', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 11, 3000, 'Brown', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 8, 3100, 'Brown', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 6, 3300, 'Brown', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
);


INSERT INTO inventory (
    product_id,
    quantity,
    price,
    color,
    size,
    created_date,
    last_modified
)
SELECT
    2, 5, 2100, 'Red', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 10, 2600, 'Red', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 0, 3200, 'Red', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 6, 2900, 'Blue', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 11, 2800, 'Blue', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 7, 3400, 'Blue', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 10, 2300, 'Black', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 15, 3000, 'Black', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 8, 3100, 'Black', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 7, 2600, 'White', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 9, 2700, 'White', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 13, 3200, 'White', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 11, 3000, 'Brown', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 8, 3100, 'Brown', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 6, 3300, 'Brown', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
);


INSERT INTO inventory (
    product_id,
    quantity,
    price,
    color,
    size,
    created_date,
    last_modified
)
SELECT
    3, 10, 1000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 3
);



