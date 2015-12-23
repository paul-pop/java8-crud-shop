package com.andigital.util;

import com.andigital.domain.Product;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.testng.annotations.Test;

import java.util.List;

import static com.andigital.util.ValidationUtil.retrieveProductFrom;
import static org.testng.Assert.*;

/**
 * Tests for {@link ValidationUtil}
 *
 * Created by Paul Pop on 22/12/2015.
 */
public class ValidationUtilTest {

    private static final Product P1 = new Product(1, "A", 1.00);
    private static final Product P2 = new Product(2, "B", 2.12);
    private static final Product INVALID_PRODUCT = new Product(null, "C", 3.23);
    private static final List<Product> products = Lists.newArrayList(P1, P2);

    @Test
    public void whenValidIdAndInListThenReturnProduct() {
        Product product = retrieveProductFrom("1", products);

        assertNotNull(product);
        assertEquals(product, P1);
    }

    @Test
    public void whenEmptyCollectionThenReturnNullProduct() {
        Product product = retrieveProductFrom(null, Sets.newHashSet());

        assertNull(product);
    }

    @Test
    public void whenInvalidIdTypeAndEmptyListThenReturnNullProduct() {
        Product product = retrieveProductFrom("invalid", Sets.newHashSet());

        assertNull(product);
    }

    @Test
    public void whenNonExistingIdThenReturnNullProduct() {
        Product product = retrieveProductFrom("30", products);

        assertNull(product);
    }

    @Test
    public void whenInvalidProductInListThenReturnNullProduct() {
        Product product = retrieveProductFrom("1", Sets.newHashSet(INVALID_PRODUCT));

        assertNull(product);
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void whenInvalidIdTypeThenThrowException() {
        retrieveProductFrom("invalid", products);

        // should not get here
        fail();
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void whenNullIdThenThrowException() {
        retrieveProductFrom(null, products);

        // should not get here
        fail();
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void whenEmptyIdThenReturnNullProduct() {
        retrieveProductFrom("", products);

        // should not get here
        fail();
    }
}
