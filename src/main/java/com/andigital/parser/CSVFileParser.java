package com.andigital.parser;

import com.andigital.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * CSV file parsing, implementation of {@link Parser}. Parses the read stream into a set of {@link Product}
 *
 * Created by Paul Pop on 15/08/15.
 */
public class CSVFileParser implements Parser<Set<Product>, Stream<String>> {

    private static final Logger logger = LoggerFactory.getLogger(CSVFileParser.class);
    private static final String CSV_SEPARATOR = ",";

    @Override
    public Set<Product> parse(Stream<String> data) {
        // Map the read data to a set of products
        if (data != null) {
            Set<Product> result = data
                    .map(line -> {
                        String[] product = line.split(CSV_SEPARATOR);
                        try {
                            Integer id = Integer.valueOf(product[0].trim());
                            String name = product[1].trim();
                            Double price = Double.valueOf(product[2].trim().substring(1)); // ignore currency

                            return new Product(id, name, price);
                        } catch (Exception e) {
                            logger.warn("Failed to parse product {}", data, e);
                            // Don't throw any errors, just return null and handle it elsewhere
                            return null;
                        }
                    })
                    .collect(toSet());

            // Get rid of any nulls when parsing failed
            result.removeIf(p -> p == null);

            return result;
        }

        throw new IllegalArgumentException("Failed to parse data");
    }
}
