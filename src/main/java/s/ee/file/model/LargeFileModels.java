package s.ee.file.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LargeFileModels {
    private LargeFileModels() {}

    public record CreateRequest(
        @JsonProperty("file_name") String fileName,
        @JsonProperty("file_size") long fileSize,
        String alias,
        String description,
        String domain,
        @JsonProperty("expire_at") Long expireAt,
        @JsonProperty("file_hash") String fileHash,
        @JsonProperty("is_private") Integer privateFile,
        @JsonProperty("mime_type") String mimeType,
        String password,
        String title
    ) {
        public static CreateRequest of(String fileName, long fileSize) {
            return new CreateRequest(fileName, fileSize, null, null, null, null, null, null, null, null, null);
        }
    }

    public record UploadRequest(@JsonProperty("upload_id") String uploadId) {}

    public record CreateResponse(int code, Data data, String message) {
        public record Data(
            @JsonProperty("existing_file") FileResponse.Data existingFile,
            @JsonProperty("expires_at") long expiresAt,
            @JsonProperty("fast_upload") boolean fastUpload,
            @JsonProperty("file_size") long fileSize,
            long id,
            @JsonProperty("upload_id") String uploadId,
            @JsonProperty("upload_url") String uploadUrl
        ) {}
    }

    public record ProgressResponse(int code, Data data, String message) {
        public record Data(
            @JsonProperty("created_at") long createdAt,
            @JsonProperty("file_name") String fileName,
            @JsonProperty("file_size") long fileSize,
            double progress,
            int status,
            @JsonProperty("updated_at") long updatedAt,
            @JsonProperty("upload_id") String uploadId,
            @JsonProperty("uploaded_size") long uploadedSize
        ) {}
    }

    public record CompleteResponse(int code, Data data, String message) {
        public record Data(FileResponse.Data file, @JsonProperty("short_link") String shortLink) {}
    }
}
