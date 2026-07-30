package s.ee.token;

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.SeeException;
import s.ee.token.model.TokenResponse;

import java.util.Map;

public class TokenClient extends Client {
    public TokenClient(Config config) {
        super(config);
    }

    public TokenResponse check(String token) throws SeeException {
        return post("/token/check", Map.of("token", token), TokenResponse.class);
    }
}
