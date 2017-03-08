package me.roybailey.springboot.common.product;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;

/**
 * Some helper utilities for loading sample data.
 */
@Slf4j
public class ProductLoaderUtil {

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

    public static <R> List<R> loadCsvRecords(String csvProductFilename, Function<Map<String, String>, R> callback) throws IOException {
        List<R> results = new ArrayList<>();
        try (
                InputStream csvInputStream = ClassLoader.getSystemResourceAsStream(csvProductFilename);
                InputStreamReader csvStreamReader = new InputStreamReader(csvInputStream);
                CSVReader reader = new CSVReader(csvStreamReader)) {
            List<String> header = Arrays.asList(reader.readNext());
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null && nextLine.length > 0) {
                // nextLine[] is an array of values from the line
                Map<String, String> record = new HashMap<>();
                for (int index = 0; index < header.size(); ++index)
                    record.put(header.get(index), nextLine[index]);
                log.info("Adding "+record);
                results.add(callback.apply(record));
            }
        }
        return results;
    }

}
