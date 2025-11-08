package s.ee.tag.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from tag operations.
 */
public record TagResponse(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {

    public Data.Tag[] getTags() {
        return data != null ? data.tags : new Data.Tag[0];
    }

    public record Data(@JsonProperty("tags") Tag[] tags) {
        public record Tag(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name
        ) {
        }
    }
}