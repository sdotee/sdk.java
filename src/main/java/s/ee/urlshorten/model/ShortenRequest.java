package s.ee.urlshorten.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@JsonSerialize
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortenRequest {
    @JsonProperty("custom_slug")
    private String customSlug;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("expiration_redirect_url")
    private String expirationRedirectUrl;

    @JsonProperty("expire_at")
    private Long expireAt;

    @JsonProperty("password")
    private String password;

    @JsonProperty("tag_ids")
    private List<Integer> tagIds;

    @JsonProperty("target_url")
    private String targetUrl;

    @JsonProperty("title")
    private String title;
}
