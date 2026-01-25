package fr.eni.springboot.controller;

import fr.eni.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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



    }
