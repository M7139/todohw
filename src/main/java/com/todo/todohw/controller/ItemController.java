package com.todo.todohw.controller;

import com.todo.todohw.model.Category;
import com.todo.todohw.model.Item;
import com.todo.todohw.repository.CategoryRepository;
import com.todo.todohw.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ItemController {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //get all
    @GetMapping("/{categoryId}/items")
    public List<Item> getItems(@PathVariable Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    //creat an item
    @PostMapping("/{categoryId}/items")
    public Item createItem(
            @PathVariable Long categoryId,
            @RequestBody Item item) {

        Category category =
                categoryRepository.findById(categoryId).orElse(null);

        if (category != null) {
            item.setCategory(category);
            return itemRepository.save(item);
        }

        return null;
    }

    //get by id
    @GetMapping("/{categoryId}/items/{itemId}")
    public Item getItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId) {

        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null &&
                item.getCategory().getId().equals(categoryId)) {

            return item;
        }

        return null;
    }

    //update item
    @PutMapping("/{categoryId}/items/{itemId}")
    public Item updateItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId,
            @RequestBody Item itemObject) {

        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null &&
                item.getCategory().getId().equals(categoryId)) {

            item.setName(itemObject.getName());
            item.setDescription(itemObject.getDescription());
            item.setDueDate(itemObject.getDueDate());

            return itemRepository.save(item);
        }

        return null;
    }

    //delete
    @DeleteMapping("/{categoryId}/items/{itemId}")
    public void deleteItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId) {

        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null &&
                item.getCategory().getId().equals(categoryId)) {

            itemRepository.deleteById(itemId);
        }
    }
}