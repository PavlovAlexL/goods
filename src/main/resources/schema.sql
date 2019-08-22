CREATE TABLE IF NOT EXISTS Product (
id                    INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
name                  VARCHAR(50) NOT NULL UNIQUE
);
COMMENT ON TABLE  Product IS 'Спарвочник - класификатор товаров';
COMMENT ON COLUMN Product.id IS 'Идентификатор';
COMMENT ON COLUMN Product.name IS 'Наименование товара';

CREATE TABLE IF NOT EXISTS Storage (
                                     id         INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                     product_id VARCHAR(50)            NOT NULL REFERENCES Product (id),
                                     amount     INTEGER                NOT NULL,
                                     price      DECIMAL                NOT NULL,
                                     date       DATE                   NOT NULL
);
COMMENT ON TABLE  Storage IS 'Склад';
COMMENT ON COLUMN Storage.id IS 'Идентификатор';
COMMENT ON COLUMN Storage.product_id IS 'Идентификатор наименования товара';
COMMENT ON COLUMN Storage.amount IS 'Количество';
COMMENT ON COLUMN Storage.price IS 'Цена';
COMMENT ON COLUMN Storage.date IS 'Дата';

CREATE TABLE IF NOT EXISTS Sales(
id              INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
storage_id      INTEGER NOT NULL REFERENCES Storage(id),
price           DECIMAL NOT NULL,
date            DATE NOT NULL
);
COMMENT ON TABLE  Sales IS 'Продажи';
COMMENT ON COLUMN Sales.id IS 'Идентификатор';
COMMENT ON COLUMN Sales.storage_id IS 'Идентификатор партии товара';
COMMENT ON COLUMN Sales.price IS 'Цена';
COMMENT ON COLUMN Sales.date IS 'Дата';