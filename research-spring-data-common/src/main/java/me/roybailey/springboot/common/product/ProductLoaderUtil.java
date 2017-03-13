package me.roybailey.springboot.common.product;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.function.Function;

/**
 * Some helper utilities for loading sample data.
 */
@Slf4j
public class ProductLoaderUtil {

    private static final String datafiles = System.getProperty("datafiles");

    public static final String SAMPLE_CSV_CATEGORIES = "northwind/categories.csv";
    public static final String SAMPLE_CSV_SUPPLIERS = "northwind/suppliers.csv";
    public static final String SAMPLE_CSV_PRODUCTS = "northwind/products.csv";

    public static <R> List<R> loadCategories(Function<Map<String, String>, R> callback) throws IOException {
        return loadCsvRecords(SAMPLE_CSV_CATEGORIES, callback);
    }

    public static <R> List<R> loadSuppliers(Function<Map<String, String>, R> callback) throws IOException {
        return loadCsvRecords(SAMPLE_CSV_SUPPLIERS, callback);
    }

    public static <R> List<R> loadProducts(Function<Map<String, String>, R> callback) throws IOException {
        return loadCsvRecords(SAMPLE_CSV_PRODUCTS, callback);
    }

    public static <R> List<R> loadCsvRecords(String csvFilename, Function<Map<String, String>, R> callback) throws IOException {
        List<R> results = new ArrayList<>();
        log.info("Loading datafile " + csvFilename);
        try {
            if (datafiles != null) {
                // load from file-system
                try (
                        FileInputStream csvInputStream = new FileInputStream(datafiles+csvFilename);
                        InputStreamReader csvStreamReader = new InputStreamReader(csvInputStream)) {
                    results = loadCsvRecordsFromReader(csvStreamReader, callback);
                }

            } else {
                // load from classpath (doesn't work from spring-boot:run)
                try (
                        InputStream csvInputStream = ClassLoader.getSystemResourceAsStream(csvFilename);
                        InputStreamReader csvStreamReader = new InputStreamReader(csvInputStream)) {
                    results = loadCsvRecordsFromReader(csvStreamReader, callback);
                }
            }
        } catch (Exception err) {
            log.error("Failed to load " + csvFilename, err);
        }
        return results;
    }

    public static <R> List<R> loadCsvRecordsFromReader(Reader inputReader, Function<Map<String, String>, R> callback) throws IOException {
        List<R> results = new ArrayList<>();
        try (CSVReader reader = new CSVReader(inputReader)) {
            List<String> header = Arrays.asList(reader.readNext());
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null && nextLine.length > 0) {
                // nextLine[] is an array of values from the line
                Map<String, String> record = new HashMap<>();
                for (int index = 0; index < header.size(); ++index)
                    record.put(header.get(index), nextLine[index]);
                log.info("Adding " + record);
                results.add(callback.apply(record));
            }
        }
        return results;
    }

}
