package s.ee.file.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PrivateDownloadUrlResponse(int code, Data data, String message, boolean success) {
    public record Data(
        @JsonProperty("expires_at") long expiresAt,
        @JsonProperty("file_id") long fileId,
        String url
    ) {}
}
