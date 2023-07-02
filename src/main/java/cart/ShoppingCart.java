package cart;

import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import services.PricingService;
import services.ProductApiService;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {
    private User cartUser;
    @Builder.Default
    private Map<String, CartItem> cartItemList = new LinkedHashMap<>();
    @Builder.Default
    private final PricingService pricingService = new PricingService();
    private final ProductApiService productApiService = new ProductApiService();
    private final Invoice invoice = new Invoice();

    public synchronized void addItem(String productName) throws CustomHttpRequestException, ProductDetailsException {
        addItem(productName, 1);
    }

    public synchronized void setQuantity(String productName, Integer quantity) {
        if (cartItemList.containsKey(productName)) {
            cartItemList.get(productName).setQuantity(quantity);
        } else {
            CartItem newItem = CartItem.builder().productName(productName).quantity(quantity).build();
            cartItemList.put(productName, newItem);
        }
    }

    public synchronized void addItem(String productName, int quantity) throws CustomHttpRequestException, ProductDetailsException {
        if (cartItemList.containsKey(productName)) {
            CartItem item = cartItemList.get(productName);
            item.increaseQuantityBy(quantity);
        } else {
            boolean isValidProduct = productApiService.verifyProduct(productName);
            if (!isValidProduct) {
                throw new ProductDetailsException("Invalid product: " + productName);
            }

            double cartItemPrice = pricingService.getProductPrice(productName);
            CartItem newItem = CartItem.builder()
                    .productName(productName)
                    .quantity(quantity)
                    .cartItemPrice(cartItemPrice)
                    .build();
            cartItemList.put(productName, newItem);
        }
    }


    // remove entire product from cart regardless of quantity
    public synchronized void removeItemFromCart(String productName) {
        cartItemList.remove(productName);
    }

    // remove specific quantity of product from cart
    public synchronized void removeItemFromCart(String productName, int quantity) {
        if (cartItemList.containsKey(productName)) {
            CartItem item = cartItemList.get(productName);
            int remainingQuantity = item.getQuantity() - quantity;
            if (remainingQuantity > 0) {
                item.setQuantity(remainingQuantity);
            } else {
                cartItemList.remove(productName);
            }
        }
    }

    public synchronized int totalNumberOfItems() {
        int totalCount = 0;
        for (CartItem item : cartItemList.values()) {
            totalCount += item.getQuantity();
        }
        return totalCount;
    }

    public synchronized void clearCart() {
        cartItemList.clear();
    }

    public Invoice generateInvoice(){
        return invoice.generateInvoice(cartItemList, cartUser);
    }
}
