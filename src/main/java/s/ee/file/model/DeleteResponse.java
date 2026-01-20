package s.ee.file.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from tag operations.
 */
public record DeleteResponse(@JsonProperty("code") String code, @JsonProperty("success") boolean success,
                             @JsonProperty("message") String message) {
}
