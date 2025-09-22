ALTER TABLE product_category
    ADD CONSTRAINT pk_product_category PRIMARY KEY (category_id, product_id);

ALTER TABLE product
    DROP COLUMN created_by;

ALTER TABLE product
    DROP COLUMN updated_by;

ALTER TABLE product
    ADD created_by VARCHAR(255) NOT NULL;

ALTER TABLE product
    ADD updated_by VARCHAR(255) NOT NULL;