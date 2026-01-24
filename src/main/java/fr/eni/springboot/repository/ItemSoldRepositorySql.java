package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemSoldRepositorySql implements ItemSoldRepository{

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemSoldRepositorySql(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Transactional
    @Override
    public void createItemSold(ItemSold itemSold) {
        String sql = "INSERT INTO ItemSold (articleName, description, auctionStartDate, auctionEndDate, startingPrice, priceSale, user_id, category_id) VALUES (:articleName, :description, :auctionStartDate, :auctionEndDate, :startingPrice, :priceSale, :seller.user_id, :category.idCategory)";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(itemSold);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        if (keyHolder.getKey() != null) {
            itemSold.setId(keyHolder.getKey().longValue());
        }

        if (itemSold.getWithdrawal() != null) {

            String sqlWithdrawal = "INSERT INTO WITHDRAWAL (street, postalCode, city, article_id) VALUES (:withdrawal.street, :withdrawal.postalCode, :withdrawal.city, :id)";

            namedParameterJdbcTemplate.update(sqlWithdrawal, new BeanPropertySqlParameterSource(itemSold));

        }

    }

}
