package s.ee.qrcode.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class QrcodeModels {
    private QrcodeModels() {}

    public record CreateRequest(
        @JsonProperty("target_url") String targetUrl,
        String title,
        String domain,
        @JsonProperty("custom_slug") String customSlug
    ) {
        public static CreateRequest of(String targetUrl, String title) {
            return new CreateRequest(targetUrl, title, null, null);
        }
    }

    public record DeleteRequest(String domain, String slug) {}

    public record CreateResponse(int code, Item data, String message) {}

    public record HistoryResponse(int code, Data data, String message) {
        public record Data(@JsonProperty("qrcodes") List<Item> qrcodes, long total) {}
    }

    public record Item(
        @JsonProperty("created_at") long createdAt,
        @JsonProperty("custom_slug") String customSlug,
        String domain,
        @JsonProperty("pdf_url") String pdfUrl,
        @JsonProperty("png_url") String pngUrl,
        @JsonProperty("scan_count") long scanCount,
        @JsonProperty("short_url") String shortUrl,
        String slug,
        @JsonProperty("svg_url") String svgUrl,
        String title
    ) {}
}
