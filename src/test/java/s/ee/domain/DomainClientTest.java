package s.ee.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import s.ee.BaseIntegrationTest;
import s.ee.SeeException;
import s.ee.domain.config.DomainConfig;

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
            new DomainConfig(getApiBaseUrl(), getApiKey(), getTimeout())
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