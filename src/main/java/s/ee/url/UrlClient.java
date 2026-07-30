package s.ee.url;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.DomainResponse;
import s.ee.common.SeeException;
import s.ee.url.model.CreateRequest;
import s.ee.url.model.DeleteRequest;
import s.ee.url.model.HistoryResponse;
import s.ee.url.model.VisitStatResponse;
import s.ee.url.model.Response;
import s.ee.misc.model.UpdateRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * Client for URL shortening operations.
 */
public class UrlClient extends Client {
    public static final int USAGE_NO_LIMIT = -1;

    public UrlClient(Config config) {
        super(config);
    }

    /**
     * Creates a shortened URL.
     *
     * @param request the shorten request
     * @return the shorten response
     * @throws SeeException if the operation fails
     */
    public Response create(CreateRequest request) throws SeeException {
        return post("/shorten", request, Response.class);
    }

    /**
     * Deletes a shortened URL.
     *
     * @param request the delete request
     * @return the response
     * @throws SeeException if the operation fails
     */
    public s.ee.common.Response delete(DeleteRequest request) throws SeeException {
        return delete("/shorten", request, s.ee.common.Response.class);
    }

    /**
     * Updates a shortened URL.
     *
     * @param request the update request
     * @return the response
     * @throws SeeException if the operation fails
     */
    public s.ee.common.Response update(UpdateRequest request) throws SeeException {
        return put("/shorten", request, s.ee.common.Response.class);
    }

    /**
     * Retrieves available domains.
     *
     * @return the domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse getDomains() throws SeeException {
        return get("/domains", DomainResponse.class);
    }

    public HistoryResponse getHistory(Integer page) throws SeeException {
        return get("/links", Map.of("page", page == null ? 1 : page), HistoryResponse.class);
    }

    public VisitStatResponse getVisitStatistics(String domain, String slug, String period) throws SeeException {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("domain", domain);
        query.put("slug", slug);
        query.put("period", period);
        return get("/link/visit-stat", query, VisitStatResponse.class);
    }

    public Response createSimple(String targetUrl, String domain, String customSlug, String title,
                                 List<Integer> tagIds, String password, Long expireAt) throws SeeException {
        Map<String, Object> query = new LinkedHashMap<>();
        query.put("signature", getApiKey());
        query.put("url", targetUrl);
        query.put("domain", domain);
        query.put("custom_slug", customSlug);
        query.put("title", title);
        query.put("tag_ids", tagIds == null ? null : tagIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse(""));
        query.put("password", password);
        query.put("expire_at", expireAt);
        query.put("json", true);
        return get("/shorten", query, Response.class);
    }
}
