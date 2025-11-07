package s.ee;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Config {
    public static final String DEFAULT_BASE_URL = "https://s.ee/api";
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;

    private String baseUrl;
    private String apiKey;
    private int timeout;
}
