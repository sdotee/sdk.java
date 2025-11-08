package s.ee;

/**
 * Configuration for SEE API client.
 *
 * @param baseUrl API base URL
 * @param apiKey API authentication key
 * @param timeout request timeout in seconds
 */
public record Config(String baseUrl, String apiKey, int timeout) {
    public static final String DEFAULT_BASE_URL = "https://s.ee/api";
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    public static class ConfigBuilder {
        private String baseUrl;
        private String apiKey;
        private int timeout;

        public ConfigBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public ConfigBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public ConfigBuilder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Config build() {
            return new Config(baseUrl, apiKey, timeout);
        }
    }
}
