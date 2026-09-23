package com.todo.todohw.controller;

import com.todo.todohw.model.Category;
import com.todo.todohw.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //get all
    @GetMapping("/")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    //get by id
    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    //post
    @PostMapping("/")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    //update
    @PutMapping("/{categoryId}")
    public Category updateCategory(
            @PathVariable Long categoryId,
            @RequestBody Category categoryObject) {

        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category != null) {
            category.setName(categoryObject.getName());
            category.setDescription(categoryObject.getDescription());

            return categoryRepository.save(category);
        }

        return null;
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}