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
    1, 10, 2200, 'Red', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 0, 2300, 'Red', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 6, 2100, 'Blue', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 11, 2200, 'Blue', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 7, 2300, 'Blue', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 10, 2100, 'Black', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 15, 2200, 'Black', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 8, 2300, 'Black', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 7, 2100, 'White', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 9, 2200, 'White', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 13, 2300, 'White', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 11, 2100, 'Brown', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 8, 2200, 'Brown', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 1
)
UNION ALL
SELECT
    1, 6, 2300, 'Brown', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
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
    2, 10, 2200, 'Red', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 0, 2300, 'Red', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 6, 2100, 'Blue', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 11, 2200, 'Blue', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 7, 2300, 'Blue', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 10, 2100, 'Black', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 15, 2200, 'Black', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 8, 2300, 'Black', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 7, 2100, 'White', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 9, 2200, 'White', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 13, 2300, 'White', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 11, 2100, 'Brown', 'S', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 8, 2200, 'Brown', 'M', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 2
)
UNION ALL
SELECT
    2, 6, 2300, 'Brown', 'L', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
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
    4, 10, 3000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 4
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
    5, 10, 500, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 5
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
    6, 10, 100, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 6
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
    7, 10, 5000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 7
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
    8, 10, 10000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 8
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
    9, 10, 1500, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 9
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
    10, 10, 300, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 10
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
    11, 10, 2500, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 11
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
    12, 10, 4000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 12
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
    13, 10, 600, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 13
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
    14, 10, 2800, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 14
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
    15, 10, 3700, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 15
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
    16, 10, 800, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 16
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
    17, 10, 1000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 17
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
    18, 10, 500, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 18
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
    19, 10, 1000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 19
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
    20, 10, 2000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 20
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
    21, 10, 3000, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM products
    WHERE product_id = 21
);












