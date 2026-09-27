package com.todo.todohw.controller;

import com.todo.todohw.model.Category;
import com.todo.todohw.model.Item;
import com.todo.todohw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("calling getCategories ==>");
        return categoryService.getCategories();
    }

    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        System.out.println("calling getCategory ==>");
        return categoryService.getCategory(categoryId);
    }

    @PostMapping("/categories/")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("calling createCategory ==>");
        return categoryService.createCategory(categoryObject);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(
            @PathVariable(value = "categoryId") Long categoryId,
            @RequestBody Category categoryObject) {

        System.out.println("calling updateCategory ==>");
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategory(
            @PathVariable(value = "categoryId") Long categoryId) {

        System.out.println("calling deleteCategory ==>");
        return categoryService.deleteCategory(categoryId);
    }

    @PostMapping("/categories/{categoryId}/items")
    public Item createCategoryItem(
            @PathVariable(value = "categoryId") Long categoryId,
            @RequestBody Item itemObject) {

        System.out.println("calling createCategoryItem ==>");
        return categoryService.createCategoryItem(categoryId, itemObject);
    }

    @GetMapping("/categories/{categoryId}/items")
    public List<Item> getCategoryItems(
            @PathVariable(value = "categoryId") Long categoryId) {

        System.out.println("calling getCategoryItems ==>");
        return categoryService.getCategoryItems(categoryId);
    }

    @GetMapping("/categories/{categoryId}/items/{itemId}")
    public Item getCategoryItem(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "itemId") Long itemId) {

        System.out.println("calling getCategoryItem ==>");
        return categoryService.getCategoryItem(categoryId, itemId);
    }

    @PutMapping("/categories/{categoryId}/items/{itemId}")
    public Item updateCategoryItem(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "itemId") Long itemId,
            @RequestBody Item itemObject) {

        System.out.println("calling getCategoryItem ==>");
        return categoryService.updateCategoryItem(
                categoryId,
                itemId,
                itemObject
        );
    }

    @DeleteMapping("/categories/{categoryId}/items/{itemId}")
    public ResponseEntity<HashMap<String, String>> deleteCategoryItem(
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "itemId") Long itemId) {

        System.out.println("calling getCategoryItem ==>");

        categoryService.deleteCategoryItem(categoryId, itemId);

        HashMap<String, String> responseMessage = new HashMap<>();

        responseMessage.put(
                "status",
                "item with id: " + itemId + " was successfully deleted."
        );

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}