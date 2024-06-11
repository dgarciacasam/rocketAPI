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
        //Este código funciona en local
        Path imagePath = Paths.get("static", "images", "profile", userId + ".jpg");
        if (!Files.exists(imagePath)) {
            imagePath = Paths.get("static", "images", "profile", "0.jpg");
        }
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        return imageBase64;


    }

}

