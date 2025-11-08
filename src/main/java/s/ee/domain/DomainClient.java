package s.ee.domain;

import s.ee.Client;
import s.ee.Config;
import s.ee.SeeException;
import s.ee.domain.config.DomainConfig;
import s.ee.domain.model.DomainResponse;

/**
 * Client for domain operations.
 */
public class DomainClient extends Client {
    
    public DomainClient(DomainConfig config) {
        super(new Config(config.baseUrl(), config.apiKey(), config.timeout()));
    }

    /**
     * Retrieves available domains.
     *
     * @return the domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse get() throws SeeException {
        return get("/v1/domains", null, DomainResponse.class);
    }
}

