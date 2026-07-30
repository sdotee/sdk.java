package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HistoryResponse(int code, List<Item> data, String message, boolean success) {
    public record Item(
        @JsonProperty("content_preview") String contentPreview,
        @JsonProperty("created_at") long createdAt,
        String domain,
        long id,
        @JsonProperty("is_expired") boolean expired,
        @JsonProperty("short_url") String shortUrl,
        String slug,
        @JsonProperty("text_type") String textType,
        String title
    ) {}
}
