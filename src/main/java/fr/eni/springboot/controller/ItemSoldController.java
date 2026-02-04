package fr.eni.springboot.controller;

import ch.qos.logback.core.util.StringUtil;
import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.Rarity;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.CategoryService;
import fr.eni.springboot.service.FileUploadService;
import fr.eni.springboot.service.ItemSoldService;
import fr.eni.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ventes")
public class ItemSoldController {

    private final ItemSoldService itemSoldService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ItemSoldController(ItemSoldService itemSoldService, CategoryService categoryService, UserService userService) {
        this.itemSoldService = itemSoldService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/createSale")
    public String displaySalesForm(Model model) {

        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("activePage", "sell");

        model.addAttribute("article", new ItemSold());

        model.addAttribute("categoryList", categoryList);

        model.addAttribute("rarities", Rarity.values());

        model.addAttribute("activePage", "sell");

        return "create-sale";
    }

    @PostMapping("/createSale")
    public String createSale(@RequestParam("imageFile") MultipartFile multipartFile, @ModelAttribute("article") ItemSold itemSold, BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "create-sale";
        }
        itemSoldService.createItemSold(itemSold, multipartFile, principal);

        return "redirect:/auctionAll";
    }

    @GetMapping("/edit-sale")
    public String displayEditSale(@RequestParam("id") long id, Model model) {
        ItemSold article = itemSoldService.readItemById(id);

        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("article", article);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("rarities", Rarity.values());
        model.addAttribute("activePage", "sell");

        return "create-sale";
    }

    @PostMapping("/edit-sale")
    public String editSale(@RequestParam(value = "imageFile", required = false) MultipartFile multipartFile,
                           @ModelAttribute("article") ItemSold itemSold,
                           BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "create-sale";
        }

        try {
            itemSoldService.updateItemSold(itemSold, multipartFile, principal);
            redirectAttributes.addFlashAttribute("success", "L'article a été mis à jour !");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ventes/edit-sale?id=" + itemSold.getId();
        }

        return "redirect:/profile";
    }

    @DeleteMapping("/delete-sale")
    public String deleteSale(@RequestParam("id") long id, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            itemSoldService.deleteItemSold(id, principal);
            redirectAttributes.addFlashAttribute("success", "L'article a été supprimé avec succès.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println("probleme de delete");
        }
        return "redirect:/profile";
    }

    @GetMapping("/ItemsAdmin")
    public String displayItemAdmin(Model model){
        model.addAttribute("itemsList", itemSoldService.readItemSold());
        return "/itemsAdmin";
    }
}
