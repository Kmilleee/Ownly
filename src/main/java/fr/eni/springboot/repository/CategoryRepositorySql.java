package fr.eni.springboot.repository;


import fr.eni.springboot.bo.Category;
import fr.eni.springboot.repository.rowMapper.CategoryRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryRepositorySql implements CategoryRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategoryRepositorySql(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public void createCategory(Category category) {
        String sql = "INSERT INTO CATEGORY (name) VALUES (:name)";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(category);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        if (keyHolder.getKey() != null) {
            category.setIdCategory(keyHolder.getKey().longValue());
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT category_id, name FROM CATEGORY";

        return namedParameterJdbcTemplate.query(sql, new CategoryRowMapper());
    }
}
