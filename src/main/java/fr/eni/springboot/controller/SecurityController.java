package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SecurityController {

    UserService userService;
    private final JavaMailSender javaMailSender;

    public static Map<String, String> tokenStore = new HashMap<>();

    public SecurityController(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        return "security/forgotPassword";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token, Model model) {

        String email = tokenStore.get(token);

        if (email == null) {
            return "redirect:/login?error=invalid_token";
        }
        model.addAttribute("token", token);
        return "security/resetPassword";
    }
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String newPassword) {


        String email = tokenStore.get(token);

        if (email != null) {

            userService.updatePassword(email, newPassword);
            tokenStore.remove(token);

            return "redirect:/login?resetSuccess";
        }

        return "redirect:/login?error";
    }


    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email) {

        System.out.println("Email reçu : " + email);


        User user = userService.findByEmail(email);
        if (user == null) {
            return "redirect:/forgotPassword?error";
        }

        String token = UUID.randomUUID().toString();
        tokenStore.put(token, email);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;


        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("antonin.mrtl.pr@gmail.com");
            message.setTo(email);
            message.setSubject("Réinitialisation mot de passe - POCOYO");
            message.setText("Cliquez ici pour changer votre mot de passe : " + resetLink);

            javaMailSender.send(message);

            System.out.println(" MAIL ENVOYÉ AVEC SUCCÈS !");
        } catch (Exception e) {
            System.out.println(" ERREUR D'ENVOI MAIL : " + e.getMessage());
            e.printStackTrace();
        }


        return "redirect:/forgotPassword?success";
    }

}
