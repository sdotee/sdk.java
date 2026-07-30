package s.ee.common;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientTest {
    private MockWebServer server;
    private TestClient client;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        client = new TestClient(new Config(server.url("/api/v1").toString().replaceAll("/$", ""), "test-key", 5));
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void getEncodesQueryParametersAndAuthorization() throws Exception {
        server.enqueue(new MockResponse().setBody("{\"code\":200,\"data\":\"ok\",\"message\":\"\"}")
            .addHeader("Content-Type", "application/json"));

        Response response = client.getWithQuery("/items", Map.of("page", 2, "name", "a b"));

        var request = server.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v1/items", request.getRequestUrl().encodedPath());
        assertEquals("2", request.getRequestUrl().queryParameter("page"));
        assertEquals("a b", request.getRequestUrl().queryParameter("name"));
        assertEquals("test-key", request.getHeader("Authorization"));
        assertEquals(200, response.code());
    }

    @Test
    void acceptsNoContentResponses() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(204));

        assertNull(client.getNoContent("/empty"));
        assertEquals("/api/v1/empty", server.takeRequest().getPath());
    }

    @Test
    void exposesHeadersForProtocolRequests() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(204).addHeader("Upload-Offset", "42"));

        assertEquals("42", client.headOffset());
        assertEquals("1.0.0", server.takeRequest().getHeader("Tus-Resumable"));
    }

    private static final class TestClient extends Client {
        private TestClient(Config config) {
            super(config);
        }

        private Response getWithQuery(String endpoint, Map<String, ?> query) throws SeeException {
            return get(endpoint, query, Response.class);
        }

        private Void getNoContent(String endpoint) throws SeeException {
            return get(endpoint, Void.class);
        }

        private String headOffset() throws SeeException {
            return executeForHeaders("HEAD", "/upload", null, Map.of("Tus-Resumable", "1.0.0"))
                .get("Upload-Offset");
        }
    }
}
