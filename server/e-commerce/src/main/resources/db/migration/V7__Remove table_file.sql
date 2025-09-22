ALTER TABLE product_file
    DROP CONSTRAINT fk_profil_on_file;

ALTER TABLE product_file
    DROP CONSTRAINT fk_profil_on_product;

DROP TABLE file CASCADE;

DROP TABLE product_file CASCADE;