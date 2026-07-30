package s.ee.token.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(int code, Data data, String message) {
    public record Data(@JsonProperty("expires_at") long expiresAt, String token, boolean valid) {}
}
