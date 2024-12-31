CREATE TABLE IF NOT EXISTS users (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `status` ENUM('ACTIVE', 'SUSPENDED', 'DELETED') NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `gender` ENUM('MALE', 'FEMALE') NOT NULL,
    `role` ENUM('USER', 'SELLER', 'ADMIN') NOT NULL,
    `picture` VARCHAR(255) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stores (
    `store_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNIQUE NOT NULL,
    `status` ENUM('ACTIVE', 'SUSPENDED', 'DELETED') NOT NULL,
    `store_name` VARCHAR(100) UNIQUE NOT NULL,
    `contact_number` VARCHAR(20) UNIQUE NOT NULL,
    `picture` VARCHAR(255) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    `product_id` INT AUTO_INCREMENT PRIMARY KEY,
    `store_id` INT NOT NULL,
    `status` ENUM('ACTIVE', 'SUSPENDED', 'DELETED') NOT NULL,
    `category` ENUM('Motors',
                        'Home_Appliances',
                        'Apparel',
                        'Toys_And_Games',
                        'Groceries',
                        'Mobiles',
                        'Sports',
                        'Office_Supplies',
                        'Accessories',
                        'Computers',
                        'Health',
                        'Bags') NOT NULL,
    `product_name` VARCHAR(100) NOT NULL,
    `slug` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `total_sold` INT DEFAULT 0,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inventory (
    `inventory_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `quantity` INT NOT NULL,
    `price` INT NOT NULL,
    `discount_percent` INT DEFAULT 0,
    `color` VARCHAR(30) DEFAULT NULL,
    `size` VARCHAR(30) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_images (
    `image_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `product_image` VARCHAR(255) NOT NULL
);




