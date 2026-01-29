package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String displayIndex(Model model) {

        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/about")
    public String displayAbout(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/user")
    public String displayUser(Model model) {
        model.addAttribute("userList", userService.readUser());

        return "user";
    }

    @GetMapping("/admin/addUser")
    public String displayAdduser(Model model) {

        model.addAttribute("userOBJ", new User());
        return "admin/addUser";
    }

    @PostMapping("/admin/addUser")
    public String createUser(@ModelAttribute("userOBJ") User user) {
        userService.createUser(user);
        userService.readUser().forEach(System.out::println);
        return "redirect:/user";
    }

    @GetMapping("/admin/admin")
    public String displayAdmin(Model model) {
        model.addAttribute("activePage", "admin");
       // model.addAttribute("NameUser", userService.readUserById());
        return "admin/admin";
    }



    @PostMapping("/singup")
    public String createUserInscription(@ModelAttribute("userOBJ") User user) {
        System.out.println("Attempting to create user: " + user.getUsername());
        userService.createUser(user);
        return "redirect:/login";
    }


    @GetMapping("/singup")
    public String displaySignup(Model model) {
        model.addAttribute("userOBJ", new User());
        return "/singup";
    }

    @GetMapping("/profile")
    public String displayProfile(Principal principal, Model model){
        model.addAttribute("activePage", "profile");
        String username = principal.getName();

        User user = userService.readUserByUsername(username);

        model.addAttribute("UserCo", user);
        return"/profile";
    }

    @GetMapping("/changeProfile")
    public String displayChangeProfile(Principal principal, Model model ){
        String username = principal.getName();

        User user = userService.readUserByUsername(username);
        model.addAttribute("UserOBJ", user);
        model.addAttribute("UserCo", user);
        return"/changeProfile";
    }

    @PostMapping("/changeProfile")
    public String displayUpdateProfile(@ModelAttribute("UserOBJ") User user, Model model ){
        userService.updateUser(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                auth.getCredentials(),
                auth.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        System.out.println("Base de données et Session mises à jour !");

        System.out.println("utilisateur modifié");
        return"redirect:/profile";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long id, HttpServletRequest request) {
        userService.deleteUser(id);

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }



}
