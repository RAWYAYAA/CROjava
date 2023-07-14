package com.example.testcro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/cro-files")
public class CroFileController {

    @GetMapping("/{filename}")
    public ResponseEntity<Set<String>> readCroFile(@PathVariable String filename) {
        String filePath = "C:\\Users\\Youcode\\" + filename + ".cro";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Set<String> fileLines = new HashSet<>();
            String line;

            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }

            return ResponseEntity.ok(fileLines);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
