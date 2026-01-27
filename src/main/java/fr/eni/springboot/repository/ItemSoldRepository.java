package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemSoldRepository {
    @Transactional
    void createItemSold(ItemSold itemSold);

    List<ItemSold> readItemSold();
}
