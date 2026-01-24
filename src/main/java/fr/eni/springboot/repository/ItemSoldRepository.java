package fr.eni.springboot.repository;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.transaction.annotation.Transactional;

public interface ItemSoldRepository {
    @Transactional
    void createItemSold(ItemSold itemSold);
}
