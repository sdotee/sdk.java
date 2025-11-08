package s.ee.urlshorten;

import s.ee.Client;
import s.ee.Config;
import s.ee.Response;
import s.ee.SeeException;
import s.ee.urlshorten.config.ShortenConfig;
import s.ee.urlshorten.exception.ShortenException;
import s.ee.urlshorten.model.DeleteRequest;
import s.ee.urlshorten.model.ShortenRequest;
import s.ee.urlshorten.model.ShortenResponse;
import s.ee.urlshorten.model.UpdateRequest;

/**
 * Client for URL shortening operations.
 */
public class ShortenClient extends Client {

    public ShortenClient(ShortenConfig config) {
        super(new Config(config.baseUrl(), config.apiKey(), config.timeout()));
    }

    /**
     * Creates a shortened URL.
     *
     * @param request the shorten request
     * @return the shorten response
     * @throws SeeException if the operation fails
     */
    public ShortenResponse create(ShortenRequest request) throws SeeException {
        return post("/v1/shorten", request, ShortenResponse.class);
    }

    /**
     * Deletes a shortened URL.
     *
     * @param request the delete request
     * @return the response
     * @throws SeeException if the operation fails
     */
    public Response delete(DeleteRequest request) throws SeeException {
        return delete("/v1/shorten", request, Response.class);
    }

    /**
     * Updates a shortened URL.
     *
     * @param request the update request
     * @return the response
     * @throws SeeException if the operation fails
     */
    public Response update(UpdateRequest request) throws SeeException {
        return put("/v1/shorten", request, Response.class);
    }
}
