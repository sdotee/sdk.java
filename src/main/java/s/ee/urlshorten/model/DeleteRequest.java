package s.ee.urlshorten.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@JsonSerialize
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRequest {
    @JsonProperty("domain")
    private String domain;

    @JsonProperty("slug")
    private String slug;
}
