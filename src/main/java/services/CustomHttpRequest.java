package services;

import exceptions.CustomHttpRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CustomHttpRequest {
    public static String httpGETRequest(String apiUrl) throws CustomHttpRequestException {
        try {
            URL url = new URI(apiUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                reader.close();
                return responseBody.toString();
            } else {
                throw new CustomHttpRequestException("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new CustomHttpRequestException("Failed to make HTTP request: " + e.getMessage(), e);
        } catch (URISyntaxException e) {
            throw new CustomHttpRequestException("Invalid URI syntax: " + e.getMessage(), e);
        }
    }
}
