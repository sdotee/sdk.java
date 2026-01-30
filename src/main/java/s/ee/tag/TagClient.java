package s.ee.tag;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.DomainResponse;
import s.ee.common.SeeException;
import s.ee.tag.model.TagResponse;

/**
 * Client for tag operations.
 */
public class TagClient extends Client {

    public TagClient(Config config) {
        super(config);
    }

    /**
     * Retrieves available tags.
     *
     * @return the tag response
     * @throws SeeException if the operation fails
     */
    public TagResponse get() throws SeeException {
        return get("/tags", null, TagResponse.class);
    }

    public DomainResponse getDomains() throws SeeException {
        return get("/tags/domains", null, DomainResponse.class);
    }
}
