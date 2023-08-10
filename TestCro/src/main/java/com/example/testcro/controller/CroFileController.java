package com.example.testcro.controller;

import com.example.testcro.Data;
import com.example.testcro.service.CroFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
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
    public ResponseEntity<List<Data>> readCroFile(@PathVariable String filename,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Data> fileContent = croFileService.readCroFile(filename, page, pageSize);
            return ResponseEntity.ok(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/pdf/{filename}")
//    public ResponseEntity<byte[]> generatePdfFromData(@PathVariable String filename,
//                                                      @RequestParam(defaultValue = "1") int page,
//                                                      @RequestParam(defaultValue = "10") int pageSize) {
//        try {
//            List<Data> fileContent = croFileService.readCroFile(filename, page, pageSize);
//            // Create a new PDF document
//            PDDocument document = new PDDocument();
//            PDPage pdfPage = new PDPage(PDRectangle.A4);
//            document.addPage(pdfPage);
//            // Add content to the PDF
//            PDPageContentStream contentStream = new PDPageContentStream(document, pdfPage);
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            float yPosition = pdfPage.getMediaBox().getHeight() - 50;
//            for (Data data : fileContent) {
//                contentStream.beginText();
//                contentStream.newLineAtOffset(50, yPosition);
//                contentStream.showText(data.toString());
//                contentStream.endText();
//                yPosition -= 20;
//
//            }
//            contentStream.close();
//            // Save the PDF to a byte array
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            document.save(outputStream);
//            document.close();
//            // Prepare the PDF byte array for download
//            byte[] pdfBytes = outputStream.toByteArray();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDisposition(ContentDisposition.attachment().filename("data.pdf").build());
//
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.notFound().build();
//        }
//    }
/*@GetMapping("/pdf/{filename}")
public ResponseEntity<byte[]> generatePdfFromData(@PathVariable String filename,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
    try {
        List<Data> fileContent = croFileService.readCroFile(filename, page, pageSize);
        // Create a new PDF document
        PDDocument document = new PDDocument();
        PDPage pdfPage = new PDPage(PDRectangle.A4);
        document.addPage(pdfPage);
        // Add content to the PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, pdfPage);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float yPosition = pdfPage.getMediaBox().getHeight() - 50;
        for (Data data : fileContent) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText(data.toString());
            contentStream.endText();
            yPosition -= 20;
        }
        contentStream.close();
        // Save the PDF to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        // Prepare the PDF byte array for download
        byte[] pdfBytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("data.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }
}*/
    @GetMapping(value = "/generate-pdf/{filename}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@PathVariable String filename,
                                              @RequestParam int page,
                                              @RequestParam int pageSize) throws IOException {
        List<Data> dataList =croFileService.readCroFile(filename, page, pageSize);
        byte[] pdfBytes = croFileService.generatePdfTable(dataList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "output.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/downloadExcel/{filename}")
    public ResponseEntity<Resource> downloadCroFileAsExcel(@PathVariable String filename,
                                                           @RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<Data> fileContent = croFileService.readCroFile(filename, page, pageSize);
            String excelFilename = "C:\\Users\\Youcode\\" + filename;
            croFileService.downloadCroFileAsExcel(excelFilename, fileContent);

            File file = new File(excelFilename + ".xlsx");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

