package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
        String sql = "INSERT INTO ItemSold (articleName, description, auctionStartDate, auctionEndDate, startingPrice, priceSale, user_id, category_id) VALUES (:articleName, :description, :auctionStartDate, :auctionEndDate, :startingPrice, :priceSale, :user_id, :category_id)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("articleName", itemSold.getArticleName());
        params.addValue("description", itemSold.getDescription());
        params.addValue("auctionStartDate", itemSold.getAuctionStartDate());
        params.addValue("auctionEndDate", itemSold.getAuctionEndDate());
        params.addValue("startingPrice", itemSold.getStartingPrice());
        params.addValue("priceSale", itemSold.getPriceSale());
        params.addValue("user_id", itemSold.getSeller().getUser_id());
        params.addValue("category_id",itemSold.getCategory().getCategory_id());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        if (keyHolder.getKey() != null) {
            itemSold.setId(keyHolder.getKey().longValue());
        }

        if (itemSold.getWithdrawal() != null) {

            MapSqlParameterSource paramsWithdrawal = new MapSqlParameterSource();

            paramsWithdrawal.addValue("street",itemSold.getWithdrawal().getStreet());
            paramsWithdrawal.addValue("postalCode",itemSold.getWithdrawal().getPostalCode());
            paramsWithdrawal.addValue("city",itemSold.getWithdrawal().getCity());
            paramsWithdrawal.addValue("article_id",itemSold.getId());

            String sqlWithdrawal = "INSERT INTO WITHDRAWAL (street, postalCode, city, article_id) VALUES (:street, :postalCode, :city, :article_id)";

            namedParameterJdbcTemplate.update(sqlWithdrawal, paramsWithdrawal);

        }

    }

}
