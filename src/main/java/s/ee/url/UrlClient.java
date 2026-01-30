package s.ee.url;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.DomainResponse;
import s.ee.common.SeeException;
import s.ee.url.model.CreateRequest;
import s.ee.url.model.DeleteRequest;
import s.ee.url.model.Response;
import s.ee.url.model.UpdateRequest;

/**
 * Client for URL shortening operations.
 */
public class UrlClient extends Client {

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
        return get("/domains", null, DomainResponse.class);
    }
}
