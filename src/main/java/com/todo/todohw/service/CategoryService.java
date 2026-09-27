package com.todo.todohw.service;

import com.todo.todohw.exception.InformationExistException;
import com.todo.todohw.exception.InformationNotFoundException;
import com.todo.todohw.model.Category;
import com.todo.todohw.model.Item;
import com.todo.todohw.model.User;
import com.todo.todohw.repository.CategoryRepository;
import com.todo.todohw.repository.ItemRepository;
import com.todo.todohw.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    public List<Category> getCategories() {
        List<Category> category = categoryRepository.findByUserId(CategoryService.getCurrentLoggedInUser().getId());
        if (category.isEmpty()) {
            throw new InformationNotFoundException("no categories found for user id " + CategoryService.getCurrentLoggedInUser().getId());
        } else {
            return category;
        }
    }

    public Category getCategory(Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            return category;
        }
    }

    public Category createCategory(Category categoryObject) {
        Category category = categoryRepository.findByUserIdAndName(
                CategoryService.getCurrentLoggedInUser().getId(), categoryObject.getName());

        if (category != null) {
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        } else {
            categoryObject.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }
    }

    public Category updateCategory(Long categoryId, Category categoryObject) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());

        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            category.setDescription(categoryObject.getDescription());
            category.setName(categoryObject.getName());
            category.setUser(CategoryService.getCurrentLoggedInUser());

            return categoryRepository.save(category);
        }
    }

    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());

        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        } else {
            categoryRepository.deleteById(categoryId);

            return "category with id " + categoryId + " has been successfully deleted";
        }
    }

    public Item createCategoryItem(Long categoryId, Item itemObject) {
        Category category = categoryRepository.findByIdAndUserId(
                categoryId,
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId + " not belongs to this user or category does not exist"
            );
        }

        Item item = itemRepository.findByNameAndUserId(
                itemObject.getName(),
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (item != null) {
            throw new InformationExistException(
                    "item with name " + item.getName() + " already exists"
            );
        }

        itemObject.setUser(CategoryService.getCurrentLoggedInUser());
        itemObject.setCategory(category);

        return itemRepository.save(itemObject);
    }

    public List<Item> getCategoryItems(Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(
                categoryId,
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId + " " +
                            "not belongs to this user or category does not exist"
            );
        }

        return category.getItemList();
    }

    public Item getCategoryItem(Long categoryId, Long itemId) {
        Category category = categoryRepository.findByIdAndUserId(
                categoryId,
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId +
                            " not belongs to this user or category does not exist"
            );
        }

        Optional<Item> item = itemRepository.findByCategoryId(categoryId)
                .stream()
                .filter(p -> p.getId().equals(itemId))
                .findFirst();

        if (item.isEmpty()) {
            throw new InformationNotFoundException(
                    "item with id " + itemId +
                            " not belongs to this user or item does not exist"
            );
        }

        return item.get();
    }

    public Item updateCategoryItem(Long categoryId, Long itemId, Item itemObject) {
        Category category = categoryRepository.findByIdAndUserId(
                categoryId,
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId +
                            " not belongs to this user or category does not exist"
            );
        }

        Optional<Item> item = itemRepository.findByCategoryId(categoryId)
                .stream()
                .filter(p -> p.getId().equals(itemId))
                .findFirst();

        if (item.isEmpty()) {
            throw new InformationNotFoundException(
                    "item with id " + itemId +
                            " not belongs to this user or item does not exist"
            );
        }

        Item oldItem = itemRepository.findByNameAndUserIdAndIdIsNot(
                itemObject.getName(),
                CategoryService.getCurrentLoggedInUser().getId(),
                itemId
        );

        if (oldItem != null) {
            throw new InformationExistException(
                    "item with name " + oldItem.getName() + " already exists"
            );
        }

        item.get().setName(itemObject.getName());
        item.get().setDescription(itemObject.getDescription());
        item.get().setDueDate(itemObject.getDueDate());

        return itemRepository.save(item.get());
    }

    public void deleteCategoryItem(Long categoryId, Long itemId) {
        Category category = categoryRepository.findByIdAndUserId(
                categoryId,
                CategoryService.getCurrentLoggedInUser().getId()
        );

        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId +
                            " not belongs to this user or category does not exist"
            );
        }

        Optional<Item> item = itemRepository.findByCategoryId(categoryId)
                .stream()
                .filter(p -> p.getId().equals(itemId))
                .findFirst();

        if (item.isEmpty()) {
            throw new InformationNotFoundException(
                    "item with id " + itemId +
                            " not belongs to this user or item does not exist"
            );
        }

        itemRepository.deleteById(item.get().getId());
    }
}