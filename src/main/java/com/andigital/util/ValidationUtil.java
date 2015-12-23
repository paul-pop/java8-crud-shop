package com.andigital.util;

import com.andigital.domain.Product;

import java.util.Collection;

/**
 * Created by Paul Pop on 22/12/2015.
 */
public final class ValidationUtil {

    private ValidationUtil() {}

    /**
     * Retrieve a specific product based on productId from a collection.
     * If it doesn't exist, just return null
     *
     * @param id
     * @param source
     * @return {@link Product}
     */
    public static Product retrieveProductFrom(String id, Collection<Product> source) {
        return source.stream()
                .filter(p -> Integer.valueOf(id).equals(p.getId()))
                .findFirst()
                .orElse(null);
    }
}
