package s.ee.urlshorten.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request object for creating a shortened URL.
 * Use factory methods to create instances with only required fields.
 */
public record ShortenRequest(
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
    public static ShortenRequest of(String domain, String targetUrl) {
        return new ShortenRequest(null, domain, null, null, null, null, targetUrl, null);
    }

    /**
     * Creates a copy with custom slug.
     *
     * @param customSlug the custom slug
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withCustomSlug(String customSlug) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with title.
     *
     * @param title the title
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withTitle(String title) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with expiration redirect URL.
     *
     * @param expirationRedirectUrl the expiration redirect URL
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withExpirationRedirectUrl(String expirationRedirectUrl) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with expiration timestamp.
     *
     * @param expireAt the expiration timestamp
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withExpireAt(Long expireAt) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with password.
     *
     * @param password the password
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withPassword(String password) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }

    /**
     * Creates a copy with tag IDs.
     *
     * @param tagIds the tag IDs
     * @return a new ShortenRequest instance
     */
    public ShortenRequest withTagIds(List<Integer> tagIds) {
        return new ShortenRequest(customSlug, domain, expirationRedirectUrl, expireAt, password, tagIds, targetUrl, title);
    }
}
