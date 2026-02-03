package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class ItemSoldServiceImpl implements ItemSoldService {

    ItemSoldRepository itemSoldRepository;
    UserRepository userRepository;

    public ItemSoldServiceImpl(ItemSoldRepository itemSoldRepository, UserRepository userRepository) {
        this.itemSoldRepository = itemSoldRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException {

        if (principal != null) {
            User seller = userRepository.readUserByUsername(principal.getName());
            if (seller != null) {
                itemSold.setSeller(seller);
            }
        }

        if (file != null && !file.isEmpty()) {
            // Sécurité pour récupérer seulement le nom de l'image (évite de pouvoir placer son image à l'endroit où le user veut)
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            //Ajout du nom de l'image à l'item à vendre
            itemSold.setImage(fileName);
        }

        itemSoldRepository.createItemSold(itemSold);

        if (file != null && !file.isEmpty()) {
            //Création du dossier de l'image avec l'id de l'annonce
            String uploadDir = "itemsSold-photos/" + itemSold.getId();
            //Ajout de l'image dans le dossier
            FileUploadService.uploadFile(uploadDir, itemSold.getImage(), file);
        }
    }

    @Override
    public List<ItemSold> readItemSold() {
        return itemSoldRepository.readItemSold();
    }

    @Override
    @Transactional
    public void updateItemSold(ItemSold itemSold, MultipartFile file) throws IOException {
        ItemSold articleActuel = itemSoldRepository.readItemById(itemSold.getId());

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            itemSold.setImage(fileName);

            String uploadDir = "itemsSold-photos/" + itemSold.getId();
            FileUploadService.uploadFile(uploadDir, fileName, file);
        } else {
            itemSold.setImage(articleActuel.getImage());
        }

        itemSoldRepository.updateItemSold(itemSold);
    }

    @Override
    public void deleteItemSold(long id) {
        itemSoldRepository.deleteItemSold(id);
    }

    @Override
    public ItemSold readItemById(long id) {
        return itemSoldRepository.readItemById(id);
    }

    @Override
    public List<ItemSold> readItemsBySeller(long sellerId) {
        return itemSoldRepository.readItemsBySeller(sellerId);
    }

    @Override
    public List<ItemSold> findByRarity(Rarity rarity) {
        return itemSoldRepository.findByRarity(rarity);
    }

    @Override
    public List<ItemSold> readItemsByBetterSel() {
        return itemSoldRepository.readItemsByBetterSel();
    }

    @Override
    public List<ItemSold> readItemBySearch(String query) {
        return itemSoldRepository.readItemBySearch(query);
    }

    @Override
    public List<ItemSold> readItemByCategory(String cat) {
        return itemSoldRepository.readItemByCategory(cat);
    }
}
