/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: TextUpdateRequest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:32
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:03
 */

package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to update text sharing.
 */
public record UpdateRequest(
        @JsonProperty("content") String content,
        @JsonProperty("domain") String domain,
        @JsonProperty("slug") String slug,
        @JsonProperty("title") String title
) {
    public static UpdateRequest of(String domain, String slug, String content, String title) {
        return new UpdateRequest(content, domain, slug, title);
    }

    public UpdateRequest withDomain(String domain) {
        return new UpdateRequest(this.content, domain, this.slug, this.title);
    }

    public UpdateRequest withSlug(String slug) {
        return new UpdateRequest(this.content, this.domain, slug, this.title);
    }
}
