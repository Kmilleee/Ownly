package fr.eni.springboot.controller;

import fr.eni.springboot.bo.Category;
import fr.eni.springboot.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/categoryList")
    public String displayCategories(Model model) {
        model.addAttribute("categoryList", categoryService.findAll());
        return "admin/list-cat";
    }

    @GetMapping("/createCat")
    public String displayCatForm(Model model) {

        model.addAttribute("category", new Category());

        return "admin/create-cat";
    }

    @PostMapping("/createCat")
    public String validCatForm(@ModelAttribute("category") Category category) {

        categoryService.createCategory(category);

        return "redirect:/admin/createCat";
    }

    @DeleteMapping("/delete")
    public String deleteCategory(@RequestParam("id") long id) {
        categoryService.deleteCat(id);
        return "redirect:/admin/categoryList";
    }

    @PostMapping("/update")
    public String updateCategories(@ModelAttribute(name = "category") Category category) {
        categoryService.updateCat(category);
        return "redirect:/admin/categoryList";
    }
}
