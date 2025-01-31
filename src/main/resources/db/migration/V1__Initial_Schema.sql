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
    `average_rating` DECIMAL(3,1) DEFAULT 0 CHECK (average_rating >= 0) NOT NULL,
    `reviews_count` INT DEFAULT 0 CHECK (reviews_count >= 0) NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    `product_id` INT AUTO_INCREMENT PRIMARY KEY,
    `store_id` INT NOT NULL,
    `status` ENUM('LISTED', 'SUSPENDED', 'DELETED') NOT NULL,
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
                        'Personal_Care',
                        'Bags',
                        'Cameras',
                        'Shoes',
                        'Audio',
                        'Babies_And_Kids') NOT NULL,
    `total_sold` INT DEFAULT 0 CHECK (total_sold >= 0) NOT NULL,
    `average_rating` DECIMAL(3,1) DEFAULT 0 CHECK (average_rating >= 0) NOT NULL,
    `reviews_count` INT DEFAULT 0 CHECK (reviews_count >= 0) NOT NULL,
    `product_name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inventory (
    `inventory_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `quantity` INT DEFAULT 0 NOT NULL CHECK (quantity >= 0),
    `price` DECIMAL NOT NULL CHECK (price > 0),
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

CREATE TABLE IF NOT EXISTS address (
    `address_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `status` ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    `address_type` ENUM('HOME', 'OFFICE') NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `contact_number` VARCHAR(15) NOT NULL,
    `street_address` VARCHAR(255) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    `post_code` VARCHAR(10) NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart (
    `cart_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNIQUE NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_item (
    `cart_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `cart_id` INT NOT NULL,
    `inventory_id` INT NOT NULL,
    `quantity` INT NOT NULL CHECK (quantity >= 1),
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `store_id` INT NOT NULL,
    `is_store_rated` BOOLEAN DEFAULT FALSE,
    `recipient_name` VARCHAR(255) NOT NULL,
    `contact_number` VARCHAR(20) NOT NULL,
    `delivery_address` VARCHAR(255) NOT NULL,
    `payment_method` ENUM('CASH_ON_DELIVERY', 'STRIPE_PAYMENT') NOT NULL,
    `total_amount` DECIMAL NOT NULL CHECK (total_amount > 0),
    `order_status` ENUM('TO_PAY', 'TO_SHIP', 'TO_RECEIVE', 'COMPLETED', 'RATED', 'CANCELLED') NOT NULL,
    `delivery_cost` INT DEFAULT 0,
    `item_quantity` INT NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    `order_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `inventory_id` INT NOT NULL,
    `review_status` ENUM('TO_REVIEW', 'REVIEWED') NOT NULL,
    `product_name` VARCHAR(100) NOT NULL,
    `quantity` INT NOT NULL,
    `product_price` DECIMAL NOT NULL CHECK (product_price > 0),
    `color` VARCHAR(30) DEFAULT NULL,
    `size` VARCHAR(30) DEFAULT NULL,
    `product_image` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_reviews (
    `product_review_id` INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `rating` INT NOT NULL CHECK (rating > 0 AND rating <= 5),
    `customer_review` VARCHAR(255) DEFAULT NULL,
    `seller_response` VARCHAR(255) DEFAULT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS store_reviews (
    `store_review_id` INT AUTO_INCREMENT PRIMARY KEY,
    `store_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `rating` INT NOT NULL CHECK (rating > 0 AND rating <= 5),
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);

CREATE TABLE IF NOT EXISTS favorites (
    `favorite_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `created_date` TIMESTAMP,
    `last_modified` TIMESTAMP
);





