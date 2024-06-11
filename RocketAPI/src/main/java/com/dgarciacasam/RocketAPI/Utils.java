package com.dgarciacasam.RocketAPI;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;


@UtilityClass
public class Utils {
    public String getProfileImages(Integer userId) throws IOException {
        String folderPath = "./RocketAPI/profile-pics/"; // Asegúrate de que la ruta es correcta
        String imagePath = folderPath + userId + ".jpg";
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            // Si no existe, cambiar el imagePath
            imagePath = "static/images/profile/0.jpg";
            ClassPathResource imageResource = new ClassPathResource(imagePath);
            return Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(imageResource.getInputStream()));
        }
        // Leer los bytes de la imagen
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        // Convertir los bytes a Base64
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        return imageBase64;
    }

}

