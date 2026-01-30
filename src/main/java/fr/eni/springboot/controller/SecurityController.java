package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        return "security/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email) {
        // 1. Vérifier si l'email existe en base
        // 2. Si oui, générer un token et envoyer l'email

        System.out.println("Email reçu : " + email); // Pour tester

        // Redirection avec un paramètre success pour afficher le message vert
        return "redirect:/forgotPassword?success";
    }

}
