package com.example.testcro.util;

import com.example.testcro.dto.DataDTO;
import com.example.testcro.entity.Data;
import org.modelmapper.ModelMapper;

public class EntityUtils {
    public static DataDTO dataToDataDTO(Data data) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(data, DataDTO.class);
    }
    public static Data dataDTOToData(DataDTO dataDto) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(dataDto, Data.class);
    }
}
