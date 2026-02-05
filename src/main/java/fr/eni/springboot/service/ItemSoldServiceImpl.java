package fr.eni.springboot.service;

import fr.eni.springboot.bo.*;
import fr.eni.springboot.repository.ItemSoldRepository;
import fr.eni.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
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
        User seller = null;
        if (principal != null) {
            seller = userRepository.readUserByUsername(principal.getName());
            if (seller != null) {
                itemSold.setSeller(seller);
            }
        }

        if (seller != null && (itemSold.getWithdrawal() == null ||
                itemSold.getWithdrawal().getStreet() == null || itemSold.getWithdrawal().getStreet().isBlank() ||
                itemSold.getWithdrawal().getCity() == null || itemSold.getWithdrawal().getCity().isBlank())) {

            Withdrawal adresseDefaut = new Withdrawal();
            adresseDefaut.setStreet(seller.getStreet());
            adresseDefaut.setPostalCode(seller.getPostalCode());
            adresseDefaut.setCity(seller.getCity());

            itemSold.setWithdrawal(adresseDefaut);
        }

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            itemSold.setImage(fileName);
        }

        itemSoldRepository.createItemSold(itemSold);

        if (file != null && !file.isEmpty()) {
            String uploadDir = "itemsSold-photos/" + itemSold.getId();
            FileUploadService.uploadFile(uploadDir, itemSold.getImage(), file);
        }
    }

    @Override
    public List<ItemSold> readItemSold() {
        return itemSoldRepository.readItemSold();
    }

    @Transactional
    @Override
    public void updateItemSold(ItemSold itemSold, MultipartFile file, Principal principal) throws IOException {

        if (principal == null) {
            throw new RuntimeException("Vous devez être connecté pour modifier une vente");
        }

        ItemSold articleActuel = itemSoldRepository.readItemById(itemSold.getId());

        if (articleActuel.getStatus() != StatusSale.NOT_STARTED) {
            throw new RuntimeException("Impossible de modifier la vente car elle a déjà commencé");
        }

        long actualUserId = userRepository.readUserByUsername(principal.getName()).getUser_id();
        long ownerId = articleActuel.getSeller().getUser_id();

        if (actualUserId != ownerId) {
            throw new RuntimeException("Impossible de modifier la vente : elle ne vous appartient pas");
        }

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

    @Transactional
    @Override
    public void deleteItemSold(long id, Principal principal) {
        ItemSold articleActuel = itemSoldRepository.readItemById(id);

        if (principal == null) {
            throw new RuntimeException("Vous devez être connecté pour supprimer une vente");
        }

        long actualUserId = userRepository.readUserByUsername(principal.getName()).getUser_id();
        long ownerId = articleActuel.getSeller().getUser_id();

        if (actualUserId != ownerId) {
            throw new RuntimeException("Impossible de supprimer la vente : elle ne vous appartient pas");
        }

        if (!LocalDateTime.now().isBefore(articleActuel.getAuctionStartDate())) {
            throw new RuntimeException("Impossible d'annuler la vente car elle a déjà commencé");
        }
        String folderPath = "itemsSold-photos/" + id;
        try {
            deleteDirectory(Paths.get(folderPath));
        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression du dossier image : " + e.getMessage());
        }

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

    @Override
    public List<ItemSold> findItemsWonByUser(long userId) {
        return itemSoldRepository.findItemsWonByUser(userId);
    }

    @Override
    public List<ItemSold> findItemsInProgressByUser(long userId) {
        return itemSoldRepository.findItemsInProgressByUser(userId);
    }

    private void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
