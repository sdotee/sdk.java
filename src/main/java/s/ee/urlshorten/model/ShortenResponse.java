package s.ee.urlshorten.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from URL shortening operations.
 */
public record ShortenResponse(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {
    public String getShortUrl() {
        return data != null ? data.shortUrl() : null;
    }

    public String getSlug() {
        return data != null ? data.slug() : null;
    }

    public record Data(
        @JsonProperty("custom_slug") String customSlug,
        @JsonProperty("short_url") String shortUrl,
        @JsonProperty("slug") String slug
    ) {}
}