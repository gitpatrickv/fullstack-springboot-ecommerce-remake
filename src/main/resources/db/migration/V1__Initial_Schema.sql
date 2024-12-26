CREATE TABLE IF NOT EXISTS users (
    `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `is_seller` BOOLEAN DEFAULT FALSE,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `gender` ENUM('MALE', 'FEMALE') NOT NULL,
    `role` ENUM('USER', 'ADMIN') NOT NULL,
    `picture` VARCHAR(255) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stores (
    `store_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT UNIQUE NOT NULL,
    `is_active` BOOLEAN DEFAULT FALSE,
    `store_name` VARCHAR(100) UNIQUE NOT NULL,
    `contact_number` VARCHAR(20) NOT NULL,
    `picture` VARCHAR(255) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);
