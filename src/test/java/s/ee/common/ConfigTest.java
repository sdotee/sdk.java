/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 *
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 *
 * File: ConfigTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-07-31 15:14:19
 *
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-07-31 15:32:21
 */

package s.ee.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigTest {
    @Test
    void validatesRequiredValues() {
        assertThrows(IllegalArgumentException.class, () -> new Config("https://s.ee/api/v1", " ", 5));
        assertThrows(IllegalArgumentException.class, () -> new Config("not-a-url", "key", 5));
        assertThrows(IllegalArgumentException.class, () -> new Config("https://s.ee/api/v1", "key", 0));
    }

    @Test
    void normalizesTrailingSlash() {
        var config = new Config("https://s.ee/api/v1/", "key", 5);

        assertEquals("https://s.ee/api/v1", config.baseUrl());
    }
}
