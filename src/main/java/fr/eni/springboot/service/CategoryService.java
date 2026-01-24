package fr.eni.springboot.service;

import fr.eni.springboot.bo.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);

    List<Category> findAll();
}
