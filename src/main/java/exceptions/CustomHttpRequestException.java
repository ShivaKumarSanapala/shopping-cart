package exceptions;

public class CustomHttpRequestException extends Exception {
    public CustomHttpRequestException(String message) {
        super(message);
    }

    public CustomHttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
