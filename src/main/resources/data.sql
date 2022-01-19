INSERT INTO category (date_created, date_modified, description, name, slug)
VALUES
(localtimestamp, localtimestamp, 'This is a category description. It should not allow HTML.', 'Nigiri', 'nigiri');

INSERT INTO product (date_created, date_modified, description, name, price, sku, slug, stock_qty, tax_class, taxable, total_sales, category_id)
VALUES
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Avocado Nigiri', 1.50, 'NIG001', 'avocado-nigiri', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri', 1.60, 'NIG002', 'zalm-nigiri', 50, 'Standaard', true, 0, 1);

INSERT INTO users (username, apikey, email, enabled, password)
VALUES
('user', 'fdgtrthahrddfa','user@wappfood.nl', TRUE, '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS'),
('admin', 'greahgrtwsreg', 'admin@wappfood.nl', TRUE, '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS');

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('user', 'ROLE_CUSTOMER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');

INSERT INTO customer (date_created, date_modified, first_name, is_paying_customer, last_name, email, username)
VALUES
(localtimestamp, localtimestamp, 'firstName', true, 'lastName', 'user@wappfood.nl', 'user');

INSERT INTO customer_meta (meta_key, meta_value, customer_id)
VALUES
('_billing_address', 'straatnaam 1', 1),
('_billing_postcode', '1234 AA', 1);