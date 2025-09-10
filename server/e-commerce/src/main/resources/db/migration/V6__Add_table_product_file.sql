CREATE TABLE product_file
(
    file_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

ALTER TABLE product_file
    ADD CONSTRAINT fk_profil_on_file FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE product_file
    ADD CONSTRAINT fk_profil_on_product FOREIGN KEY (product_id) REFERENCES product (id);