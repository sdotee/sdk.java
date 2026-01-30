package s.ee.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request object for updating a shortened URL.
 * Use factory methods to create instances with only required fields.
 */
public record UpdateRequest(
        @JsonProperty("domain") String domain,
        @JsonProperty("slug") String slug,
        @JsonProperty("target_url") String targetUrl,
        @JsonProperty("title") String title
) {
    /**
     * Creates a request with required fields only.
     *
     * @param domain the domain of the shortened URL
     * @param slug the slug of the shortened URL
     * @return a new UpdateRequest instance
     */
    public static UpdateRequest of(String domain, String slug) {
        return new UpdateRequest(domain, slug, null, null);
    }

    /**
     * Creates a copy with updated target URL.
     *
     * @param targetUrl the new target URL
     * @return a new UpdateRequest instance
     */
    public UpdateRequest withTargetUrl(String targetUrl) {
        return new UpdateRequest(domain, slug, targetUrl, title);
    }

    /**
     * Creates a copy with updated title.
     *
     * @param title the new title
     * @return a new UpdateRequest instance
     */
    public UpdateRequest withTitle(String title) {
        return new UpdateRequest(domain, slug, targetUrl, title);
    }
}
