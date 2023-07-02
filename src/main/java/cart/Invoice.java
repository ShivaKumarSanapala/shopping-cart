package cart;

import lombok.*;
import services.PricingService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private String invoiceNumber;
    private LocalDate date;
    private User customer;
    private List<CartItem> cartItems;
    private String invoiceDetails;

    private final PricingService pricingService = new PricingService();

    private String generateInvoiceNumber() {
        // Generate a unique invoice number based on some logic
        Random random = new Random();
        int randomNumber = random.nextInt(1000000); // Generate a random number between 0 and 999999
        return "INV-" + String.format("%06d", randomNumber); // Format the random number with leading zeros
    }

    public Invoice generateInvoice(Map<String, CartItem> cartItemList, User cartUser) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setDate(LocalDate.now());
        invoice.setCustomer(cartUser);
        invoice.setCartItems(new ArrayList<>(cartItemList.values()));

        StringBuilder invoiceBuilder = new StringBuilder();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        invoiceBuilder.append("Invoice Number: ").append(invoice.getInvoiceNumber()).append("\n");
        invoiceBuilder.append("Date: ").append(invoice.getDate()).append("\n");
        invoiceBuilder.append("Customer: ").append(invoice.getCustomer().getName()).append("\n");
        invoiceBuilder.append("Email: ").append(invoice.getCustomer().getEmail()).append("\n");
        invoiceBuilder.append("Items:").append("\n");

        for (CartItem cartItem : invoice.getCartItems()) {
            String productName = cartItem.getProductName();
            int quantity = cartItem.getQuantity();
            double itemPrice = cartItem.getCartItemPrice(); // Use the cartItemPrice
            invoiceBuilder.append("Cart contains ")
                    .append(quantity)
                    .append(" x ")
                    .append(productName)
                    .append(" (Price: ")
                    .append(decimalFormat.format(itemPrice))
                    .append(")\n");
        }

        double subtotal = pricingService.calculateSubtotal(invoice.getCartItems());
        double tax = pricingService.calculateTax(subtotal);
        invoiceBuilder.append("Subtotal = ")
                .append(decimalFormat.format(subtotal))
                .append("\n");
        invoiceBuilder.append("Tax = ")
                .append(decimalFormat.format(tax))
                .append("\n");
        invoiceBuilder.append("Total = ")
                .append(decimalFormat.format(pricingService.calculateTotal(subtotal, tax)))
                .append("\n");

        invoice.setInvoiceDetails(invoiceBuilder.toString());

        return invoice;
    }
}
