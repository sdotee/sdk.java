package s.ee.text;

import s.ee.Client;
import s.ee.Config;
import s.ee.Response;
import s.ee.SeeException;
import s.ee.model.DomainResponse;
import s.ee.text.model.*;
import s.ee.model.DeleteRequest;

/**
 * Client for Text operations.
 */
public class TextClient extends Client {

    public TextClient(Config config) {
        super(config);
    }

    /**
     * Creates a text sharing.
     *
     * @param request the create request
     * @return the text response
     * @throws SeeException if the operation fails
     */
    public TextResponse create(TextCreateRequest request) throws SeeException {
        return post("/text", request, TextResponse.class);
    }

    /**
     * Updates a text sharing.
     *
     * @param request the update request
     * @return the generic response
     * @throws SeeException if the operation fails
     */
    public Response update(TextUpdateRequest request) throws SeeException {
        return put("/text", request, Response.class);
    }

    /**
     * Deletes a text sharing.
     *
     * @param request the delete request
     * @return the generic response
     * @throws SeeException if the operation fails
     */
    public Response delete(DeleteRequest request) throws SeeException {
        return delete("/text", request, Response.class);
    }

    /**
     * Retrieves available text domains.
     *
     * @return the text domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse getDomains() throws SeeException {
        return get("/text/domains", null, DomainResponse.class);
    }
}
