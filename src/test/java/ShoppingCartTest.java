import cart.CartItem;
import cart.ShoppingCart;
import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PricingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private PricingService pricingService;

    @BeforeEach
    public void setup() {
        shoppingCart = new ShoppingCart();
        pricingService = new PricingService();
    }


    @Test
    public void testAddItemToCart() throws CustomHttpRequestException, ProductDetailsException {
        String productName = "cornflakes";
        int quantity = 2;

        shoppingCart.addItem(productName, quantity);

        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.containsKey(productName));
        CartItem cartItem = cartItems.get(productName);
        Assertions.assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    public void testSetQuantity() throws CustomHttpRequestException, ProductDetailsException {
        // Arrange
        String productName = "cornflakes";
        int initialQuantity = 2;
        int newQuantity = 5;

        // Act
        shoppingCart.addItem(productName, initialQuantity);
        shoppingCart.setQuantity(productName, newQuantity);

        // Assert
        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.containsKey(productName));
        CartItem cartItem = cartItems.get(productName);
        Assertions.assertEquals(newQuantity, cartItem.getQuantity());
    }

    @Test
    public void testRemoveItemFromCart() throws CustomHttpRequestException, ProductDetailsException {
        // Arrange
        String productName = "cornflakes";
        int quantity = 2;

        // Act
        shoppingCart.addItem(productName, quantity);
        shoppingCart.removeItemFromCart(productName);

        // Assert
        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertFalse(cartItems.containsKey(productName));
    }

    @Test
    public void testRemoveItemFromCartWithQuantity() throws CustomHttpRequestException, ProductDetailsException {
        String productName = "cornflakes";
        int initialQuantity = 5;
        int removeQuantity = 2;

        shoppingCart.addItem(productName, initialQuantity);
        shoppingCart.removeItemFromCart(productName, removeQuantity);

        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.containsKey(productName));
        CartItem cartItem = cartItems.get(productName);
        Assertions.assertEquals(initialQuantity - removeQuantity, cartItem.getQuantity());
    }

    @Test
    public void testTotalNumberOfItems() throws CustomHttpRequestException, ProductDetailsException {
        String productName1 = "cornflakes";
        String productName2 = "cheerios";
        int quantity1 = 3;
        int quantity2 = 2;

        shoppingCart.addItem(productName1, quantity1);
        shoppingCart.addItem(productName2, quantity2);

        int totalItems = shoppingCart.totalNumberOfItems();
        Assertions.assertEquals(quantity1 + quantity2, totalItems);
    }

    @Test
    public void testClearCart() throws CustomHttpRequestException, ProductDetailsException {
        String productName = "cornflakes";
        int quantity = 2;

        shoppingCart.addItem(productName, quantity);
        shoppingCart.clearCart();

        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.isEmpty());
    }
    @Test
    void addItem() throws CustomHttpRequestException, ProductDetailsException {
        // Arrange
        String productName = "cornflakes";
        int quantity = 2;

        // Act
        shoppingCart.addItem(productName, quantity);

        // Assert
        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.containsKey(productName));
        CartItem cartItem = cartItems.get(productName);
        Assertions.assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void addItemInvalidProduct() {
        String productName = "invalid-product";
        int quantity = 1;
        Assertions.assertThrows(ProductDetailsException.class, () -> shoppingCart.addItem(productName, quantity));
    }

    @Test
    void clearCart() throws CustomHttpRequestException, ProductDetailsException {
        String productName = "cornflakes";
        int quantity = 2;
        shoppingCart.addItem(productName, quantity);
        shoppingCart.clearCart();
        Map<String, CartItem> cartItems = shoppingCart.getCartItemList();
        Assertions.assertTrue(cartItems.isEmpty());
    }
    @Test
    void testCalculateSubtotal() throws CustomHttpRequestException, ProductDetailsException {
        String productName1 = "cornflakes";
        String productName2 = "cheerios";
        double price1 = 2.52;
        double price2 = 8.43;
        int quantity1 = 2;
        int quantity2 = 3;
        double expectedSubtotal = price1 * quantity1 + price2 * quantity2;

        shoppingCart.addItem(productName1, quantity1);
        shoppingCart.addItem(productName2, quantity2);
        List<CartItem> cartItems = new ArrayList<>(shoppingCart.getCartItemList().values());
        double actualSubtotal = pricingService.calculateSubtotal(cartItems);

        Assertions.assertEquals(expectedSubtotal, actualSubtotal);
    }

    @Test
    void testCalculateTax() throws CustomHttpRequestException, ProductDetailsException {
        // Arrange
        String productName1 = "cornflakes";
        String productName2 = "cheerios";
        double price1 = 2.52;
        double price2 = 8.43;
        int quantity1 = 2;
        int quantity2 = 3;
        double subtotal = price1 * quantity1 + price2 * quantity2;
        double expectedTax = subtotal * 0.125;

        shoppingCart.addItem(productName1, quantity1);
        shoppingCart.addItem(productName2, quantity2);
        double actualSubtotal = pricingService.calculateSubtotal(new ArrayList<>(shoppingCart.getCartItemList().values()));
        double actualTax = pricingService.calculateTax(actualSubtotal);

        Assertions.assertEquals(expectedTax, actualTax, 0.01);
    }

    @Test
    void testCalculateTotal() throws CustomHttpRequestException, ProductDetailsException {
        // Arrange
        String productName1 = "cornflakes";
        String productName2 = "cheerios";
        double price1 = 2.52;
        double price2 = 8.43;
        int quantity1 = 2;
        int quantity2 = 3;
        double subtotal = price1 * quantity1 + price2 * quantity2;
        double tax = subtotal * 0.125;
        double expectedTotal = subtotal + tax;

        // Act
        shoppingCart.addItem(productName1, quantity1);
        shoppingCart.addItem(productName2, quantity2);
        double actualTotal = pricingService.calculateTotal(subtotal, tax);
        Assertions.assertEquals(expectedTotal, actualTotal, 0.01);
    }

}
