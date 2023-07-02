import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;
import org.junit.jupiter.api.Test;
import services.PricingService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingServiceTest {
    @Test
    public void testGetProductPrice() throws CustomHttpRequestException, ProductDetailsException {
        PricingService pricingService = new PricingService();
        double price = pricingService.getProductPrice("cornflakes");
        assertEquals(2.52, price); // Check if the retrieved price is approximately equal to the expected value
    }
}
