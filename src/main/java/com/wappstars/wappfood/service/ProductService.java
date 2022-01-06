package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.BadRequestException;
import com.wappstars.wappfood.exception.RecordNotFoundException;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import com.wappstars.wappfood.repository.ProductRepository;
import com.wappstars.wappfood.util.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer productId){
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent()){
            return product.get();
        } else {
            // exception
            throw new RecordNotFoundException("No product found!");
        }
    }

    public List<Product> getProductsByCategoryId(Integer id){
        // TODO: Create Not Found exception for no products in category
        return productRepository.findAllByCategoryId(id);
    }

    public Integer addProduct(Product product){
        Product newProduct = new Product();
        if(product.getName() == null){
            // exception
            throw new BadRequestException("Product name is required");
        } else {
            newProduct.setName(product.getName());
        }
        if(product.getSlug() == null){
            newProduct.setSlug(Slug.makeSlug(product.getName()));
        } else {
            newProduct.setSlug(product.getSlug());
        }
        if(product.getDescription() == null) {
            newProduct.setDescription("");
        } else {
            newProduct.setDescription(product.getDescription());
        }
        if(product.getSku() == null){
            newProduct.setSku("");
        } else{
            newProduct.setSku(product.getSku());
        }
        if(product.getPrice() == null){
            newProduct.setPrice(0.0);
        } else {
            newProduct.setPrice(product.getPrice());
        }
        if(product.getTotalSales() == null){
            newProduct.setTotalSales(0);
        } else {
            newProduct.setTotalSales(product.getTotalSales());
        }
        if(product.getStockQty() == null){
            newProduct.setStockQty(0);
        } else {
            newProduct.setStockQty(product.getStockQty());
        }
        newProduct.setTaxable(product.isTaxable());

        if(product.getTaxClass() == null){
            newProduct.setTaxClass("Standaard");
        } else {
            newProduct.setTaxClass(product.getTaxClass());
        }
        if(product.getCategory() == null){
            // exception
            throw new BadRequestException("Product category is required");
        } else {
            newProduct.setCategory(product.getCategory());
        }
        productRepository.save(newProduct);
        return newProduct.getId();
    }

    public void deleteProduct(Integer productId){
        productRepository.deleteById(productId);
    }

    public void updateProduct(Integer id, Product product){
        if(!productRepository.existsById(id)){
            // exception
            throw new RecordNotFoundException("No product found!");
        }

        Product existingProduct = productRepository.findById(id).orElse(null);

        // TODO: only update if has value
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
        existingProduct.setCategory(product.getCategory());

        productRepository.save(existingProduct);
    }

}
