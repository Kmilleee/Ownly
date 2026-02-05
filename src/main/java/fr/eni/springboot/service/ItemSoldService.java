package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ItemSoldService {

    void createItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException;

    List<ItemSold> readItemSold();

    @Transactional
    void updateItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException;

    @Transactional
    void deleteItemSold(long id, Principal principal);

    ItemSold readItemById(long id);

    List<ItemSold> readItemsBySeller(long sellerId);

    List<ItemSold> findByRarity(Rarity rarity);

    List<ItemSold> readItemsByBetterSel();

    List<ItemSold> readItemBySearch(String query);

    List<ItemSold> readItemByCategory(String cat);

    List<ItemSold> findItemsWonByUser(long userId);

    List<ItemSold> findItemsInProgressByUser(long userId);
}
