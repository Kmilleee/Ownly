package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;

import java.util.List;

public interface ItemSoldService {
    
    void createItemSold(ItemSold itemSold);

    List<ItemSold> readItemSold();
}
