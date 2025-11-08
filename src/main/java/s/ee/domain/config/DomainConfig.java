package s.ee.domain.config;

import s.ee.Config;

/**
 * Configuration for domain client.
 *
 * @param baseUrl API base URL
 * @param apiKey API authentication key
 * @param timeout request timeout in seconds
 */
public record DomainConfig(String baseUrl, String apiKey, int timeout) implements Cloneable {
    
    /**
     * Creates a default configuration with the specified API key.
     *
     * @param apiKey the API key
     * @return default configuration
     */
    public static DomainConfig defaultConfig(String apiKey) {
        return new DomainConfig(Config.DEFAULT_BASE_URL, apiKey, Config.DEFAULT_TIMEOUT_SECONDS);
    }
}
