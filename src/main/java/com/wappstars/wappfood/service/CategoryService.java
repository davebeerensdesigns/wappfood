package com.wappstars.wappfood.service;

import com.wappstars.wappfood.dto.CategoryInputDto;
import com.wappstars.wappfood.exception.EntityExistsException;
import com.wappstars.wappfood.exception.EntityNotFoundException;
import com.wappstars.wappfood.model.Category;
import com.wappstars.wappfood.model.Product;
import com.wappstars.wappfood.repository.CategoryRepository;
import com.wappstars.wappfood.util.HtmlToTextResolver;
import com.wappstars.wappfood.util.StringToSlugResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO: Run validations on category

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new EntityNotFoundException(Category.class, "GET", "*");
        }
        return categories;
    }

    public Optional<Category> getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new EntityNotFoundException(Category.class, "category id", categoryId.toString());
        }
        return category;
    }

    public Category addCategory(CategoryInputDto category){
        Category newCategory = new Category();

        newCategory.setName(
                HtmlToTextResolver.HtmlToText(category.getName())
        );

        String slug = (category.getSlug() == null ) ? category.getName() : category.getSlug();
        slug = HtmlToTextResolver.HtmlToText(slug);
        slug = StringToSlugResolver.makeSlug(slug);
        if(categoryRepository.existsBySlug(slug)){
            throw new EntityExistsException(Category.class, "slug", slug);
        } else {
            newCategory.setSlug(
                    slug
            );
        }

        newCategory.setDescription(
                HtmlToTextResolver.HtmlToText(
                        category.getDescription()
                )
        );

        newCategory.setImage(
                category.getImage()
        );

        return categoryRepository.save(newCategory);
    }

    public void deleteCategory(Integer categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new EntityNotFoundException(Category.class, "category id", categoryId.toString());
        }
        categoryRepository.deleteById(categoryId);
    }

    public Category updateCategory(Integer categoryId, CategoryInputDto category) {

        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(Category.class, "category id", categoryId.toString()));

        if(category.getName() != null) {
            existingCategory.setName(
                    HtmlToTextResolver.HtmlToText(category.getName())
            );
        }

        if(category.getSlug() != null) {
            String slug = HtmlToTextResolver.HtmlToText(category.getSlug());
            slug = StringToSlugResolver.makeSlug(slug);
            if (!slug.equals(existingCategory.getSlug())) {
                if (categoryRepository.existsBySlug(slug)) {
                    throw new EntityExistsException(Category.class, "slug", slug);
                } else {
                    existingCategory.setSlug(
                            slug
                    );
                }
            }
        }

        if(category.getDescription() != null) {
            existingCategory.setDescription(
                    HtmlToTextResolver.HtmlToText(
                            category.getDescription()
                    )
            );
        }

        if(category.getImage() != null)
        existingCategory.setImage(
                category.getImage()
        );

        return categoryRepository.save(existingCategory);
    }
}
