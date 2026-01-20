/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: TextUpdateRequest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:32
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:03
 */

package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to update text sharing.
 */
public record TextUpdateRequest(
    @JsonProperty("content") String content,
    @JsonProperty("domain") String domain,
    @JsonProperty("slug") String slug,
    @JsonProperty("title") String title
) {}
