/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: DomainResponse.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-12-05 16:08:46
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:21
 */

package s.ee.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from domain operations.
 */
public record DomainResponse(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {

    public String[] getDomains() {
        return data != null ? data.domains() : new String[0];
    }

    public record Data(@JsonProperty("domains") String[] domains) {
    }
}
