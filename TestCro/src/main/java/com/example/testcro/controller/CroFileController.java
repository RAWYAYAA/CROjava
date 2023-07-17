package com.example.testcro.controller;

import com.example.testcro.Data;
import com.example.testcro.service.CroFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cro-files")
public class CroFileController {

    private final CroFileService croFileService;

    @Autowired
    public CroFileController(CroFileService croFileService) {
        this.croFileService = croFileService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<List<Data>> readCroFile(@PathVariable String filename) {
        try {
            List<Data> fileContent = croFileService.readCroFile(filename);
           /* String[] lines = fileContent.split("\n");
            Set<String> fileLines = new HashSet<>();
            for (String line : lines) {
                // Extraire les informations souhaitées de chaque ligne ici si nécessaire
                fileLines.add(line);
            }
*/
            return ResponseEntity.ok(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
