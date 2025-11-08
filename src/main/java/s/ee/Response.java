package s.ee;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Standard API response structure.
 *
 * @param code response code
 * @param data response data
 * @param message response message
 */
public record Response(
        @JsonProperty("code") int code,
        @JsonProperty("data") String data,
        @JsonProperty("message") String message
) {
    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder {
        private int code;
        private String data;
        private String message;

        public ResponseBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder data(String data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Response build() {
            return new Response(code, data, message);
        }
    }
}
