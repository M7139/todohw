package com.todo.todohw.repository;

import com.todo.todohw.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);

    Category findByIdAndUserId(Long categoryId, Long userId);

    List<Category> findByUserId(Long userId);

    Category findByUserIdAndName(Long UserId, String categoryName);
}