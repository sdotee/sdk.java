/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: FileResponse.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:15
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:12
 */

package s.ee.file.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for file upload operations.
 */
public record FileResponse(
    @JsonProperty("code") int code,
    @JsonProperty("data") Data data,
    @JsonProperty("message") String message
) {

    public String getUrl() {
        return data != null ? data.url() : null;
    }

    public long getFileId() {
        return data != null ? data.fileId() : 0;
    }

    public record Data(
        @JsonProperty("delete") String delete,
        @JsonProperty("file_id") long fileId,
        @JsonProperty("filename") String filename,
        @JsonProperty("hash") String hash,
        @JsonProperty("height") int height,
        @JsonProperty("page") String page,
        @JsonProperty("path") String path,
        @JsonProperty("size") long size,
        @JsonProperty("storename") String storename,
        @JsonProperty("upload_status") int uploadStatus,
        @JsonProperty("url") String url,
        @JsonProperty("width") int width
    ) {}
}
