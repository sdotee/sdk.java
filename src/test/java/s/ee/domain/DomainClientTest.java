/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: DomainClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-08 09:00:06
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:34
 */

package s.ee.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import s.ee.BaseIntegrationTest;
import s.ee.Config;
import s.ee.SeeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Domain service.
 *
 * @see s.ee.BaseIntegrationTest for configuration options
 */
class DomainClientTest extends BaseIntegrationTest {

    private static DomainClient domainClient;

    @BeforeAll
    static void setUpAll() {
        // Initialize client with configuration from environment/system properties
        domainClient = new DomainClient(
            new Config(getApiBaseUrl(), getApiKey(), getTimeout())
        );
    }

    @Test
    void get() throws SeeException {
        // Act
        var response = domainClient.get();

        // Assert
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getDomains(), "Domains list should not be null");
        assertEquals(DomainClient.NO_ERROR, response.code(), "Response code should be NO_ERROR");
    }
}
