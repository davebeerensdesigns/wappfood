package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: Run validations on category

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new EntityNotFoundException(Product.class, "id", categoryId.toString());
        }
        Category category = categoryRepository.getById(categoryId);
        return category;
    }

    public Integer addCategory(Category category){
        Category newCategory = categoryRepository.save(category);
        return newCategory.getId();
    }

    public void deleteCategory(Integer categoryId){
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(Integer categoryId, Category category) {
        if(!categoryRepository.existsById(categoryId)){
            throw new EntityNotFoundException(Product.class, "id", categoryId.toString());
        }

        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);

        existingCategory.setId(category.getId());
        existingCategory.setDateCreated(category.getDateCreated());
        existingCategory.setDateModified(category.getDateModified());
        existingCategory.setName(category.getName());
        existingCategory.setSlug(category.getSlug());
        existingCategory.setDescription(category.getDescription());

        categoryRepository.save(existingCategory);
    }
}
