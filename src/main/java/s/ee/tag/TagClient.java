package s.ee.tag;

import s.ee.Client;
import s.ee.Config;
import s.ee.SeeException;
import s.ee.tag.config.TagConfig;
import s.ee.tag.model.TagResponse;

/**
 * Client for tag operations.
 */
public class TagClient extends Client {
    
    public TagClient(TagConfig config) {
        super(new Config(config.baseUrl(), config.apiKey(), config.timeout()));
    }

    /**
     * Retrieves available tags.
     *
     * @return the tag response
     * @throws SeeException if the operation fails
     */
    public TagResponse get() throws SeeException {
        return get("/v1/tags", null, TagResponse.class);
    }
}