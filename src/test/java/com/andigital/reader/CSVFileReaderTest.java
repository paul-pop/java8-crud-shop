package com.andigital.reader;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.testng.Assert.*;

/**
 * Tests for {@link CSVFileReader}
 *
 * Created by Paul Pop on 16/08/15.
 */
public class CSVFileReaderTest {

    private static final String VALID_CSV = "test-valid-data.csv";
    private static final String NO_DATA_CSV = "test-no-data.csv";
    private static final String EMPTY_CSV = "test-empty.csv";

    private Reader<Stream<String>, Path> reader;

    @BeforeMethod
    public void beforeMethod() {
        reader = new CSVFileReader();
    }

    @Test(expectedExceptions = IOException.class)
    public void whenEmptyPathThenThrowException() throws IOException {
        reader.read(null);

        // Should not get here
        fail();
    }

    @Test
    public void whenEmptyFileThenReadCorrectly() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(EMPTY_CSV).toURI());

        Stream<String> result = reader.read(path);

        assertNotNull(result);
        assertEquals(result.count(), 0);
    }

    @Test
    public void whenNoDataInTheCSVFileReadCorrectly() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(NO_DATA_CSV).toURI());

        Stream<String> result = reader.read(path);

        assertNotNull(result);
        assertEquals(result.count(), 0);
    }

    @Test
    public void whenValidCSVFileReadCorrectly() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource(VALID_CSV).toURI());

        Stream<String> result = reader.read(path);
        assertNotNull(result);

        // persist state of stream into array so we can make assertions
        Object[] results = result.toArray();
        assertEquals(results.length, 3);
        assertEquals(results[0], "1,A,£1.00");
        assertEquals(results[1], "2,B,£2.12");
        assertEquals(results[2], "3,C,£3.23");
    }


}
