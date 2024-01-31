insert into user_authentication (id, username, roles, password, account_expiration, is_account_locked,
                                 credentials_expiration, is_active, created_by, created_on, updated_by, updated_on)
values (1, 'hmuruga1', 'ADMIN', '$2a$04$OLxe8mbexYUaf0elPRHYiuLzXlKJjVG9khE/IfMZNO/Fr9JKxtIRa',
        '2024-08-13 18:22:47.000', 0, '2024-08-13 18:22:47.000', 1, 'SYSTEM', '2024-08-13 18:22:47.000',
        'SYSTEM', '2024-08-13 18:22:47.000');

insert into dbo.product (name, category, price, brand, created_by, created_on, updated_by, updated_on)
values ('Samsung21FE', 'Mobile', 40000, 'SAMSUNG', 'SYSTEM', '2024-08-13 18:22:47.000',
        'SYSTEM', '2024-08-13 18:22:47.000');


insert into dbo.supplier (supplier_name, contact_name, phone, email, created_by, created_on, updated_by, updated_on)
values ('Hari', 'Hari', '8675750139', 'hariashok.1996@gmail.com', 'SYSTEM', '2024-08-13 18:22:47.000',
        'SYSTEM', '2024-08-13 18:22:47.000');

insert into dbo.stock (product_id, quantity, last_stock_update, created_by, created_on, updated_by, updated_on)
values (1, 12, '2024-08-13 18:22:47.000', 'SYSTEM', '2024-08-13 18:22:47.000',
        'SYSTEM', '2024-08-13 18:22:47.000');

insert into dbo.purchase (product_id, supplier_id, quantity, purchased_date, created_by, created_on, updated_by, updated_on)
values (1,1, 12, '2024-08-13 18:22:47.000', 'SYSTEM', '2024-08-13 18:22:47.000',
        'SYSTEM', '2024-08-13 18:22:47.000');
