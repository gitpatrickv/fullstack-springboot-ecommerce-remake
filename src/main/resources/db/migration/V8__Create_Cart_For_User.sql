INSERT INTO cart (total_items, user_id, created_date, last_modified)
SELECT 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = 1
)
AND NOT EXISTS (
    SELECT 1
    FROM cart
    WHERE user_id = 1
);

INSERT INTO cart (total_items, user_id, created_date, last_modified)
SELECT 0, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = 2
)
AND NOT EXISTS (
    SELECT 1
    FROM cart
    WHERE user_id = 2
);
