package s.ee.bio.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class BioModels {
    private BioModels() {}

    public record Link(String title, String url, String description) {}

    public record CreateRequest(
        String title,
        String description,
        String domain,
        @JsonProperty("custom_slug") String customSlug,
        @JsonProperty("mastodon_url") String mastodonUrl,
        @JsonProperty("rss_url") String rssUrl,
        @JsonProperty("custom_links") List<Link> customLinks
    ) {
        public static CreateRequest of(String title) {
            return new CreateRequest(title, null, null, null, null, null, null);
        }
    }

    public record UpdateRequest(
        long id,
        String title,
        String description,
        @JsonProperty("mastodon_url") String mastodonUrl,
        @JsonProperty("rss_url") String rssUrl,
        @JsonProperty("custom_links") List<Link> customLinks
    ) {}

    public record DeleteRequest(long id) {}

    public record CreateResponse(int code, Data data, String message) {
        public record Data(@JsonProperty("bio_page_id") long bioPageId,
                           @JsonProperty("short_url") String shortUrl) {}
    }

    public record HistoryResponse(int code, Data data, String message) {
        public record Data(@JsonProperty("bio_pages") List<Item> bioPages, long total) {}
        public record Item(
            @JsonProperty("created_at") long createdAt,
            @JsonProperty("custom_links") List<Link> customLinks,
            String description,
            String domain,
            long id,
            String link,
            @JsonProperty("mastodon_url") String mastodonUrl,
            @JsonProperty("rss_url") String rssUrl,
            String slug,
            String title
        ) {}
    }
}
