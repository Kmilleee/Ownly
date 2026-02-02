package fr.eni.springboot.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadService {

    public static void uploadFile(String uploadDir, String fileName, MultipartFile uploadedFile) throws IOException {
        // Transforme le String du chemin de l'image à stocker (exemple : "itemsSold-photos/42") en "vrai" chemin
        Path uploadPath = Paths.get(uploadDir);

        // Vérifie si le dossier existe déjà sinon le créer
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = uploadedFile.getInputStream()) {
            //"Tuyau" on récupère le flux (géré par le try pour fermeture automatique)

            //Colle bout à bout les éléments pour l'emplacement de l'image (Dossier + / + NomDuFichier)
            Path filePath = uploadPath.resolve(fileName);

            //Envoie l'image vers son emplacement (et efface / remplace l'ancienne image si l'ancienne à le même nom)
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("Erreur lors de l'upload : " + e.getMessage());
        }
    }
}