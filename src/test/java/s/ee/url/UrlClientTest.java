/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: ShortenClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-08 09:59:52
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:47
 */

package s.ee.url;

import org.junit.jupiter.api.*;
import s.ee.BaseIntegrationTest;
import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.SeeException;
import s.ee.url.model.CreateRequest;
import s.ee.url.model.DeleteRequest;
import s.ee.url.model.UpdateRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the URL shortening service.
 *
 * @see BaseIntegrationTest for configuration options
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlClientTest extends BaseIntegrationTest {

    // Test data constants
    private static final String TEST_TARGET_URL = "https://www.example.com/404";
    private static final String UPDATED_TARGET_URL = "http://www.example.com/updated";

    // Shared test state
    private static UrlClient shortenClient;
    private static String testSlug;

    @BeforeAll
    static void setUpAll() {
        // Initialize client with configuration from environment/system properties
        shortenClient = new UrlClient(createConfig());
    }

    @Test
    @Order(1)
    @DisplayName("Test creating a shortened URL")
    void testCreateShortenedUrl() throws SeeException {
        // Arrange
        var request = CreateRequest.of(getTestDomain(), TEST_TARGET_URL);

        // Act
        var response = shortenClient.create(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        Assertions.assertEquals(Client.NO_ERROR, response.code(), "Response code should be NO_ERROR");
        assertNotNull(response.data(), "Response data should not be null");
        assertNotNull(response.data().shortUrl(), "Short URL should not be null");
        assertNotNull(response.data().slug(), "Slug should not be null");

        // Save slug for subsequent tests
        testSlug = response.data().slug();

        System.out.println("Created short URL: " + response.data().shortUrl());
        System.out.println("Generated slug: " + testSlug);
    }

    @Test
    @Order(2)
    @DisplayName("Test updating a shortened URL")
    void testUpdateShortenedUrl() throws SeeException {
        // Arrange
        assertNotNull(testSlug, "Test slug should be set by create test");

        var request = UpdateRequest.of(getTestDomain(), testSlug).withTargetUrl(UPDATED_TARGET_URL).withTitle("Hello, world! from " + new Date());

        // Act
        var response = shortenClient.update(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Client.NO_ERROR, response.code(), "Response code should be NO_ERROR");
//        assertNotNull(response.message(), "Response message should not be null");

        System.out.println("Updated URL successfully: " + response.message());
    }

    @Test
    @Order(3)
    @DisplayName("Test deleting a shortened URL")
    void testDeleteShortenedUrl() throws SeeException {
        // Arrange
        assertNotNull(testSlug, "Test slug should be set by create test");

        var request = DeleteRequest.of(getTestDomain(), testSlug);

        // Act
        var response = shortenClient.delete(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Client.NO_ERROR, response.code(), "Response code should be NO_ERROR");

        System.out.println("Deleted URL successfully");
    }

    @Test
    @Order(4)
    @Disabled
    @DisplayName("Test creating a shortened URL with custom slug")
    void testCreateWithCustomSlug() throws SeeException {
        // Arrange
        String customSlug = "test-custom-" + System.currentTimeMillis();
        var request = CreateRequest.of(getTestDomain(), TEST_TARGET_URL).withCustomSlug(customSlug);

        // Act
        var response = shortenClient.create(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Client.NO_ERROR, response.code(), "Response code should be NO_ERROR");
        assertNotNull(response.data(), "Response data should not be null");
        assertEquals(customSlug, response.data().customSlug(), "Slug should match custom slug");

        // Cleanup
        var deleteRequest = DeleteRequest.of(getTestDomain(), customSlug);
        shortenClient.delete(deleteRequest);

        System.out.println("Created short URL with custom slug: " + customSlug);
    }

    @Test
    @Order(5)
    @DisplayName("Test creating a shortened URL with title")
    void testCreateWithTitle() throws SeeException {
        // Arrange
        String title = "Test Title " + new Date();
        var request = CreateRequest.of(getTestDomain(), TEST_TARGET_URL).withTitle(title);

        // Act
        var response = shortenClient.create(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(Client.NO_ERROR, response.code(), "Response code should be NO_ERROR");
        assertNotNull(response.data(), "Response data should not be null");
        assertNotNull(response.data().slug(), "Slug should not be null");

        // Cleanup
        var deleteRequest = DeleteRequest.of(getTestDomain(), response.data().slug());
        shortenClient.delete(deleteRequest);

        System.out.println("Created short URL with title: " + title);
    }

    @Test
    @DisplayName("Test error handling for invalid API key")
    void testInvalidApiKey() {
        // Arrange - create client with intentionally invalid API key
        var invalidClient = new UrlClient(new Config(getApiBaseUrl(), "invalid-key", getTimeout()));

        var request = CreateRequest.of(getTestDomain(), TEST_TARGET_URL);

        // Act & Assert
        assertThrows(SeeException.class, () -> invalidClient.create(request), "Should throw SeeException for invalid API key");
    }
}
