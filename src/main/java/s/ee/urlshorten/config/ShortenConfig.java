package s.ee.urlshorten.config;

import lombok.experimental.SuperBuilder;
import s.ee.Config;

@SuperBuilder
public class ShortenConfig extends Config {

    public static ShortenConfig defaultConfig(String apiKey) {
        return ShortenConfig.builder()
                .baseUrl(Config.DEFAULT_BASE_URL)
                .apiKey(apiKey)
                .timeout(Config.DEFAULT_TIMEOUT_SECONDS)
                .build();
    }
}
