package com.example.testcro.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class CroFileService {
    public String readCroFile(String filename) throws IOException {
        String filePath = "C:\\Users\\Youcode\\"+filename+".cro";
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder fileContent = new StringBuilder();

            while ((bytesRead = fis.read(buffer)) != -1) {
                String data = new String(buffer, 0, bytesRead);
                fileContent.append(data);
            }
            return fileContent.toString();
        }
    }
}
