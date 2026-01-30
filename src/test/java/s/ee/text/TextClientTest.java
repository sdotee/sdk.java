/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: TextClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:34:10
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:43
 */

package s.ee.text;

import org.junit.jupiter.api.*;
import s.ee.BaseIntegrationTest;
import s.ee.common.SeeException;
import s.ee.text.model.CreateRequest;
import s.ee.text.model.DeleteRequest;
import s.ee.text.model.UpdateRequest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TextClientTest extends BaseIntegrationTest {

    private static TextClient client;
    private static String slug;
    private static String domain;

    @BeforeAll
    static void setUp() {
        client = new TextClient(createConfig());
    }

    @Test
    @Order(1)
    @DisplayName("Test creating text sharing")
    void testCreate() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty()) {
            return;
        }

        var request = new CreateRequest("Hello Text World", "Test Title");
        var response = client.create(request);

        assertNotNull(response);
        assertEquals(200, response.code());
        assertNotNull(response.data());

        slug = response.data().slug();
        // Assuming the response implies default domain if not returned, or we need to know it.
        // In the update request, we need 'domain'.
        // The create response payload in documentation doesn't explicitly return domain,
        // but 'short_url' might contain it.
        // For testing, let's assume we use the default test domain or extract from short_url if possible.
        // Or we use getTestDomain() from BaseIntegrationTest if applicable.
        domain = getTestDomain();
    }

    @Test
    @Order(2)
    @DisplayName("Test updating text sharing")
    void testUpdate() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty() || slug == null) {
            return;
        }

        var request = new UpdateRequest("Updated Content", domain, slug, "Updated Title");
        var response = client.update(request);

        assertNotNull(response);
        assertEquals(200, response.code());
    }

    @Test
    @Order(3)
    @DisplayName("Test getting text domains")
    void testGetDomains() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty()) {
            return;
        }

        var response = client.getDomains();
        assertNotNull(response);
        assertEquals(200, response.code());
        assertNotNull(response.getDomains());
    }

    @Test
    @Order(4)
    @DisplayName("Test deleting text sharing")
    void testDelete() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty() || slug == null) {
            return;
        }

        var request = new DeleteRequest(domain, slug);
        var response = client.delete(request);

        assertNotNull(response);
        assertEquals(200, response.code());
    }
}
