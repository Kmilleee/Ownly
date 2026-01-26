package fr.eni.springboot.controller;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
    public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/")
        public String displayIndex(){
            return "index";
        }

        //test tailwinds
        @GetMapping("/test")
        public String displaytest(){
            return "test";
        }

        @GetMapping("/user")
        public String displayUser(Model model){
            model.addAttribute("userList", userService.readUser());

            return "user";
        }

        @GetMapping("/addUser")
        public String displayAdduser(Model model){

            model.addAttribute("userOBJ", new User());
            return "addUser";
        }

//        @PostMapping("/addUser")
//        public String createUser(@ModelAttribute("userOBJ") User user){
//            userService.createUser(user);
//            userService.readUser().forEach(System.out::println);
//            return "redirect:/user";
//        }

    // Dans UserController.java

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute("userOBJ") User user){
        // AJOUTE CES LIGNES POUR LE DEBUG
        System.out.println("---------------- DEBUG ----------------");
        System.out.println("Pseudo: " + user.getUsername());
        System.out.println("Prenom: " + user.getFirstName()); // Est-ce encore null ?
        System.out.println("MDP: " + user.getPassword());
        System.out.println("Credit: " + user.getCredit());
        System.out.println("Active: " + user.isActive()); // ou user.getActive()
        System.out.println("---------------------------------------");

        userService.createUser(user); // Ça plante ici
        return "redirect:/user";
    }


    }
