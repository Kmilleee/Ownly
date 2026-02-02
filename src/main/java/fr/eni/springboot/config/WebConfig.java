package fr.eni.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // On définit le chemin vers le dossier de photos
        Path uploadDir = Paths.get("itemsSold-photos");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Si une URL commence par /images/ on va chercher le fichier dans le dossier d'uploads
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}