INSERT INTO users (status, email, name, password, gender, role, account_non_expired, account_non_locked, credentials_non_expired, enabled, created_date, last_modified)
SELECT 'ACTIVE', 'patrick@gmail.com', 'patrick name', '$2a$10$v3PjyBH9NL1wqli3a4gZFOPJMjiraGkTDEO/IzTQ9jLquqI41XCaG', 'MALE', 'SELLER', true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'patrick@gmail.com'
);

INSERT INTO users (status, email, name, password, gender, role, account_non_expired, account_non_locked, credentials_non_expired, enabled, created_date, last_modified)
SELECT 'ACTIVE', 'one@gmail.com', 'one name', '$2a$10$v3PjyBH9NL1wqli3a4gZFOPJMjiraGkTDEO/IzTQ9jLquqI41XCaG', 'MALE', 'SELLER', true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'one@gmail.com'
);

INSERT INTO users (status, email, name, password, gender, role, account_non_expired, account_non_locked, credentials_non_expired, enabled, created_date, last_modified)
SELECT 'ACTIVE', 'user@gmail.com', 'user name', '$2a$10$v3PjyBH9NL1wqli3a4gZFOPJMjiraGkTDEO/IzTQ9jLquqI41XCaG', 'MALE', 'SELLER', true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'user@gmail.com'
);

