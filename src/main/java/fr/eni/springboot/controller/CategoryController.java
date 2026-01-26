package fr.eni.springboot.controller;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/createCat")
    public String displayCatForm(Model model) {

        model.addAttribute("category", new Category());

        return "create-cat";
    }

    @PostMapping("/createCat")
    public String validCatForm(@ModelAttribute("category") Category category) {

        categoryService.createCategory(category);

        return "redirect:createCat";
    }
}
