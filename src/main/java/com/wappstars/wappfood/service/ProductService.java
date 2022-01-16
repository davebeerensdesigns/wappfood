package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import com.wappstars.wappfood.repository.ProductRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.util.StringToSlugResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Product getProduct(Integer productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "product id", productId.toString()));
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

    public Integer addProduct(Product product) {
        Product newProduct = new Product();

        newProduct.setName(
                product.getName()
        );
        newProduct.setSlug(
                StringToSlugResolver.makeSlug(
                        HtmlToTextResolver.HtmlToText(
                                (product.getSlug() != null ) ? product.getSlug() : product.getName()
                        )
                )
        );
        newProduct.setDescription(
                HtmlToTextResolver.HtmlToText(
                        product.getDescription()
                )
        );
        newProduct.setSku(
                product.getSku()
        );
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
            newProduct.setCategory(
                    product.getCategory()
            );
        }

        productRepository.save(newProduct);
        return newProduct.getId();

    }

    public void deleteProduct(Integer productId){
        if(!productRepository.existsById(productId)){
            throw new EntityNotFoundException(Product.class, "product id", productId.toString());
        }
        productRepository.deleteById(productId);
    }

    public void updateProduct(Integer productId, Product product) {
        if(!productRepository.existsById(productId)){
            throw new EntityNotFoundException(Product.class, "product id", productId.toString());
        }

        Product existingProduct = productRepository.findById(productId).orElse(null);

        existingProduct.setName(
                product.getName()
        );
        existingProduct.setSlug(
                (product.getSlug() != null ) ? StringToSlugResolver.makeSlug(product.getSlug()) : existingProduct.getSlug()
        );
        existingProduct.setDescription(
                HtmlToTextResolver.HtmlToText(
                        product.getDescription()
                )
        );
        existingProduct.setSku(
                product.getSku()
        );
        existingProduct.setPrice(
                product.getPrice()
        );
        existingProduct.setTotalSales(
                product.getTotalSales()
        );
        existingProduct.setStockQty(
                product.getStockQty()
        );
        existingProduct.setTaxable(
                product.isTaxable()
        );
        existingProduct.setTaxClass(
                product.getTaxClass()
        );
        existingProduct.setCategory(
                product.getCategory()
        );

        productRepository.save(existingProduct);
    }

}
