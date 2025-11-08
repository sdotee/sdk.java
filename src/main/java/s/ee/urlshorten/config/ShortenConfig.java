package s.ee.urlshorten.config;

import s.ee.Config;

/**
 * Configuration for URL shortening client.
 *
 * @param baseUrl API base URL
 * @param apiKey API authentication key
 * @param timeout request timeout in seconds
 */
public record ShortenConfig(String baseUrl, String apiKey, int timeout) implements Cloneable {

    /**
     * Creates a default configuration with the specified API key.
     *
     * @param apiKey the API key
     * @return default configuration
     */
    public static ShortenConfig defaultConfig(String apiKey) {
        return new ShortenConfig(Config.DEFAULT_BASE_URL, apiKey, Config.DEFAULT_TIMEOUT_SECONDS);
    }
}
