package fr.eni.springboot.repository;


import fr.eni.springboot.bo.Category;
import fr.eni.springboot.repository.rowMapper.CategoryRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
            category.setCategory_id(keyHolder.getKey().longValue());
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT category_id, name FROM CATEGORY";

        return namedParameterJdbcTemplate.query(sql, new CategoryRowMapper());
    }

    @Override
    public Category readCatById(long category_id) {

        String sql = "SELECT * FROM CATEGORY WHERE category_id=:category_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", category_id);

        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(Category.class));
    }


    @Override
    public void updateCat(Category category){

        String sql =" update CATEGORY set name=:name where category_id=:category_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(category);

        namedParameterJdbcTemplate.update(sql, map);


    }

    @Override
    public void deleteCat(long category_id) {

        String sql = "DELETE FROM CATEGORY WHERE category_id=:category_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("category_id", category_id);

        namedParameterJdbcTemplate.update(sql, map);

    }


}
