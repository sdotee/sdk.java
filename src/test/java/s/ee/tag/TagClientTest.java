/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: TagClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-08 09:55:08
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:40
 */

package s.ee.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s.ee.BaseIntegrationTest;
import s.ee.common.SeeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Tag service.
 *
 * @see s.ee.BaseIntegrationTest for configuration options
 */
class TagClientTest extends BaseIntegrationTest {

    private static final int NO_ERROR = 200;
    private TagClient tagClient;

    @BeforeEach
    void setUp() {
        // Initialize client with configuration from environment/system properties
        tagClient = new TagClient(createConfig());
    }

    @Test
    void get() throws SeeException {
        // Act
        var response = tagClient.get();

        // Assert
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getTags(), "Tags list should not be null");
        assertEquals(NO_ERROR, response.code(), "Response code should be NO_ERROR");
    }
}
