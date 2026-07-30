package s.ee.url.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VisitStatResponse(int code, Data data, String message) {
    public record Data(@JsonProperty("visit_count") long visitCount) {}
}
