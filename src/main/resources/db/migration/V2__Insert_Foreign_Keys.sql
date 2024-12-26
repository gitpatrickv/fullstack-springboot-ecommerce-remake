ALTER TABLE stores
ADD CONSTRAINT fk_store_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);
