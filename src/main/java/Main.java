import cart.Invoice;
import cart.ShoppingCart;
import cart.User;
import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;

public class Main {
    public static void main(String[] args) {
        User user = new User("John Doe", "john.doe@example.com");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartUser(user);

        try {
            shoppingCart.addItem("cornflakes");
            shoppingCart.addItem("cornflakes", 1);
            shoppingCart.removeItemFromCart("cornflakes");

            shoppingCart.addItem("cornflakes", 1);
            shoppingCart.addItem("cornflakes", 1);
            shoppingCart.addItem("cornflakes", 1);
            shoppingCart.removeItemFromCart("cornflakes", 1);

            shoppingCart.addItem("weetabix", 1);
            // invalid product
            shoppingCart.addItem("dairy_milk_chocolate", 3);

        } catch (CustomHttpRequestException | ProductDetailsException e) {
            System.out.println("Failed to add item to cart: " + e.getMessage());
        }

        System.out.println("\n\n-----YOUR INVOICE-----\n");
        Invoice invoice = shoppingCart.generateInvoice();
        System.out.println(invoice.getInvoiceDetails());
        System.out.println("-----END OF INVOICE-----\n");


    }
}

