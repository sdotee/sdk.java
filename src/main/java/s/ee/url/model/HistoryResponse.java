package s.ee.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HistoryResponse(int code, List<Item> data, String message, boolean success) {
    public record Item(
        @JsonProperty("created_at") long createdAt,
        String domain,
        @JsonProperty("object_type") int objectType,
        @JsonProperty("short_url") String shortUrl,
        String slug,
        @JsonProperty("target_url") String targetUrl,
        String title,
        @JsonProperty("visit_count") long visitCount
    ) {}
}
