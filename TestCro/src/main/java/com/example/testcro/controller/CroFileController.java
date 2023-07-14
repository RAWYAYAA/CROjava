package com.example.testcro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/cro-files")
public class CroFileController {

    @GetMapping("/{filename}")
    public ResponseEntity<String> readCroFile(@PathVariable String filename) {
        String filePath = "C:\\Users\\Youcode\\test.cro";

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder fileContent = new StringBuilder();

            while ((bytesRead = fis.read(buffer)) != -1) {
                String data = new String(buffer, 0, bytesRead);
                fileContent.append(data);
            }

            return ResponseEntity.ok(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

