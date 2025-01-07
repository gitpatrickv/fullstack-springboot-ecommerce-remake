ALTER TABLE stores
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE products
ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES stores(store_id);

ALTER TABLE inventory
ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

ALTER TABLE product_images
ADD CONSTRAINT fk_image_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

ALTER TABLE address
ADD CONSTRAINT fk_address_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE cart
ADD CONSTRAINT fk_cart_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE cart_item
ADD CONSTRAINT fk_cart_item_cart_id FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
ADD CONSTRAINT fk_cart_item_inventory_id FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id);