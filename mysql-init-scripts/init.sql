DELIMITER $$
CREATE PROCEDURE `create_order_and_calculate_total`(
	IN p_customer_id BIGINT,
    IN p_shipping_address VARCHAR(255),
    IN p_discount DOUBLE,
    IN p_priority VARCHAR(50),
    IN p_status VARCHAR(50),
    IN p_order_items JSON,
    OUT p_order_id BIGINT
)
BEGIN
DECLARE v_total DOUBLE DEFAULT 0;
    DECLARE i INT DEFAULT 0;
    DECLARE item_count INT;
    DECLARE v_product_id BIGINT;
    DECLARE v_quantity INT;
    DECLARE v_available_quantity INT;
    DECLARE v_price DOUBLE;
    DECLARE v_customer_expenditure DOUBLE;
    INSERT INTO orders (
        customer_id,
        shipping_address,
        order_date,
        processing_date,
        total,
        discount,
        status,
        priority,
        created_at
    ) VALUES (
        p_customer_id,
        p_shipping_address,
        CURDATE(),
        NULL,
        0, -- Tạm thời set total = 0
        p_discount,-- Tạm thời set p_discount = 0
        p_status,
         p_priority,
        CURDATE()
    );
    SET p_order_id = LAST_INSERT_ID();
    -- trả về giá trị AUTO_INCREMENT cuối cùng được sinh ra bởi câu lệnh INSERT cho order
    SET item_count = JSON_LENGTH(p_order_items);
        WHILE i < item_count DO
        -- Lấy thông tin từ JSON array
        SET v_product_id = JSON_EXTRACT(p_order_items, CONCAT('$[', i, '].productId'));
        SET v_quantity = JSON_EXTRACT(p_order_items, CONCAT('$[', i, '].quantity'));

        -- Lấy giá từ bảng product
        SELECT price, quantity INTO v_price, v_available_quantity  FROM product WHERE id = v_product_id;
        SELECT expenditure INTO v_customer_expenditure FROM customer WHERE id = p_customer_id;

        IF v_price IS NULL OR v_available_quantity =0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Product not found or price not available';
        END IF;
        IF v_available_quantity < v_quantity  THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient quantity.';
        END IF;


        -- Thêm vào order_item
        INSERT INTO orders_items (order_id, product_id, quantity)
        VALUES (p_order_id, v_product_id, v_quantity);

        -- Cộng vào tổng tiền
        SET v_total = v_total + (v_price * v_quantity);


		UPDATE product
		SET quantity = quantity - v_quantity
		WHERE id = v_product_id;



        SET i = i + 1;
    END WHILE;

    -- Áp dụng giảm giá
    SET v_total = v_total * (1 - (IFNULL(p_discount, 0)/100));
    SET v_customer_expenditure= v_customer_expenditure + v_total;

	UPDATE customer
	SET expenditure = v_customer_expenditure
	WHERE id = p_customer_id;
    -- Cập nhật tổng tiền cho order
    UPDATE orders
    SET total = v_total
    WHERE id = p_order_id;


END$$

DELIMITER ;


-- Categories table
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(350) NOT NULL
);

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(200),
    email VARCHAR(150) UNIQUE NOT NULL,
    username VARCHAR(100) NOT NULL,
    membership VARCHAR(100),
    role VARCHAR(100) NOT NULL,
    password VARCHAR(100),
    expenditure DOUBLE

);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    updated_at DATETIME(6),
    updated_by VARCHAR(255),
    description VARCHAR(255),
    is_active BIT(1),
    name VARCHAR(350) NOT NULL,
    price FLOAT NOT NULL,
    quantity INT NOT NULL,
    thumbnail VARCHAR(350),
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
-- Coupons table
CREATE TABLE IF NOT EXISTS coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    updated_at DATETIME(6),
    updated_by VARCHAR(255),
    is_active BIT(1),
    description VARCHAR(255),
    discount DOUBLE,
    code VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);
-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_by VARCHAR(255),
    updated_at DATETIME(6),
    updated_by VARCHAR(255),
    coupon_id BIGINT,
    discount DOUBLE,
    order_date DATETIME(6),
    processing_date DATETIME(6),
    shipping_address VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    total DOUBLE,
    customer_id BIGINT,
    FOREIGN KEY (coupon_id) REFERENCES coupon(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS orders_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    order_id BIGINT,
    quantity INT
);

INSERT INTO role (name) VALUES
('ADMIN'), ('USER');


INSERT INTO category (name) VALUES
('Electric'), ('Cosmetic'), ('Books');

INSERT INTO product (name, description, quantity, price, thumbnail, category_id) VALUES
('Laptop','A new laptop description',7,999, '84f4a668-f12c-4186-a351-b34e2abdc137.jpg',2);

INSERT INTO product (name, description, quantity, price, thumbnail, category_id) VALUES
('Cushion','A new cushion description',10,19, '84f4a668-f12c-4186-a351-b34e2abdc137.jpg',1);

INSERT INTO product (name, description, quantity, price, thumbnail, category_id) VALUES
('The complete guide of java','A new java book description',9,39, '84f4a668-f12c-4186-a351-b34e2abdc137.jpg',3);