/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: Response.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-12-05 16:08:46
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-30 18:46:50
 */

package s.ee.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Standard API response structure.
 *
 * @param code    response code
 * @param data    response data
 * @param message response message
 */
public record Response(@JsonProperty("code") int code, @JsonProperty("data") String data,
                       @JsonProperty("message") String message) {

    public Response {
        if (data == null) {
            data = "";
        }

        if (message == null) {
            message = "";
        }
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder {
        private int code;
        private String data;
        private String message;

        public ResponseBuilder() {
            this.code = 0;
            this.data = "";
            this.message = "";
        }

        public ResponseBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder data(String data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Response build() {
            return new Response(code, data, message);
        }
    }
}
