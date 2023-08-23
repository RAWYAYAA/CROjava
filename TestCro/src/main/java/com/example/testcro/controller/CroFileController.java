package com.example.testcro.controller;

import com.example.testcro.Data;
import com.example.testcro.service.CroFileService;
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

    public CroFileController(CroFileService croFileService) {
        this.croFileService = croFileService;
    }
   /* @GetMapping("{filename}")
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

    }*/

   @GetMapping("/read-cro-file")
   public List<Data> readCroFile(
           @RequestParam String filename,
           @RequestParam int page,
           @RequestParam int pageSize
   ) {
       try {
           return croFileService.readAndStoreCroFile(filename, page, pageSize);
       } catch (IOException e) {
           e.printStackTrace();
           return null;
       }
   }
/*
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
    */
}

