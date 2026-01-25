package fr.eni.springboot.controller;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.service.CategoryService;
import fr.eni.springboot.service.ItemSoldService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ventes")
public class ItemSoldController {

    private final ItemSoldService itemSoldService;
    private final CategoryService categoryService;

    public ItemSoldController(ItemSoldService itemSoldService, CategoryService categoryService) {
        this.itemSoldService = itemSoldService;
        this.categoryService = categoryService;
    }

    @GetMapping("/createSale")
    public String displaySalesForm(Model model) {

        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("article", new ItemSold());

        model.addAttribute("categoryList", categoryList);

        return "create-sale";
    }

    @PostMapping("/createSale")
    public String validSalesForm(@ModelAttribute("article") ItemSold itemSold) {

        itemSoldService.createItemSold(itemSold);

        return "redirect:createSale";
    }
}
