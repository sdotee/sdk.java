/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: TagClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-08 09:55:08
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:40
 */

package s.ee.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s.ee.BaseIntegrationTest;
import s.ee.Config;
import s.ee.SeeException;
import s.ee.domain.DomainClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Tag service.
 *
 * @see s.ee.BaseIntegrationTest for configuration options
 */
class TagClientTest extends BaseIntegrationTest {

    private TagClient tagClient;

    @BeforeEach
    void setUp() {
        // Initialize client with configuration from environment/system properties
        tagClient = new TagClient(
            new Config(getApiBaseUrl(), getApiKey(), getTimeout())
        );
    }

    @Test
    void get() throws SeeException {
        // Act
        var response = tagClient.get();

        // Assert
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getTags(), "Tags list should not be null");
        assertEquals(DomainClient.NO_ERROR, response.code(), "Response code should be NO_ERROR");
    }
}
