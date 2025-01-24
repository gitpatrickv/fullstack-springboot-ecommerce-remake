INSERT INTO stores (user_id, status, store_name, contact_number,  average_rating, reviews_count, created_date, last_modified)
SELECT 1, 'ACTIVE', 'Market Market', '09194566543', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM stores WHERE store_name = 'Market Market'
);

INSERT INTO stores (user_id, status, store_name, contact_number, average_rating, reviews_count, created_date, last_modified)
SELECT 2, 'ACTIVE', 'Grocery Store', '09201233214', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM stores WHERE store_name = 'Grocery Store'
);

INSERT INTO stores (user_id, status, store_name, contact_number, average_rating, reviews_count, created_date, last_modified)
SELECT 3, 'ACTIVE', 'Super Market', '09675423123', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM stores WHERE store_name = 'Super Market'
);

