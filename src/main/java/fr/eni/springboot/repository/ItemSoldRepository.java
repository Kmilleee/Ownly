package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemSoldRepository {
    @Transactional
    void createItemSold(ItemSold itemSold);

    List<ItemSold> readItemSold();

    void updateItemSold(ItemSold itemSold);
    void deleteItemSold(long id);
    ItemSold readItemById(long id);

    List<ItemSold> readItemsBySeller(long sellerId);

    ItemSold readItemSoldById(long article_id);

    List<ItemSold> findByRarity(Rarity rarity);

    List<ItemSold> readItemsByBetterSel();

    List<ItemSold> readItemBySearch(String query);
}
