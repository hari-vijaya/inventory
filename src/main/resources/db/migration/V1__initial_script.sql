--Product table: This table will store information about each product in the supermarket.
CREATE TABLE product
(
    id         INT PRIMARY KEY IDENTITY,
    name       NVARCHAR(100)  NOT NULL UNIQUE,
    category   NVARCHAR(50),
    brand      NVARCHAR(50),
    price      DECIMAL(10, 2) NOT NULL,
    created_by NVARCHAR(50)   NOT NULL,
    created_on DATETIME       NOT NULL,
    updated_by NVARCHAR(50)   NOT NULL,
    updated_on DATETIME       NOT NULL
);

--Supplier table: This table will store information about product suppliers.
CREATE TABLE supplier
(
    id            INT PRIMARY KEY IDENTITY,
    supplier_name NVARCHAR(100) NOT NULL,
    contact_name  NVARCHAR(100) NOT NULL UNIQUE,
    phone         NVARCHAR(20),
    email         NVARCHAR(100),
    created_by    NVARCHAR(50)  NOT NULL,
    created_on    DATETIME      NOT NULL,
    updated_by    NVARCHAR(50)  NOT NULL,
    updated_on    DATETIME      NOT NULL
);

--Stock table: This table will keep track of the stock levels for each product.
CREATE TABLE stock
(
    id                INT PRIMARY KEY IDENTITY,
    product_id        INT          NOT NULL,
    quantity          INT          NOT NULL,
    last_stock_update DATETIME     NOT NULL,
    created_by        NVARCHAR(50) NOT NULL,
    created_on        DATETIME     NOT NULL,
    updated_by        NVARCHAR(50) NOT NULL,
    updated_on        DATETIME     NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

--Purchase table: This table will record information about purchases made from suppliers.
CREATE TABLE purchase
(
    id             INT PRIMARY KEY IDENTITY,
    product_id     INT          NOT NULL,
    supplier_id    INT          NOT NULL,
    quantity       INT          NOT NULL,
    purchased_date DATETIME     NOT NULL,
    created_by     NVARCHAR(50) NOT NULL,
    created_on     DATETIME     NOT NULL,
    updated_by     NVARCHAR(50) NOT NULL,
    updated_on     DATETIME     NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (supplier_id) REFERENCES supplier (id)
);

--Sale table: This table will store information about sales made to customers.
CREATE TABLE sale
(
    id         INT PRIMARY KEY,
    bill_id    INT          NOT NULL,
    product_id INT          NOT NULL,
    quantity   INT          NOT NULL,
    sales_date DATE         NOT NULL,
    created_by NVARCHAR(50) NOT NULL,
    created_on DATETIME     NOT NULL,
    updated_by NVARCHAR(50) NOT NULL,
    updated_on DATETIME     NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product (id)
);

--Employee table: This table will store information about employees managing the inventory.
CREATE TABLE user_authentication
(
    id                     INT PRIMARY KEY,
    username               NVARCHAR(50) UNIQUE NOT NULL,
    roles                  NVARCHAR(500)       NOT NULL,
    password               NVARCHAR(200)       NOT NULL,
    account_expiration     DATETIME            NOT NULL,
    is_account_locked      BIT                 NOT NULL DEFAULT 0,
    credentials_expiration DATETIME            NOT NULL,
    is_active              BIT                 NOT NULL DEFAULT 1,
    created_by             NVARCHAR(50)        NOT NULL,
    created_on             DATETIME            NOT NULL,
    updated_by             NVARCHAR(50)        NOT NULL,
    updated_on             DATETIME            NOT NULL
);
