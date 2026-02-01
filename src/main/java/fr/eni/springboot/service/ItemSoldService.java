package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ItemSoldService {

    void createItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException;
    List<ItemSold> readItemSold();
}
