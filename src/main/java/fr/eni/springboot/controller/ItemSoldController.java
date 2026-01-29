package fr.eni.springboot.controller;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.bo.ItemSold;
import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.CategoryService;
import fr.eni.springboot.service.ItemSoldService;
import fr.eni.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    //Objet Principal EN GROS ça permet d'accéder à l'identité du user connecté
    public String validSalesForm(Principal principal, @ModelAttribute("article") ItemSold itemSold) {

        /* principal.getName retourne l'identité du user connecté (en l'occurence le username car la valeur retournée
         correspond à la première colonne sélectionnée dans la requête SQL dans SecurityConfiguration (voir commentaire)*/
        User seller = userService.readUserByUsername(principal.getName());

        //Affecte le User récupéré en tant que vendeur
        itemSold.setSeller(seller);

        itemSoldService.createItemSold(itemSold);

        return "redirect:createSale";
    }
}
