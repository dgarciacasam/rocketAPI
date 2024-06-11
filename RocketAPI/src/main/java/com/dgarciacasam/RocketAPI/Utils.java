package com.dgarciacasam.RocketAPI;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;


@UtilityClass
public class Utils {
    public String getProfileImages(Integer userId) throws IOException {
        String imagePath = "static/images/profile/" + userId + ".jpg";
        ClassPathResource imageResource = new ClassPathResource(imagePath);

        // Si no existe la imagen, usa la imagen por defecto
        if (!imageResource.exists()) {
            imagePath = "static/images/profile/0.jpg";
            imageResource = new ClassPathResource(imagePath);
        }

        // Leer los bytes de la imagen
        byte[] imageBytes = StreamUtils.copyToByteArray(imageResource.getInputStream());

        // Convertir los bytes a Base64
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        return imageBase64;
    }

}

