package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import exceptions.CustomHttpRequestException;
import exceptions.ProductDetailsException;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ProductApiService {
    private static final String PRODUCT_DETAILS_API_BASE_URL = "https://equalexperts.github.io/backend-take-home-test-data/";

    public ProductDetailsResponse getProductDetails(String productName) throws ProductDetailsException, CustomHttpRequestException {
        String apiUrl = PRODUCT_DETAILS_API_BASE_URL + productName + ".json";
        String response = CustomHttpRequest.httpGETRequest(apiUrl);

        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        if (jsonObject.has("title") && jsonObject.has("price")) {
            String title = jsonObject.get("title").getAsString();
            double price = jsonObject.get("price").getAsDouble();
            return new ProductDetailsResponse(title, price);
        } else {
            throw new ProductDetailsException("Failed to retrieve product details for: " + productName);
        }
    }

    public boolean verifyProduct(String productName) throws ProductDetailsException {
        try {
            getProductDetails(productName);
            return true;
        } catch (ProductDetailsException e) {
            return false;
        } catch (CustomHttpRequestException e) {
            throw new ProductDetailsException("Invalid product: " + productName);
        }
    }

    @Data
    @AllArgsConstructor
    public static class ProductDetailsResponse {
        private String title;
        private double price;
    }
}
