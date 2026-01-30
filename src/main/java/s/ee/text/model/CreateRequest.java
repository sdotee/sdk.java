/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: TextCreateRequest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:31
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:50
 */

package s.ee.text.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to create text sharing.
 */
public record CreateRequest(
        @JsonProperty("content") String content,
        @JsonProperty("title") String title,
        @JsonProperty("text_type") String textType,
        @JsonProperty("domain") String domain,
        @JsonProperty("custom_slug") String customSlug,
        @JsonProperty("expire_at") Integer expireIn,
        @JsonProperty("password") String password
) {
    public static String TEXT_TYPE_PLAIN_TEXT = "plain_text";
    public static String TEXT_TYPE_MARKDOWN = "markdown";
    public static String TEXT_TYPE_SOURCE_CODE = "source_code";

    /**
     * Creates a CreateRequest with mandatory fields.
     *
     * @param content the text content to share
     * @param title   the title of the text
     * @return a new CreateRequest instance
     */
    public static CreateRequest of(String content, String title) {
        return new CreateRequest(content, title, TEXT_TYPE_PLAIN_TEXT, null, null, null, null);
    }

    public CreateRequest withTextType(String textType) {
        return new CreateRequest(content, title, textType, domain, customSlug, expireIn, password);
    }

    public CreateRequest withDomain(String domain) {
        return new CreateRequest(content, title, textType, domain, customSlug, expireIn, password);
    }

    public CreateRequest withExpireIn(Integer expireIn) {
        return new CreateRequest(content, title, textType, domain, customSlug, expireIn, password);
    }

    public CreateRequest withPassword(String password) {
        return new CreateRequest(content, title, textType, domain, customSlug, expireIn, password);
    }

    public CreateRequest withCustomSlug(String customSlug) {
        return new CreateRequest(content, title, textType, domain, customSlug, expireIn, password);
    }
}
