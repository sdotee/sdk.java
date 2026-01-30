/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: TextResponse.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:32
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:58
 */

package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for text operations.
 */
public record Response(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {

    public String getShortUrl() {
        return data != null ? data.shortUrl() : null;
    }

    public String getSlug() {
        return data != null ? data.slug() : null;
    }

    public record Data(
        @JsonProperty("custom_slug") String customSlug,
        @JsonProperty("short_url") String shortUrl,
        @JsonProperty("slug") String slug
    ) {}
}
