package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityExistsException;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import com.wappstars.wappfood.repository.ProductRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.util.StringToSlugResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//TODO: Run validations on product

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getProducts(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new EntityNotFoundException(Product.class);
        }
        return products;
    }

    public Optional<Product> getProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            throw new EntityNotFoundException(Product.class, "product id", productId.toString());
        }
        return product;
    }

    public List<Product> getProductsByCategoryId(Integer categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new EntityNotFoundException(Category.class, "category id", categoryId.toString());
        }
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        if(products.isEmpty()){
            throw new EntityNotFoundException(Product.class, "category id", categoryId.toString());
        }
        return products;
    }

    public Product addProduct(Product product) {
        Product newProduct = new Product();

        newProduct.setName(
                HtmlToTextResolver.HtmlToText(product.getName())
        );

        String slug = (product.getSlug() == null ) ? product.getName() : product.getSlug();
        slug = HtmlToTextResolver.HtmlToText(slug);
        slug = StringToSlugResolver.makeSlug(slug);
        if(productRepository.existsBySlug(slug)){
            throw new EntityExistsException(Product.class, "slug", slug);
        } else {
            newProduct.setSlug(
                    slug
            );
        }
        newProduct.setDescription(
                HtmlToTextResolver.HtmlToText(
                        product.getDescription()
                )
        );
        if(productRepository.existsBySku(product.getSku())){
            throw new EntityExistsException(Product.class, "sku", product.getSku());
        } else {
            newProduct.setSku(
                    product.getSku()
            );
        }
        newProduct.setPrice(
                product.getPrice()
        );
        newProduct.setTotalSales(
                product.getTotalSales()
        );
        newProduct.setStockQty(
                product.getStockQty()
        );
        newProduct.setTaxable(
                product.isTaxable()
        );
        newProduct.setTaxClass(
                product.getTaxClass()
        );

        Integer prodCatId = product.getCategory().getId();
        if(!categoryRepository.existsById(prodCatId)){
            throw new EntityNotFoundException(Category.class, "category id", prodCatId.toString());
        } else {
            Category category = categoryRepository.getById(prodCatId);
            newProduct.setCategory(
                    category
            );
        }

        return productRepository.save(newProduct);

    }

    public void deleteProduct(Integer productId){
        if(!productRepository.existsById(productId)){
            throw new EntityNotFoundException(Product.class, "product id", productId.toString());
        }
        productRepository.deleteById(productId);
    }

    public Product updateProduct(Integer productId, Product product) {

        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(Product.class, "product id", productId.toString()));

        if(product.getName() != null)
            existingProduct.setName(
                HtmlToTextResolver.HtmlToText(product.getName())
            );

        if(product.getSlug() != null) {
            String slug = HtmlToTextResolver.HtmlToText(product.getSlug());
            slug = StringToSlugResolver.makeSlug(slug);
            if (!slug.equals(existingProduct.getSlug())) {
                if (productRepository.existsBySlug(slug)) {
                    throw new EntityExistsException(Product.class, "slug", slug);
                } else {
                    existingProduct.setSlug(
                            slug
                    );
                }
            }
        }

        if(product.getDescription() != null)
        existingProduct.setDescription(
                HtmlToTextResolver.HtmlToText(
                        product.getDescription()
                )
        );

        if(product.getSku() != null)
        if(productRepository.existsBySku(product.getSku())){
            throw new EntityExistsException(Product.class, "sku", product.getSku());
        } else {
            existingProduct.setSku(
                    product.getSku()
            );
        }

        if(product.getSku() != null)
        existingProduct.setPrice(
                product.getPrice()
        );

        if(product.getTotalSales() != null)
        existingProduct.setTotalSales(
                product.getTotalSales()
        );

        if(product.getStockQty() != null)
        existingProduct.setStockQty(
                product.getStockQty()
        );

        if(product.isTaxable() == true) {
            existingProduct.setTaxable(true);
        } else if(product.isTaxable() == true){
            existingProduct.setTaxable(false);
        }

        if(product.getTaxClass() != null)
        existingProduct.setTaxClass(
                product.getTaxClass()
        );

        if(product.getCategory().getId() != null) {
            Integer prodCatId = product.getCategory().getId();
            if (!categoryRepository.existsById(prodCatId)) {
                throw new EntityNotFoundException(Category.class, "category id", prodCatId.toString());
            } else {
                Category category = categoryRepository.getById(prodCatId);
                existingProduct.setCategory(
                        category
                );
            }
        }

        return productRepository.save(existingProduct);
    }

}
