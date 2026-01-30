/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: DomainClient.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-12-05 16:08:46
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:24
 */

package s.ee.domain;

import s.ee.Client;
import s.ee.Config;
import s.ee.SeeException;
import s.ee.model.DomainResponse;

/**
 * Client for domain operations.
 */
public class DomainClient extends Client {

    public DomainClient(Config config) {
        super(config);
    }

    /**
     * Retrieves available domains.
     *
     * @return the domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse get() throws SeeException {
        return get("/domains", null, DomainResponse.class);
    }
}

