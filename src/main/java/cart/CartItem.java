package cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
    private String productName;
    private int quantity;
    private Double cartItemPrice;

    public void increaseQuantityBy(Integer number){
        quantity += number;
    }
}