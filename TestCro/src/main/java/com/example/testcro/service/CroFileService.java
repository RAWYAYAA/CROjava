package com.example.testcro.service;

import com.example.testcro.entity.Data;

import java.io.IOException;
import java.util.List;

public interface CroFileService {
    public List<Data> readAndStoreCroFile(String filename, int page, int pageSize) throws IOException;
    public byte[] generatePdfTable(List<Data> dataList) throws IOException;
    public void triggerCleanup();
    public void downloadCroFileAsExcel(String filename, List<Data> dataList) throws IOException;
}
