/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: Config.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-12-05 16:08:46
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:25
 */

package s.ee.common;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Configuration for SEE API client.
 *
 * @param baseUrl API base URL
 * @param apiKey  API authentication key
 * @param timeout request timeout in seconds
 */
public record Config(String baseUrl, String apiKey, int timeout) {
    public static final String DEFAULT_BASE_URL = "https://s.ee/api/v1";
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;

    public Config {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL must not be blank");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key must not be blank");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }

        baseUrl = normalizeBaseUrl(baseUrl);
    }

    public Config(String apiKey) {
        this(DEFAULT_BASE_URL, apiKey, DEFAULT_TIMEOUT_SECONDS);
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    private static String normalizeBaseUrl(String value) {
        var normalized = value.strip().replaceFirst("/+$", "");
        try {
            var uri = new URI(normalized);
            var scheme = uri.getScheme();
            if (uri.getHost() == null || !("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))
                || uri.getQuery() != null || uri.getFragment() != null) {
                throw new IllegalArgumentException("Base URL must be an absolute HTTP(S) URL without a query or fragment");
            }
            return normalized;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Base URL is invalid", e);
        }
    }

    public static class ConfigBuilder {
        private String baseUrl;
        private String apiKey;
        private int timeout;

        public ConfigBuilder() {
            this.baseUrl = DEFAULT_BASE_URL;
            this.timeout = DEFAULT_TIMEOUT_SECONDS;
        }

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
