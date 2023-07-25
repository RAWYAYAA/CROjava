
package com.example.testcro.service;
/*
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
           for (int i = 1; i < lines.length; i++) {
               String line = lines[i];
               Data data=new Data();
               // Extraire les informations souhaitées à l'aide des indices
               String codeBanqueRemettant = line.substring(0,3);
               String nomRemettant = line.substring(85, 120);
               String numeroCheque = line.substring(171, 178);
               String codeAgence=line.substring(185,190);
               String numeroCompte = line.substring(191, 198);
               String cleCompte = line.substring(198, 200);
               String montant = line.substring(291, 306).replaceAll("^0*", "");;
               // Stocker les informations extraites
               data.setCode_Banque_Remettant(codeBanqueRemettant);
               data.setNom_Remettant(nomRemettant);
               data.setN_Cheque(numeroCheque);
               data.setCode_Agence(codeAgence);
               data.setN_Compte(numeroCompte);
               data.setCle_Compte(cleCompte);
               data.setMontant(montant);
               result.add(data);
           }
           return result;
       }
   }
}
*/


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

    public List<Data> readCroFile(String filename, int page, int pageSize) throws IOException {
        String filePath = "C:\\Users\\Youcode\\" + filename + ".cro";
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
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                Data data = new Data();
                // Extraire les informations souhaitées à l'aide des indices
                String codeBanqueRemettant = line.substring(0, 3);
                String nomRemettant = line.substring(85, 120);
                String numeroCheque = line.substring(171, 178);
                String codeAgence = line.substring(185, 190);
                String numeroCompte = line.substring(191, 198);
                String cleCompte = line.substring(198, 200);
                String montant = line.substring(291, 306).replaceAll("^0*", "");;
                // Stocker les informations extraites
                data.setCode_Banque_Remettant(codeBanqueRemettant);
                data.setNom_Remettant(nomRemettant);
                data.setN_Cheque(numeroCheque);
                data.setCode_Agence(codeAgence);
                data.setN_Compte(numeroCompte);
                data.setCle_Compte(cleCompte);
                data.setMontant(montant);
                result.add(data);
            }

            // Pagination
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, result.size());
            return result.subList(startIndex, endIndex);
        }
    }

    public int getTotalRecords(String filename) throws IOException {
        String filePath = "C:\\Users\\Youcode\\" + filename + ".cro";
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

            // Soustraire 1 pour exclure l'en-tête de la pagination
            return lines.length - 1;
        }
    }
}

