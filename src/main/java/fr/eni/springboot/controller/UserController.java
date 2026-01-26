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

        @GetMapping("/admin/addUser")
        public String displayAdduser(Model model){

            model.addAttribute("userOBJ", new User());
            return "admin/addUser";
        }

        @PostMapping("/admin/addUser")
        public String createUser(@ModelAttribute("userOBJ") User user){
            userService.createUser(user);
            userService.readUser().forEach(System.out::println);
            return "redirect:/user";
        }




    }
