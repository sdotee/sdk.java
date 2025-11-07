package s.ee;

import java.io.IOException;

public class SeeException extends IOException {
    private final Response response;

    public SeeException(String message) {
        super(message);
        this.response = Response.builder().message(message).build();
    }

    public SeeException(Response errorResponse) {
        super(errorResponse.toString());
        this.response = errorResponse;
    }

    public SeeException(String message, Throwable cause) {
        super(message, cause);
        this.response = Response.builder().message(message).build();
    }
}
