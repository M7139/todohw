package com.todo.todohw.controller;

import com.todo.todohw.model.Category;
import com.todo.todohw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //get all
    @GetMapping("/")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    //get by id
    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }
    //post
    @PostMapping("/")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    //update
    @PutMapping("/{categoryId}")
    public Category updateCategory(
            @PathVariable Long categoryId,
            @RequestBody Category categoryObject) {

        return categoryService.updateCategory(categoryId, categoryObject);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}