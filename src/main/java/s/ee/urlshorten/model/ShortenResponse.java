package s.ee.urlshorten.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@Getter
@Setter
public class ShortenResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("data")
    private Data data;

    @JsonProperty("message")
    private String message;

    // Convenience method to get the short URL directly
    public String getShortUrl() {
        return data != null ? data.getShortUrl() : null;
    }

    public String getSlug() {
        return data != null ? data.getSlug() : null;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Data {
        @JsonProperty("custom_slug")
        private String customSlug;

        @JsonProperty("short_url")
        private String shortUrl;

        @JsonProperty("slug")
        private String slug;
    }
}