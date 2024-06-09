package com.dgarciacasam.RocketAPI;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;


@UtilityClass
public class Utils {
    public String getProfileImages(Integer userId) throws IOException {
        ClassPathResource imgFile = new ClassPathResource("/images/profile/" + userId + ".jpg");
        if (!imgFile.exists()) {
            imgFile = new ClassPathResource("/images/0.jpg");
            if (!imgFile.exists()) {
                return "";
            }

        }

        try (InputStream inputStream = imgFile.getInputStream()) {
            byte[] imageBytes = StreamUtils.copyToByteArray(inputStream);
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
            return imageBase64;
        }
    }

}
