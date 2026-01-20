/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: BaseIntegrationTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-08 10:04:00
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:03:23
 */

package s.ee;

/**
 * Base class for integration tests providing common configuration utilities.
 *
 * <p>Configuration can be provided via environment variables or system properties:
 * <ul>
 *   <li>SEE_API_BASE_URL: API base URL (default: https://s.ee/api/v1)</li>
 *   <li>SEE_API_KEY: API authentication key (required for tests to run)</li>
 *   <li>SEE_TEST_DOMAIN: Domain for shortened URLs (default: s.ee)</li>
 *   <li>SEE_TEST_TIMEOUT: Request timeout in seconds (default: 10)</li>
 * </ul>
 *
 * <p>Environment variables take precedence over system properties.
 *
 * @see <a href="../../../../../../TEST_CONFIGURATION.md">Test Configuration Guide</a>
 */
public abstract class BaseIntegrationTest {

    /**
     * Retrieves the API base URL from environment variable or system property.
     * Falls back to default test URL if not specified.
     *
     * @return the API base URL
     */
    protected static String getApiBaseUrl() {
        return getConfigValue("SEE_API_BASE_URL", "https://s.ee/api/v1");
    }

    /**
     * Retrieves the API key from environment variable or system property.
     * This is sensitive information and should not be hardcoded.
     *
     * @return the API key
     * @throws IllegalStateException if API key is not configured
     */
    protected static String getApiKey() {
        String apiKey = getConfigValue("SEE_API_KEY", null);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "API key not configured. Set SEE_API_KEY environment variable or system property."
            );
        }
        return apiKey;
    }

    /**
     * Retrieves the test domain from environment variable or system property.
     * Falls back to default test domain if not specified.
     *
     * @return the test domain
     */
    protected static String getTestDomain() {
        return getConfigValue("SEE_TEST_DOMAIN", "s.ee");
    }

    /**
     * Retrieves the request timeout from environment variable or system property.
     * Falls back to default timeout if not specified.
     *
     * @return the timeout in seconds
     */
    protected static int getTimeout() {
        String timeout = getConfigValue("SEE_TEST_TIMEOUT", "10");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    /**
     * Retrieves a configuration value from environment variable or system property.
     * Environment variables take precedence over system properties.
     *
     * @param key          the configuration key
     * @param defaultValue the default value if not found
     * @return the configuration value or default
     */
    protected static String getConfigValue(String key, String defaultValue) {
        // Check environment variable first (highest priority)
        String value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value;
        }

        // Check system property as fallback
        value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }

        // Return default value
        return defaultValue;
    }
}
