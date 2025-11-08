package s.ee.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from domain operations.
 */
public record DomainResponse(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {

    public String[] getDomains() {
        return data != null ? data.domains() : new String[0];
    }

    public record Data(@JsonProperty("domains") String[] domains) {
    }
}