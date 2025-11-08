package s.ee.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s.ee.SeeException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SeeCli.
 * Note: These tests require a valid API key and active internet connection.
 * Set SEE_API_KEY environment variable to run integration tests.
 */
class SeeCliTest {
    private SeeCli cli;
    private static final String TEST_DOMAIN = "s.ee";
    private static final String TEST_TARGET_URL = "https://www.example.com";

    @BeforeEach
    void setUp() {
        // Use environment variable for API key
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            // Use a dummy API key for unit tests that don't require actual API calls
            apiKey = "test-api-key";
        }
        cli = new SeeCli(apiKey);
    }

    @Test
    void testConstructorWithApiKey() {
        var cli = new SeeCli("test-key");
        assertNotNull(cli);
    }

    @Test
    void testConstructorWithAllParameters() {
        var cli = new SeeCli("test-key", "https://api.test.com", 20);
        assertNotNull(cli);
    }

    @Test
    void testConstructorThrowsExceptionForNullApiKey() {
        assertThrows(IllegalArgumentException.class, () -> new SeeCli(null));
    }

    @Test
    void testConstructorThrowsExceptionForEmptyApiKey() {
        assertThrows(IllegalArgumentException.class, () -> new SeeCli(""));
    }

    @Test
    void testConstructorThrowsExceptionForBlankApiKey() {
        assertThrows(IllegalArgumentException.class, () -> new SeeCli("   "));
    }

    // Integration tests - these require a valid API key
    @Test
    void testShortenUrl() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        assertDoesNotThrow(() -> {
            String shortUrl = cli.shorten(TEST_DOMAIN, TEST_TARGET_URL);
            assertNotNull(shortUrl);
            assertTrue(shortUrl.contains(TEST_DOMAIN));
            System.out.println("Created short URL: " + shortUrl);
        });
    }

    @Test
    void testShortenUrlWithCustomSlug() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        String customSlug = "test-" + System.currentTimeMillis();
        assertDoesNotThrow(() -> {
            String shortUrl = cli.shortenWithSlug(TEST_DOMAIN, TEST_TARGET_URL, customSlug);
            assertNotNull(shortUrl);
            assertTrue(shortUrl.contains(customSlug));
            System.out.println("Created short URL with custom slug: " + shortUrl);
        });
    }

    @Test
    void testShortenUrlWithTitleAndTags() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        assertDoesNotThrow(() -> {
            String shortUrl = cli.shortenWithTitleAndTags(
                TEST_DOMAIN, 
                TEST_TARGET_URL, 
                "Test Title",
                List.of(1, 2)
            );
            assertNotNull(shortUrl);
            assertTrue(shortUrl.contains(TEST_DOMAIN));
            System.out.println("Created short URL with title and tags: " + shortUrl);
        });
    }

    @Test
    void testListDomains() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        assertDoesNotThrow(() -> {
            List<String> domains = cli.listDomains();
            assertNotNull(domains);
            assertFalse(domains.isEmpty());
            System.out.println("Available domains: " + domains);
        });
    }

    @Test
    void testListTags() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        assertDoesNotThrow(() -> {
            List<String> tags = cli.listTags();
            assertNotNull(tags);
            // Tags list might be empty if no tags are configured
            System.out.println("Available tags: " + tags);
        });
    }

    @Test
    void testDeleteShortUrl() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        // First create a short URL, then delete it
        assertDoesNotThrow(() -> {
            String customSlug = "delete-test-" + System.currentTimeMillis();
            String shortUrl = cli.shortenWithSlug(TEST_DOMAIN, TEST_TARGET_URL, customSlug);
            System.out.println("Created short URL for deletion: " + shortUrl);

            // Delete it
            boolean deleted = cli.deleteShortUrl(TEST_DOMAIN, customSlug);
            assertTrue(deleted);
            System.out.println("Successfully deleted: " + customSlug);
        });
    }

    @Test
    void testUpdateShortUrl() {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.out.println("Skipping integration test: SEE_API_KEY not set");
            return;
        }

        var cli = new SeeCli(apiKey);
        // First create a short URL, then update it
        assertDoesNotThrow(() -> {
            String customSlug = "update-test-" + System.currentTimeMillis();
            String shortUrl = cli.shortenWithSlug(TEST_DOMAIN, TEST_TARGET_URL, customSlug);
            System.out.println("Created short URL for update: " + shortUrl);

            // Update it
            String newTargetUrl = "https://www.example.org";
            boolean updated = cli.updateShortUrl(TEST_DOMAIN, customSlug, newTargetUrl);
            assertTrue(updated);
            System.out.println("Successfully updated: " + customSlug);
        });
    }

    @Test
    void testMainWithNoArguments() {
        // This test verifies that main exists
        // We can't easily test System.exit(), so we'll just ensure the method exists
        try {
            assertNotNull(SeeCli.class.getMethod("main", String[].class));
        } catch (NoSuchMethodException e) {
            fail("main method should exist");
        }
    }
}
