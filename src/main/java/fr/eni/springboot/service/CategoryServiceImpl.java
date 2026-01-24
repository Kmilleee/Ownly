package fr.eni.springboot.service;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.createCategory(category);
    }

    @Override
    public List<Category> findAll() {
       return categoryRepository.findAll();
    }
}
