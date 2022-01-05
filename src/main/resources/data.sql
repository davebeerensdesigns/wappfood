INSERT INTO category (date_created, date_modified, description, name, slug)
VALUES
(localtimestamp, localtimestamp, '<p>This is a category description. It should only allow HTML.</p>', 'Nigiri', 'nigiri');

INSERT INTO product (date_created, date_modified, description, name, price, sku, slug, stock_qty, tax_class, taxable, total_sales)
VALUES
(localtimestamp, localtimestamp, '<p>This is a product description. It should only allow HTML.</p>', 'Avocado Nigiri', 1.50, 'NIG001', 'avocado-nigiri', 50, 'Standaard', true, 0);