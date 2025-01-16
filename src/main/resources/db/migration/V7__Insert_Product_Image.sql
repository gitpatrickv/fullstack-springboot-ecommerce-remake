INSERT INTO product_images (
    product_id,
    product_image
)
SELECT 1, 'https://img.freepik.com/free-vector/realistic-neon-lights-background_23-2148907367.jpg'
FROM products
WHERE product_id = 1;

INSERT INTO product_images (
    product_id,
    product_image
)
SELECT 2, 'https://png.pngtree.com/thumb_back/fh260/background/20230408/pngtree-rainbow-curves-abstract-colorful-background-image_2164067.jpg'
FROM products
WHERE product_id = 2;

INSERT INTO product_images (
    product_id,
    product_image
)
SELECT 3, 'https://st2.depositphotos.com/3336339/8196/i/450/depositphotos_81969890-stock-photo-red-chaotic-cubes-wall-background.jpg'
FROM products
WHERE product_id = 3;


