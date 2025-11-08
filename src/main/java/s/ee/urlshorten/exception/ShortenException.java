package s.ee.urlshorten.exception;

import s.ee.Response;
import s.ee.SeeException;

/**
 * Exception thrown when URL shortening operations fail.
 */
public class ShortenException extends SeeException {
    private final Response response;

    public ShortenException(String message) {
        super(message);
        this.response = Response.builder().message(message).build();
    }

    public ShortenException(Response errorResponse) {
        super(errorResponse.toString());
        this.response = errorResponse;
    }

    public ShortenException(String message, Throwable cause) {
        super(message, cause);
        this.response = Response.builder().message(message).build();
    }
}
