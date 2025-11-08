package s.ee.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s.ee.BaseIntegrationTest;
import s.ee.SeeException;
import s.ee.domain.DomainClient;
import s.ee.tag.config.TagConfig;

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
            new TagConfig(getApiBaseUrl(), getApiKey(), getTimeout())
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