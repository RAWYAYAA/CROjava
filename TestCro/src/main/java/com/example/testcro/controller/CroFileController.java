package com.example.testcro.controller;

import com.example.testcro.dto.DataDTO;
import com.example.testcro.entity.Data;
import com.example.testcro.service.CroFileService;
import com.example.testcro.util.EntityUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cro-files")
public class CroFileController {
    private final CroFileService croFileService;

    public CroFileController(CroFileService croFileService) {
        this.croFileService = croFileService;
    }
    @GetMapping("/read-cro-file")
    public List<DataDTO> readCroFile(
            @RequestParam String filename,
            @RequestParam int page,
            @RequestParam int pageSize
    ) {
        try {
            List<Data>  dataList =croFileService.readAndStoreCroFile(filename, page, pageSize);
            List<DataDTO> dataDTOList = dataList.stream().map(data -> EntityUtils.dataToDataDTO(data)).collect(Collectors.toList());
            return dataDTOList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/triggerCleanup")
    public ResponseEntity<String> triggerCleanup() {
        croFileService.triggerCleanup();
        return ResponseEntity.ok("Cleanup triggered.");
    }
    @GetMapping(value = "/generate-pdf/{filename}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@PathVariable String filename,
                                              @RequestParam int page,
                                              @RequestParam int pageSize) throws IOException {
        List<Data> dataList =croFileService.readAndStoreCroFile(filename, page, pageSize);
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
            List<Data> fileContent = croFileService.readAndStoreCroFile(filename, page, pageSize);
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

