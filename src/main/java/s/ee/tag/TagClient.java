package s.ee.tag;

import s.ee.Client;
import s.ee.Config;
import s.ee.SeeException;
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
}
