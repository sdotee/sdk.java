package s.ee.urlshorten;

import s.ee.Client;
import s.ee.Response;
import s.ee.SeeException;
import s.ee.urlshorten.config.ShortenConfig;
import s.ee.urlshorten.exception.UrlShortenException;
import s.ee.urlshorten.model.DeleteRequest;
import s.ee.urlshorten.model.ShortenRequest;
import s.ee.urlshorten.model.ShortenResponse;
import s.ee.urlshorten.model.UpdateRequest;

public class ShortenClient extends Client {

    public ShortenClient(ShortenConfig config) {
        super(config);
    }

    /**
     * Create a shortened URL
     *
     * @param request The shorten request containing target URL and other parameters
     * @return ShortenResponse containing the shortened URL information
     * @throws UrlShortenException If the request fails
     */
    public ShortenResponse create(ShortenRequest request) throws SeeException {
        return post(request, ShortenResponse.class);
    }

    /**
     * Delete a shortened URL
     *
     * @param request The delete request containing slug and domain
     * @return DeleteResponse containing the result
     * @throws UrlShortenException If the request fails
     */
    public Response delete(DeleteRequest request) throws SeeException {
        return delete(request, Response.class);
    }

    /**
     * Update a shortened URL
     *
     * @param request The update request containing new parameters
     * @return UpdateResponse containing the updated URL information
     * @throws UrlShortenException If the request fails
     */
    public Response update(UpdateRequest request) throws SeeException {
        return put(request, Response.class);
    }
}
