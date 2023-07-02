package services;
import cart.CartItem;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;
import lombok.Builder;

import java.text.DecimalFormat;
import java.util.List;

public class PricingService {

    private final ProductApiService productApiService = new ProductApiService();
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public double getProductPrice(String productName) throws CustomHttpRequestException, ProductDetailsException {
        return productApiService.getProductDetails(productName).getPrice();
    }

    public double calculateSubtotal(List<CartItem> cartItems) {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            double itemPrice = item.getCartItemPrice(); // Use the cartItemPrice
            subtotal += itemPrice * item.getQuantity();
        }
        return Double.parseDouble(decimalFormat.format(subtotal));
    }

    public double calculateTax(double subtotal) {
        final double TAX_RATE = 0.125; // 12.5% tax rate
        double tax = subtotal * TAX_RATE;
        return Double.parseDouble(decimalFormat.format(tax));
    }

    public double calculateTotal(double subtotal, double tax) {
        double total = subtotal + tax;
        return Double.parseDouble(decimalFormat.format(total));
    }

}

