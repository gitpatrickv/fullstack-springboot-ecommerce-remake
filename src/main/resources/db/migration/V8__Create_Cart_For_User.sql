INSERT INTO cart (user_id, created_date, last_modified)
SELECT 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
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

INSERT INTO cart (user_id, created_date, last_modified)
SELECT 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
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

INSERT INTO cart (user_id, created_date, last_modified)
SELECT 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = 3
)
AND NOT EXISTS (
    SELECT 1
    FROM cart
    WHERE user_id = 3
);

