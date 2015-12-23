package com.andigital.shop;

import com.andigital.domain.Product;
import com.google.common.collect.Sets;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Tests for {@link ShopService}
 *
 * Created by Paul Pop on 16/08/15.
 */
public class ShopServiceTest {

    private static final Product P1 = new Product(1, "A", 1.00);
    private static final Product P2 = new Product(2, "B", 2.12);
    private static final Product P3 = new Product(3, "C", 3.23);

    private Set<Product> products = Sets.newHashSet(P1, P2, P3);
    private ShopService shop;

    @BeforeMethod
    public void beforeMethod() throws Exception {
        shop = new ShopService();
        shop.loadProducts(products);
    }

    /**
     * Tests for {@link ShopService#addProductToBasket}
     */
    @Test
    public void whenAddingNewProductBasketGetsUpdated() {
        shop.addProductToBasket("1");

        List<Product> basket = shop.getBasket();

        assertEquals(basket.size(), 1);
        assertBasketItem(basket.get(0), 1, "A", 1.00);
    }

    @Test
    public void whenAddingTheSameProductTwiceBasketGetsUpdated() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");

        List<Product> basket = shop.getBasket();

        assertEquals(basket.size(), 2);
        assertBasketItem(basket.get(0), 1, "A", 1.00);
        assertBasketItem(basket.get(1), 1, "A", 1.00);
        assertEquals(basket.get(0), basket.get(1)); // assert additions are similar
    }

    @Test
    public void whenUnexistingProductAddedBasketIsEmpty() {
        shop.addProductToBasket("100");

        List<Product> basket = shop.getBasket();

        assertTrue(basket.isEmpty());
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void whenInvalidProductAddedThrowsException() {
        shop.addProductToBasket("not_a_number");

        fail("Should not get here");
    }

    /**
     * Tests for {@link ShopService#removeProductFromBasket}
     */
    @Test
    public void whenRemovingExistingProductBasketGetsUpdated() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.removeProductFromBasket("1");

        List<Product> basket = shop.getBasket();

        assertEquals(basket.size(), 1);
        assertBasketItem(basket.get(0), 2, "B", 2.12);
    }

    @Test
    public void whenRemovingExistingProductTwiceBasketGetsUpdated() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.removeProductFromBasket("1");
        shop.removeProductFromBasket("1");

        List<Product> basket = shop.getBasket();

        assertEquals(basket.size(), 1);
        assertBasketItem(basket.get(0), 2, "B", 2.12);
    }

    @Test
    public void whenRemovingTwoExistingProductsBasketGetsUpdated() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.removeProductFromBasket("1");

        List<Product> basket = shop.getBasket();

        assertEquals(basket.size(), 1);
        assertBasketItem(basket.get(0), 2, "B", 2.12);
    }

    @Test
    public void whenRemovingNotExistingProductBasketDoesNotChange() {
        shop.removeProductFromBasket("1");

        List<Product> basket = shop.getBasket();

        assertTrue(basket.isEmpty());
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void whenInvalidProductRemovedThrowsException() {
        shop.addProductToBasket("1");
        shop.removeProductFromBasket("not_a_number");

        fail("Should not get here");
    }

    /**
     * Tests for {@link ShopService#getTotal}
     */
    @Test
    public void whenAddingProductsToBasketTotalIsCorrect() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.addProductToBasket("3");

        assertEquals(shop.getTotal(), 6.35);
        assertEquals(shop.getTotal(), 6.35); // make sure when you call it the second time it has the same value
    }

    @Test
    public void whenNoProductsInBasketTotalIsZero() {
        shop.addProductToBasket("1");
        shop.removeProductFromBasket("1");

        Double total = shop.getTotal();

        assertEquals(total, 0.00);
    }

    /**
     * A couple of end-to-end tests
     */
    @Test
    public void testE2E_1() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.addProductToBasket("2");
        shop.addProductToBasket("3");
        shop.addProductToBasket("3");
        shop.addProductToBasket("4");

        assertEquals(shop.getBasket().size(), 6); // should contain product 1 twice, 2 twice and 3 twice
        assertEquals(shop.getTotal(), 12.70);
    }

    @Test
    public void testE2E_2() {
        shop.addProductToBasket("1");
        shop.removeProductFromBasket("1");
        shop.addProductToBasket("2");
        shop.removeProductFromBasket("2");
        shop.addProductToBasket("3");
        shop.removeProductFromBasket("3");
        shop.addProductToBasket("50");
        shop.removeProductFromBasket("50");

        assertEquals(shop.getBasket().size(), 0); // nothing :(
        assertEquals(shop.getTotal(), 0.00);
    }

    @Test
    public void testE2E_3() {
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");
        shop.addProductToBasket("1");
        shop.addProductToBasket("2");
        shop.removeProductFromBasket("1");
        shop.removeProductFromBasket("3");

        assertEquals(shop.getBasket().size(), 1); // should only contain product 2
        assertEquals(shop.getTotal(), 2.12);
    }

    private static void assertBasketItem(Product product, Integer id, String name, Double value) {
        assertEquals(product.getId(), id);
        assertEquals(product.getName(), name);
        assertEquals(product.getPrice(), value);
    }

}
