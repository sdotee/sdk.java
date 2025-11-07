package s.ee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonSerialize
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Response {
    @JsonProperty("code")
    private int code;

    @JsonProperty("data")
    private String data;

    @JsonProperty("message")
    private String message;
}
