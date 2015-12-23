package com.andigital.shop;

import com.andigital.domain.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.andigital.util.ValidationUtil.retrieveProductFrom;
import static java.util.stream.Collectors.toList;

/**
 * Shop service - CRUD operations on basket
 */
public class ShopService {

    // Only write 2dp values
    private final DecimalFormat df = new DecimalFormat("0.00");

    private Set<Product> products = new HashSet<>();
    private List<Product> basket = new ArrayList<>();

    /**
     * Load the products from the data store and save them in memory.
     */
    public void loadProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * List the available products on stdout
     */
    public void showProducts() {
        if (!products.isEmpty()) {
            products.forEach(System.out::println);
        } else {
            System.out.println("No products have been loaded!");
        }
    }

    /**
     * List the products in the basket on stdout
     */
    public void showBasket() {
        if (!basket.isEmpty()) {
            basket.forEach(System.out::println);
        } else {
            System.out.println("No items in basket!");
        }
    }

    /**
     * Add a product to the basket
     */
    public void addProductToBasket(String productId) {
        Product product = retrieveProductFrom(productId, products);
        if (product != null) {
            basket.add(product);

            System.out.println("Added product " + product + " to your basket");
        } else {
            System.out.println("The product with id " + productId + " was not found in the list of products");
        }
    }

    /**
     * Remove a product from the basket
     */
    public void removeProductFromBasket(String productId) {
        Product product = retrieveProductFrom(productId, basket);
        if (product != null) {
            basket = basket.stream()
                    .filter(p -> !Integer.valueOf(productId).equals(p.getId())) // Apply filter for given productId
                    .collect(toList());

            System.out.println("Removed product " + product + " from your basket");
        } else {
            System.out.println("The product with id " + productId + " was not found in your basket");
        }

    }

    /**
     * Return the total value of the products in the basket, lock on reads
     * If there are no items in the basket, display an error message.
     */
    public Double getTotal() {
        double total = basket.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        System.out.println("Total value of products in your basket is: £" + df.format(total));
        return total;
    }

    /**
     * Exit the application
     */
    public void exit() {
        System.out.println("Thanks for shopping at My Shop!");
        System.exit(0);
    }

    // only for testing purposes
    List<Product> getBasket() {
        return basket;
    }

}
