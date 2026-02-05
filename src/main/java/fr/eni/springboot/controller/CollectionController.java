package fr.eni.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CollectionController {

    @GetMapping("/collection")
    public String showCollection(Model model) {


        File folder = new File("src/main/resources/static/img/collection");
        List<String> imageFiles = new ArrayList<>();

        // Récupère les noms de fichiers
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    imageFiles.add(file.getName());
                }
            }
        }


        model.addAttribute("cardList", imageFiles);

        return "collection";
    }
}
