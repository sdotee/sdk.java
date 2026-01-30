package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request object for deleting a shortened URL.
 * This record only contains required fields, so no factory methods are needed.
 */
public record DeleteRequest(
    @JsonProperty("domain") String domain,
    @JsonProperty("slug") String slug
) {
    /**
     * Creates a new delete request.
     *
     * @param domain the domain of the shortened URL
     * @param slug the slug of the shortened URL
     * @return a new DeleteRequest instance
     */
    public static DeleteRequest of(String domain, String slug) {
        return new DeleteRequest(domain, slug);
    }
}
