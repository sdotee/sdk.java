package s.ee.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request object for creating a shortened URL.
 * Use factory methods to create instances with only required fields.
 */
public record CreateRequest(
    @JsonProperty("custom_slug") String customSlug,
    @JsonProperty("domain") String domain,
    @JsonProperty("expiration_redirect_url") String expirationRedirectUrl,
    @JsonProperty("expire_at") Long expireAt,
    @JsonProperty("password") String password,
    @JsonProperty("tag_ids") List<Integer> tagIds,
    @JsonProperty("target_url") String targetUrl,
    @JsonProperty("title") String title
) {
    /**
     * Creates a request with only required fields.
     *
     * @param domain the domain for the shortened URL
     * @param targetUrl the target URL to shorten
     * @return a new ShortenRequest instance
     */
    public static CreateRequest of(String domain, String targetUrl) {
        return new CreateRequest(null, domain, null, null, null, null, targetUrl, null);
    }

    /**
     * Creates a copy with custom slug.
     *
     * @param customSlug the custom slug
     * @return a new ShortenRequest instance
     */
    public CreateRequest withCustomSlug(String customSlug) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with title.
     *
     * @param title the title
     * @return a new ShortenRequest instance
     */
    public CreateRequest withTitle(String title) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with expiration redirect URL.
     *
     * @param expirationRedirectUrl the expiration redirect URL
     * @return a new ShortenRequest instance
     */
    public CreateRequest withExpirationRedirectUrl(String expirationRedirectUrl) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with expiration timestamp.
     *
     * @param expireAt the expiration timestamp
     * @return a new ShortenRequest instance
     */
    public CreateRequest withExpireAt(Long expireAt) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with password.
     *
     * @param password the password
     * @return a new ShortenRequest instance
     */
    public CreateRequest withPassword(String password) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with tag IDs.
     *
     * @param tagIds the tag IDs
     * @return a new ShortenRequest instance
     */
    public CreateRequest withTagIds(List<Integer> tagIds) {
        return new CreateRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }
}
