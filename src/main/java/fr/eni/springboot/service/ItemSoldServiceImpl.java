package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.ItemSoldRepositorySql;
import org.springframework.stereotype.Service;

@Service
public class ItemSoldServiceImpl implements ItemSoldService {

    ItemSoldRepository itemSoldRepository;

    public ItemSoldServiceImpl(ItemSoldRepository itemSoldRepository) {
        this.itemSoldRepository = itemSoldRepository;
    }

    @Override
    public void createItemSold(ItemSold itemSold) {
        itemSoldRepository.createItemSold(itemSold);
    }
}
