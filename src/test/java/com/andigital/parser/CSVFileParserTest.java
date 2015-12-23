package com.andigital.parser;

import com.andigital.domain.Product;
import com.google.common.collect.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.testng.Assert.*;

/**
 * Tests for {@link CSVFileParser}
 *
 * Created by Paul Pop on 16/08/15.
 */
public class CSVFileParserTest {

    private Parser<Set<Product>, Stream<String>> parser;

    @BeforeMethod
    public void beforeMethod() {
        parser = new CSVFileParser();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void whenParsedDataIsNullThenThrowException() {
        parser.parse(null);

        // Should not get here
        fail();
    }

    @Test
    public void whenInvokedAllElementsAreBeingParsed() {
        Stream<String> data = Lists.newArrayList("1,ABC,£2.99", "2,DEF,£0.99").stream();
        Set<Product> result = parser.parse(data);

        assertFalse(result.isEmpty());
        result.forEach(element -> {
            assertNotNull(element.getId());
            assertNotNull(element.getName());
            assertNotNull(element.getPrice());
        });
    }

    @Test
    public void whenParsedDataIsPartiallyInvalidReturnSetWithoutInvalidProduct() {
        Stream<String> data = Lists.newArrayList("1,ABC", "2,DEF,£0.99").stream();
        Set<Product> result = parser.parse(data);

        assertFalse(result.isEmpty());
        result.forEach(element -> {
            assertNotNull(element.getId());
            assertNotNull(element.getName());
            assertNotNull(element.getPrice());
        });
    }

    @Test
    public void whenParsedDataDoesNotExistReturnEmptySet() {
        Stream<String> data = Lists.<String>newArrayList().stream();
        Set<Product> result = parser.parse(data);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenParsedDataIsInvalidReturnEmptySet() {
        Stream<String> data = Lists.newArrayList("").stream();
        Set<Product> result = parser.parse(data);

        assertTrue(result.isEmpty());
    }

}
