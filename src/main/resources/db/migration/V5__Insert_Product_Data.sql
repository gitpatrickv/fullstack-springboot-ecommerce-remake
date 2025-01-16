INSERT INTO products (
    store_id,
    status,
    category,
    product_name,
    slug,
    description,
    total_sold,
    created_date,
    last_modified
)
SELECT
    1,
    'LISTED',
    'Motors',
    'Product Name',
    'product-name',
    'Example description for the product.',
    0,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM products
    WHERE product_name = 'Product Name'
      AND store_id = 1
);
