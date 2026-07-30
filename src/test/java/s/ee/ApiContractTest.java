package s.ee;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s.ee.common.Config;
import s.ee.file.FileClient;
import s.ee.token.TokenClient;
import s.ee.url.UrlClient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiContractTest {
    private MockWebServer server;
    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        config = new Config(server.url("/api/v1").toString().replaceAll("/$", ""), "api-key", 5);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void tokenCheckPostsSwaggerBody() throws Exception {
        server.enqueue(json("{\"code\":200,\"data\":{\"token\":\"abc\",\"valid\":true,\"expires_at\":42}}"));

        assertEquals(true, new TokenClient(config).check("abc").data().valid());
        var request = server.takeRequest();
        assertEquals("POST", request.getMethod());
        assertEquals("/api/v1/token/check", request.getPath());
        assertEquals("{\"token\":\"abc\"}", request.getBody().readUtf8());
    }

    @Test
    void historyAndSimpleModeUseSwaggerQueries() throws Exception {
        server.enqueue(json("{\"code\":200,\"data\":[]}"));
        server.enqueue(json("{\"code\":200,\"data\":{\"slug\":\"x\",\"short_url\":\"https://s.ee/x\"}}"));
        var client = new UrlClient(config);

        client.getHistory(3);
        client.createSimple("https://example.com/a b", "s.ee", null, null, List.of(1, 2), null, null);

        assertEquals("3", server.takeRequest().getRequestUrl().queryParameter("page"));
        var simple = server.takeRequest().getRequestUrl();
        assertEquals("api-key", simple.queryParameter("signature"));
        assertEquals("https://example.com/a b", simple.queryParameter("url"));
        assertEquals("1,2", simple.queryParameter("tag_ids"));
        assertEquals("true", simple.queryParameter("json"));
    }

    @Test
    void tusChunkUsesProtocolHeadersAndReturnsOffset() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(204).addHeader("Upload-Offset", "3"));

        long offset = new FileClient(config).uploadLargeFileChunk("upload/id", 0, new byte[]{1, 2, 3});

        assertEquals(3, offset);
        var request = server.takeRequest();
        assertEquals("PATCH", request.getMethod());
        assertEquals("/api/v1/file/large-file-tus/upload%2Fid", request.getPath());
        assertEquals("1.0.0", request.getHeader("Tus-Resumable"));
        assertEquals("0", request.getHeader("Upload-Offset"));
        assertEquals(3, request.getBodySize());
    }

    private MockResponse json(String body) {
        return new MockResponse().setBody(body).addHeader("Content-Type", "application/json");
    }
}
