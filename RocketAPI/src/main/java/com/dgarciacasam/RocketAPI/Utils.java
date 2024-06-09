package com.dgarciacasam.RocketAPI;

import com.dgarciacasam.RocketAPI.Projects.Model.ProjectModel;
import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;
import java.util.stream.Stream;

@UtilityClass
public class Utils {
    public String getProfileImages(Integer userId) throws IOException {
        Path profilePath = Paths.get("RocketAPI", "src", "main", "resources");

        try (Stream<Path> paths = Files.walk(profilePath)) {
            paths.forEach(path -> {
                System.out.println(path);
                if (Files.isRegularFile(path)) {
                    System.out.println("Es archivo");
                } else if (Files.isDirectory(path)) {
                    System.out.println("Es directorio");
                }
            });
        }

        Path imagePath = Paths.get("RocketAPI", "src", "main", "resources", "static", "images", "profile", userId + ".jpg");
        if (!Files.exists(imagePath)) {
            imagePath = Paths.get("RocketAPI", "src", "main", "resources", "static", "images", "profile", "0.jpg");
        }
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        return imageBase64;
    }
}
