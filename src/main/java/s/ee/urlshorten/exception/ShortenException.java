/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: ShortenException.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-11-07 17:07:27
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:43
 */

package s.ee.urlshorten.exception;

import s.ee.Response;
import s.ee.SeeException;

/**
 * Exception thrown when URL shortening operations fail.
 */
public class ShortenException extends SeeException {
    private final Response response;

    public ShortenException(String message) {
        super(message);
        this.response = Response.builder().message(message).build();
    }

    public ShortenException(Response errorResponse) {
        super(errorResponse.toString());
        this.response = errorResponse;
    }

    public ShortenException(String message, Throwable cause) {
        super(message, cause);
        this.response = Response.builder().message(message).build();
    }
}
