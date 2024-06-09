package com.dgarciacasam.RocketAPI;

import lombok.experimental.UtilityClass;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;


@UtilityClass
public class Utils {
    public String getProfileImages(Integer userId) throws IOException {
        String currentPath = Paths.get("").toAbsolutePath().toString();
        File currentDir = new File(currentPath);

        System.out.println("Listing files in directory: " + currentDir.getAbsolutePath());
        listFilesInDirectoryRecursively(currentDir, 0);


        Path imagePath = Paths.get("src", "main", "resources", "static", "images", "profile", userId + ".jpg");
        if (!Files.exists(imagePath)) {
            imagePath = Paths.get( "src", "main", "resources", "0.jpg");
        }
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        return imageBase64;
    }

    private void listFilesInDirectoryRecursively(File directory, int depth) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                printIndented(file.getName(), depth);
                if (file.isDirectory()) {
                    listFilesInDirectoryRecursively(file, depth + 1);
                }
            }
        }
    }

    private void printIndented(String name, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(name);
    }
}
