INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Apparel',
    'With Variation',
    'Example description for the product.',
    200,
    4.5,
    30,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'With Variation'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Mobiles',
    'Variation',
    'Example description for the product.',
    100,
    3.5,
    50,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Variation'
      AND store_id = 2
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Motors',
    'Without Variation',
    'Example description for the product.',
    500,
    4.9,
    300,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Without Variation'
      AND store_id = 2
);


INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Sports',
    'Product Name',
    'Example description for the product.',
    20,
    3.3,
    10,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Product Name'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    3,
    'LISTED',
    'Computers',
    'Item Name',
    'Example description for the product.',
    60,
    2,
    30,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Item Name'
      AND store_id = 3
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Motors',
    'Motors',
    'Example description for the product.',
    10,
    1.5,
    5,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Motors'
      AND store_id = 2
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Home_Appliances',
    'Home Appliance',
    'Example description for the product.',
    88,
    2.5,
    44,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Home Appliance'
      AND store_id = 2
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Apparel',
    'Apparel',
    'Example description for the product.',
    33,
    3.1,
    20,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Apparel'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    3,
    'LISTED',
    'Toys_And_Games',
    'Toys And Games',
    'Example description for the product.',
    99,
    5,
    70,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Toys And Games'
      AND store_id = 3
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    3,
    'LISTED',
    'Groceries',
    'Groceries',
    'Example description for the product.',
    15,
    1.8,
    8,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Groceries'
      AND store_id = 3
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    3,
    'LISTED',
    'Mobiles',
    'Mobiles',
    'Example description for the product.',
    250,
    4.6,
    200,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Mobiles'
      AND store_id = 3
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Sports',
    'Sports',
    'Example description for the product.',
    33,
    3.3,
    20,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Sports'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Office_Supplies',
    'Office Supplies',
    'Example description for the product.',
    70,
    4.3,
    50,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Office Supplies'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Accessories',
    'Accessories',
    'Example description for the product.',
    5,
    5,
    3,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Accessories'
      AND store_id = 2
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    2,
    'LISTED',
    'Computers',
    'Computers',
    'Example description for the product.',
    20,
    2,
    15,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Computers'
      AND store_id = 2
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Personal_Care',
    'Personal Care',
    'Example description for the product.',
    999,
    4.8,
    888,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Personal Care'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Bags',
    'Bags',
    'Example description for the product.',
    111,
    4.2,
    90,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Bags'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Cameras',
    'Cameras',
    'Example description for the product.',
    10,
    1.8,
    9,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Cameras'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Shoes',
    'Shoes',
    'Example description for the product.',
    100,
    3.4,
    86,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Shoes'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Audio',
    'Audio',
    'Example description for the product.',
    444,
    4.4,
    400,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Audio'
      AND store_id = 1
);

INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    description,
    total_sold,
    average_rating,
    reviews_count,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Babies_And_Kids',
    'Babies And Kids',
    'Example description for the product.',
    15,
    5,
    14,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Babies And Kids'
      AND store_id = 1
);



