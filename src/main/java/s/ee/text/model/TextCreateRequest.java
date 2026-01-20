/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: TextCreateRequest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:31
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:50
 */

package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to create text sharing.
 */
public record TextCreateRequest(
    @JsonProperty("content") String content,
    @JsonProperty("title") String title
) {}
