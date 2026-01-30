package s.ee.text;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.SeeException;
import s.ee.common.DomainResponse;
import s.ee.text.model.*;
import s.ee.text.model.DeleteRequest;

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
    public Response create(CreateRequest request) throws SeeException {
        return post("/text", request, Response.class);
    }

    /**
     * Updates a text sharing.
     *
     * @param request the update request
     * @return the generic response
     * @throws SeeException if the operation fails
     */
    public s.ee.common.Response update(UpdateRequest request) throws SeeException {
        return put("/text", request, s.ee.common.Response.class);
    }

    /**
     * Deletes a text sharing.
     *
     * @param request the delete request
     * @return the generic response
     * @throws SeeException if the operation fails
     */
    public s.ee.common.Response delete(DeleteRequest request) throws SeeException {
        return delete("/text", request, s.ee.common.Response.class);
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
