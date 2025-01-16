INSERT INTO stores (user_id, status, store_name, contact_number, created_date, last_modified)
SELECT 1, 'ACTIVE', 'Market Market', '09194566543', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM stores WHERE store_name = 'Market Market'
);

INSERT INTO stores (user_id, status, store_name, contact_number, created_date, last_modified)
SELECT 2, 'ACTIVE', 'Super Store', '09201233214', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM stores WHERE store_name = 'Super Store'
);
