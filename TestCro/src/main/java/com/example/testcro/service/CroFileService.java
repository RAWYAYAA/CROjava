package com.example.testcro.service;

import com.example.testcro.Data;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CroFileService {
   public List<Data> readCroFile(String filename) throws IOException {

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

           // Extraire les informations spécifiques de chaque ligne
           String[] lines = fileContent.toString().split("\n");
           List<Data> result = new ArrayList<>();

           for (String line : lines) {
               Data data=new Data();
               // Extraire les informations souhaitées à l'aide des indices
               if (line.length() >= 306) {
               String codeBanqueRemettant = line.substring(0, 4);
               String nomRemettant = line.substring(84, 120);
               String numeroCheque = line.substring(170, 178);
               String numeroCompte = line.substring(184, 198);
               String cleCompte = line.substring(197, 200);
               String montant = line.substring(290, 304);

               // Stocker les informations extraites
               data.setCode_Banque_Remettant(codeBanqueRemettant);
               data.setNom_Remettant(nomRemettant);
               data.setN_Cheque(numeroCheque);
               data.setN_Compte(numeroCompte);
               data.setCle_Compte(cleCompte);
               data.setMontant(montant);
               result.add(data);
           }}

           return result;
       }
   }

}
