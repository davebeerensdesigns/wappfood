INSERT INTO category (date_created, date_modified, description, name, slug)
VALUES
(localtimestamp, localtimestamp, '<p>This is a category description. It should not allow HTML.</p>', 'Nigiri', 'nigiri');

INSERT INTO product (date_created, date_modified, description, name, price, sku, slug, stock_qty, tax_class, taxable, total_sales, category_id)
VALUES
(localtimestamp, localtimestamp, '<p>This is a product description. It should not allow HTML.</p>', 'Avocado Nigiri', 1.50, 'NIG001', 'avocado-nigiri', 50, 'Standaard', true, 0, 1),
(localtimestamp, localtimestamp, '<p>This is a product description. It should not allow HTML.</p>', 'Zalm Nigiri', 1.60, 'NIG002', 'avocado-nigiri', 50, 'Standaard', true, 0, 1);