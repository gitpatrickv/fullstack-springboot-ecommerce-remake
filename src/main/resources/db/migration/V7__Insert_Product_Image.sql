INSERT INTO product_images (
    product_id,
    product_image
)
SELECT 1, 'https://img.freepik.com/free-vector/realistic-neon-lights-background_23-2148907367.jpg'
FROM products
WHERE product_id = 1;
