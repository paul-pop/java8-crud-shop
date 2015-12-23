package com.andigital.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * CSV file reader implementation of {@link Reader}. Reads all the lines from the file as a stream.
 *
 * Created by Paul Pop on 22/12/2015.
 */
public class CSVFileReader implements Reader<Stream<String>, Path> {

    private static final Logger logger = LoggerFactory.getLogger(CSVFileReader.class);

    @Override
    public Stream<String> read(Path from) throws IOException {
        try {
            if (from != null) {
                return Files.lines(from).skip(1); // Skip the first line as it's just the CSV headers
            }

            throw new IOException();
        } catch (IOException e) {
            logger.error("Error occurred when reading file: {}", from, e);
            throw e;
        }
    }

}
