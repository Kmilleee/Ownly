package fr.eni.springboot.service;

import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.ItemSoldRepositorySql;
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
    private final UserService userService;

    public ItemSoldServiceImpl(ItemSoldRepository itemSoldRepository, UserService userService) {
        this.itemSoldRepository = itemSoldRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException {

        if (principal != null) {
            User seller = userService.readUserByUsername(principal.getName());
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
    public ItemSold readItemSoldById(long article_id) {
        return itemSoldRepository.readItemSoldById(article_id);
    }
}
