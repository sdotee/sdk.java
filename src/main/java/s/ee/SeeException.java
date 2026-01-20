/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: SeeException.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2025-12-05 16:08:46
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:01:35
 */

package s.ee;

import java.io.IOException;

/**
 * Exception thrown when SEE API operations fail.
 */
public class SeeException extends IOException {
    private final Response response;

    public SeeException(String message) {
        super(message);
        this.response = new Response(0, null, message);
    }

    public SeeException(Response errorResponse) {
        super(errorResponse.toString());
        this.response = errorResponse;
    }

    public SeeException(String message, Throwable cause) {
        super(message, cause);
        this.response = new Response(0, null, message);
    }

    public Response getResponse() {
        return response;
    }
}
