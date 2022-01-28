INSERT INTO category (date_created, date_modified, description, name, slug)
VALUES
(localtimestamp, localtimestamp, 'This is a category description. It should not allow HTML.', 'Nigiri', 'nigiri');

INSERT INTO product (date_created, date_modified, description, name, price, sku, slug, stock_qty, tax_class, taxable, total_sales, category_id)
VALUES
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Avocado Nigiri', 1.50, 'NIG001', 'avocado-nigiri', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri', 1.60, 'NIG002', 'zalm-nigiri', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 2', 1.60, 'NIG003', 'zalm-nigiri-2', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 3', 1.60, 'NIG004', 'zalm-nigiri-3', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 4', 1.60, 'NIG005', 'zalm-nigiri-4', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 5', 1.60, 'NIG006', 'zalm-nigiri-5', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 6', 1.60, 'NIG007', 'zalm-nigiri-6', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 7', 1.60, 'NIG008', 'zalm-nigiri-7', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 8', 1.60, 'NIG009', 'zalm-nigiri-8', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 9', 1.60, 'NIG0010', 'zalm-nigiri-9', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 10', 1.60, 'NIG0011', 'zalm-nigiri-10', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 11', 1.60, 'NIG0012', 'zalm-nigiri-11', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 12', 1.60, 'NIG0013', 'zalm-nigiri-12', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 13', 1.60, 'NIG0014', 'zalm-nigiri-13', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 14', 1.60, 'NIG0015', 'zalm-nigiri-14', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri 15', 1.60, 'NIG0016', 'zalm-nigiri-15', 50, 'Standaard', true, 0, 1);

INSERT INTO users (date_created, date_modified, username, apikey, email, enabled, password)
VALUES
(localtimestamp, localtimestamp, 'user', 'fdgtrthahrddfa','user@wappfood.nl', TRUE, '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS'),
(localtimestamp, localtimestamp, 'admin', 'greahgrtwsreg', 'admin@wappfood.nl', TRUE, '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS');

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
('_billing_phone', '0612345678', 1),
('_billing_email', 'user@wappfood.nl', 1),
('_billing_company', 'Wappstars', 1),
('_billing_address', 'hurksestraat 28b', 1),
('_billing_city', 'Eindhoven', 1),
('_billing_state', 'Noord Brabant', 1),
('_billing_postcode', '1234 aa', 1),
('_billing_country', 'Nederland', 1),
('_shipping_company', 'Wappstars', 1),
('_shipping_address', 'hurksestraat 28b', 1),
('_shipping_city', 'Eindhoven', 1),
('_shipping_state', 'Noord Brabant', 1),
('_shipping_postcode', '1234 aa', 1),
('_shipping_country', 'Nederland', 1);