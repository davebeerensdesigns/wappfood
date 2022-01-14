INSERT INTO category (date_created, date_modified, description, name, slug)
VALUES
(localtimestamp, localtimestamp, 'This is a category description. It should not allow HTML.', 'Nigiri', 'nigiri');

INSERT INTO product (date_created, date_modified, description, name, price, sku, slug, stock_qty, tax_class, taxable, total_sales, category_id)
VALUES
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Avocado Nigiri', 1.50, 'NIG001', 'avocado-nigiri', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, 'This is a product description. It should not allow HTML.', 'Zalm Nigiri', 1.60, 'NIG002', 'zalm-nigiri', 50, 'Standaard', true, 0, 1);

INSERT INTO users (username, password, email, enabled)
VALUES
('user', '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS','user@wappfood.nl', TRUE),
('admin', '$2a$12$GEnymscrjZufETmnysLVC.tcr.8OHhHGz/XQhYlvdkjCW6DDOtsPS', 'admin@wappfood.nl', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');