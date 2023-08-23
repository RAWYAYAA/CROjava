package com.example.testcro.service;

import com.example.testcro.Data;
import com.example.testcro.repository.DataRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class CroFileService {
    @Autowired
    DataRepository dataRepository;
   /* public List<Data> readCroFile(String filename, int page, int pageSize) throws IOException {
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
                String nomRemettant = line.substring(84, 119);
                String numeroCheque = line.substring(171, 178);
                String codeAgence = line.substring(185, 190);
                String numeroCompte = line.substring(191, 198);
                String cleCompte = line.substring(198, 200);
                String montant = line.substring(291, 306).replaceAll("^0*", "");
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
    }*/
   public List<Data> readAndStoreCroFile(String filename, int page, int pageSize) throws IOException {
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

           String[] lines = fileContent.toString().split("\n");
           List<Data> result = new ArrayList<>();
           int startIndex = (page - 1) * pageSize;
           int endIndex = Math.min(startIndex + pageSize, lines.length);

           for (int i = startIndex; i < endIndex; i++) {
               String line = lines[i];
               Data data = new Data();
               //
               String codeBanqueRemettant = line.substring(0, 3);
               String nomRemettant = line.substring(84, 119);
               String numeroCheque = line.substring(171, 178);
               String codeAgence = line.substring(185, 190);
               String numeroCompte = line.substring(191, 198);
               String cleCompte = line.substring(198, 200);
               String montant = line.substring(291, 306).replaceAll("^0*", "");
               //
               data.setCode_Banque_Remettant(codeBanqueRemettant);
               data.setNom_Remettant(nomRemettant);
               data.setN_Cheque(numeroCheque);
               data.setCode_Agence(codeAgence);
               data.setN_Compte(numeroCompte);
               data.setCle_Compte(cleCompte);
               data.setMontant(montant);
               result.add(data);
           }
           return dataRepository.saveAll(result);
       }
   }
    /*public int getTotalRecords(String filename) throws IOException {
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
    }*/

    public byte[] generatePdfTable(List<Data> dataList) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 10;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            int rowsPerPage = 10;

            drawTableHeader(contentStream, margin, yPosition, tableWidth);
            yPosition -= 20;

            for (Data data : dataList) {
                if (yPosition <= margin) {
                    contentStream.close();
                    PDPage newPage = new PDPage(PDRectangle.A4);
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    yPosition = yStart;
                    drawTableHeader(contentStream, margin, yPosition, tableWidth);
                    yPosition -= 20;
                }

                drawTableRow(contentStream, margin, yPosition, tableWidth, data);
                yPosition -= 20;
            }

            contentStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
    private void drawTableHeader(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(xStart, yStart);
        contentStream.showText("CBR");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("NR");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("NC");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("CA");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("NC");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("CC");
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText("Mt");
        contentStream.endText();
    }
    private void drawTableRow(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth, Data data) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(xStart, yStart);
        contentStream.showText(data.getCode_Banque_Remettant());
        contentStream.newLineAtOffset(50, 0);
        String nomRemettant = data.getNom_Remettant();
        String[] words = nomRemettant.split("\\s+");
        StringBuilder abbreviatedNom = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                abbreviatedNom.append(word.substring(0, Math.min(3, word.length())));
            }
        }
        contentStream.showText(abbreviatedNom.toString());
        contentStream.newLineAtOffset(150, 0);
        contentStream.showText(data.getN_Cheque());
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText(data.getCode_Agence());
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText(data.getN_Compte());
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText(data.getCle_Compte());
        contentStream.newLineAtOffset(80, 0);
        contentStream.showText(data.getMontant());
        contentStream.endText();
    }
   public void downloadCroFileAsExcel(String filename, List<Data> dataList) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Code Banque Remettant");
            headerRow.createCell(1).setCellValue("Nom Remettant");
            headerRow.createCell(2).setCellValue("Numéro de Chèque");
            headerRow.createCell(3).setCellValue("Code Agence");
            headerRow.createCell(4).setCellValue("Numéro de Compte");
            headerRow.createCell(5).setCellValue("Clé Compte");
            headerRow.createCell(6).setCellValue("Montant");
            int rowNum = 1;
            for (Data data : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getCode_Banque_Remettant());
                row.createCell(1).setCellValue(data.getNom_Remettant());
                row.createCell(2).setCellValue(data.getN_Cheque());
                row.createCell(3).setCellValue(data.getCode_Agence());
                row.createCell(4).setCellValue(data.getN_Compte());
                row.createCell(5).setCellValue(data.getCle_Compte());
                row.createCell(6).setCellValue(data.getMontant());
            }
            try (FileOutputStream outputStream = new FileOutputStream(filename + ".xlsx")) {
                workbook.write(outputStream);
            }
              }
    }
        public Data saveSata(){
        Data data =new Data();

        return data;
        }
}


