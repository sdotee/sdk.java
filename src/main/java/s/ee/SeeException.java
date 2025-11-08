package s.ee;

import java.io.IOException;

/**
 * Exception thrown when SEE API operations fail.
 */
public class SeeException extends IOException {
    private final Response response;

    public SeeException(String message) {
        super(message);
        this.response = new Response(0, null, message);
    }

    public SeeException(Response errorResponse) {
        super(errorResponse.toString());
        this.response = errorResponse;
    }

    public SeeException(String message, Throwable cause) {
        super(message, cause);
        this.response = new Response(0, null, message);
    }
    
    public Response getResponse() {
        return response;
    }
}
