package com.codebasicz.inventory.util;

import com.codebasicz.inventory.exception.custom.FailedToConvertFileToObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> List<T> parseCSV(String csvContent, Class<T> classType) {
        List<Map<String, String>> csvData = new ArrayList<>();
        List<T> result = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new StringReader(csvContent))) {
            String[] headers = reader.readNext();
            String[] row;
            while ((row = reader.readNext()) != null) {
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    rowMap.put(headers[i], row[i]);
                }
                csvData.add(rowMap);
            }
            for (Map<String, String> csv : csvData) {
                T data = objectMapper.convertValue(csv, classType);
                result.add(data);
            }
            return result;
        } catch (IOException | CsvValidationException e) {
            throw new FailedToConvertFileToObject("Failed to convert the file to object.");
        }
    }

}
