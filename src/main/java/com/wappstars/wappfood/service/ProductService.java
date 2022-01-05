package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.RecordNotFoundException;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import com.wappstars.wappfood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent()){
            return product.get();
        } else {
            // exception
            throw new RecordNotFoundException("No product found!");
        }
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    public void updateProduct(Long id, Product product){
        if(!productRepository.existsById(id)){
            // exception
            throw new RecordNotFoundException("No product found!");
        }

        Product existingProduct = productRepository.findById(id).orElse(null);

        existingProduct.setId(product.getId());
        existingProduct.setDateCreated(product.getDateCreated());
        existingProduct.setDateModified(product.getDateModified());
        existingProduct.setName(product.getName());
        existingProduct.setSlug(product.getSlug());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setSku(product.getSku());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setTotalSales(product.getTotalSales());
        existingProduct.setStockQty(product.getStockQty());
        existingProduct.setTaxable(product.isTaxable());
        existingProduct.setTaxClass(product.getTaxClass());

        productRepository.save(existingProduct);
    }

}
