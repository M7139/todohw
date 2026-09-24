package com.todo.todohw.controller;

import com.todo.todohw.model.Item;
import com.todo.todohw.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    //get all
    @GetMapping("/{categoryId}/items")
    public List<Item> getItems(@PathVariable Long categoryId) {
        return itemService.getItems(categoryId);
    }

    //creat an item
    @PostMapping("/{categoryId}/items")
    public Item createItem(
            @PathVariable Long categoryId,
            @RequestBody Item item) {

        return itemService.createItem(categoryId, item);
    }

    //get by id
    @GetMapping("/{categoryId}/items/{itemId}")
    public Item getItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId) {

        return itemService.getItem(categoryId, itemId);
    }

    //update item
    @PutMapping("/{categoryId}/items/{itemId}")
    public Item updateItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId,
            @RequestBody Item itemObject) {

        return itemService.updateItem(categoryId, itemId, itemObject);
    }

    //delete
    @DeleteMapping("/{categoryId}/items/{itemId}")
    public void deleteItem(
            @PathVariable Long categoryId,
            @PathVariable Long itemId) {

        itemService.deleteItem(categoryId, itemId);
    }
}