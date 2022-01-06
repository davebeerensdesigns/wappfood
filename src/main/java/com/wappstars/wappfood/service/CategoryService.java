package com.wappstars.wappfood.service;

import com.wappstars.wappfood.exception.RecordNotFoundException;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Category getCategory(Integer categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isPresent()){
            return category.get();
        } else {
            // exception
            throw new RecordNotFoundException("No category found!");
        }
    }

    public Integer addCategory(Category category){
        Category newCategory = categoryRepository.save(category);
        return newCategory.getId();
    }

    public void deleteCategory(Integer categoryId){
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(Integer id, Category category){
        if(!categoryRepository.existsById(id)){
            // exception
            throw new RecordNotFoundException("No category found!");
        }

        Category existingCategory = categoryRepository.findById(id).orElse(null);

        existingCategory.setId(category.getId());
        existingCategory.setDateCreated(category.getDateCreated());
        existingCategory.setDateModified(category.getDateModified());
        existingCategory.setName(category.getName());
        existingCategory.setSlug(category.getSlug());
        existingCategory.setDescription(category.getDescription());

        categoryRepository.save(existingCategory);
    }
}
