package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository {

    void createCategory(Category category);

    List<Category> findAll();
}
