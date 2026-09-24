package com.todo.todohw.service;

import com.todo.todohw.model.Category;
import com.todo.todohw.model.Item;
import com.todo.todohw.repository.CategoryRepository;
import com.todo.todohw.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

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

    public List<Item> getItems(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    public Item createItem(Long categoryId, Item item) {

        Category category =
                categoryRepository.findById(categoryId).orElse(null);

        if (category != null) {
            item.setCategory(category);
            return itemRepository.save(item);
        }

        return null;
    }

    public Item getItem(Long categoryId, Long itemId) {

        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null &&
                item.getCategory().getId().equals(categoryId)) {
            return item;
        }

        return null;
    }

    public Item updateItem(
            Long categoryId,
            Long itemId,
            Item itemObject) {

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

    public void deleteItem(Long categoryId, Long itemId) {

        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null &&
                item.getCategory().getId().equals(categoryId)) {

            itemRepository.deleteById(itemId);
        }
    }
}