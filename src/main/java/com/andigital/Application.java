package com.andigital;

import com.andigital.domain.Product;
import com.andigital.parser.CSVFileParser;
import com.andigital.parser.Parser;
import com.andigital.reader.CSVFileReader;
import com.andigital.reader.Reader;
import com.andigital.shop.ShopService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Paul Pop on 22/12/2015.
 */
public class Application {

    private static final String CSV_FILE = "product-data.csv";

    private ShopService service;

    /**
     * Initialize welcome message and load the list of products
     *
     * @throws URISyntaxException
     */
    public void init() throws URISyntaxException, IOException {
        printInstructions();

        // Initialize reader - from file
        Reader<Stream<String>, Path> reader = new CSVFileReader();
        Stream<String> data = reader.read(Paths.get(ClassLoader.getSystemResource(CSV_FILE).toURI()));

        // Initialize parser - from data stream
        Parser<Set<Product>, Stream<String>> parser = new CSVFileParser();
        Set<Product> products = parser.parse(data);

        service = new ShopService();
        service.loadProducts(products);
    }

    /**
     * Start the System.in reader to read user input and call the {@link ShopService} operations
     */
    public void readInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String inputValue = br.readLine();
            if (inputValue.startsWith("add")) {
                service.addProductToBasket(inputValue.split(" ")[1]);
            } else if (inputValue.startsWith("remove")) {
                service.removeProductFromBasket(inputValue.split(" ")[1]);
            } else if (inputValue.equalsIgnoreCase("list")) {
                service.showProducts();
            } else if (inputValue.equalsIgnoreCase("basket")) {
                service.showBasket();
            } else if (inputValue.equalsIgnoreCase("total")) {
                service.getTotal();
            } else if ("exit".equalsIgnoreCase(inputValue)) {
                service.exit();
            } else {
                printInstructions();
            }
        }
    }

    private void printInstructions() {
        System.out.println("Enter \"list\" to show a list of products in the inventory");
        System.out.println("Enter \"add <ProductId>\" to add to basket");
        System.out.println("Enter \"remove <ProductId>\" to remove from basket");
        System.out.println("Enter \"basket\" to show the list of products in your basket");
        System.out.println("Enter \"total\" to show the total price of the basket");
        System.out.println("Enter \"exit\" to quit");
    }
}
