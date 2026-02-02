package fr.eni.springboot.controller;

import ch.qos.logback.core.util.StringUtil;
import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.ItemSold;
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

        model.addAttribute("activePage", "sell");

        return "create-sale";
    }

    @PostMapping("/createSale")
    public String createSale(@RequestParam("imageFile") MultipartFile multipartFile, @ModelAttribute("article") ItemSold itemSold, BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "create-sale";
        }
        itemSoldService.createItemSold(itemSold, multipartFile, principal);

        return "redirect:/ventes/createSale";
    }

    @GetMapping("/edit-sale")
    public String displayEditSale(@RequestParam("id") long id, Model model) {
        ItemSold article = itemSoldService.readItemById(id);

        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("article", article);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("activePage", "sell");

        return "create-sale";
    }

    @PostMapping("/edit-sale")
    public String editSale(@RequestParam("imageFile") MultipartFile multipartFile,
                           @ModelAttribute("article") ItemSold itemSold,
                           BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "create-sale";
        }

        itemSoldService.updateItemSold(itemSold, multipartFile);

        return "redirect:/profile";
    }
}
