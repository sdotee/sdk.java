/**
 * Copyright (c) 2025-2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: FileClientTest.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:34:10
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:02:38
 */

package s.ee.file;

import org.junit.jupiter.api.*;
import s.ee.BaseIntegrationTest;
import s.ee.common.SeeException;
import s.ee.file.model.FileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileClientTest extends BaseIntegrationTest {

    private static FileClient client;
    private static File testFile;
    private static String uploadedFileHash;

    @BeforeAll
    static void setUp() throws IOException {
        client = new FileClient(createConfig());

        // Create a temporary test file
        testFile = File.createTempFile("test-upload", ".txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, World! This is a test file.");
            writer.write("The timesamp is: " + System.currentTimeMillis());
        }
    }

    @AfterAll
    static void tearDown() {
        if (testFile != null && testFile.exists()) {
            boolean ignored = testFile.delete();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test uploading a file")
    void testUpload() throws SeeException {
        // Skip if no API key provided (to avoid failures in local env without creds)
        if (getApiKey() == null || getApiKey().isEmpty()) {
            return;
        }

        FileResponse response = client.upload(testFile);
        assertNotNull(response);
        assertEquals(200, response.code());
        assertNotNull(response.data());

        uploadedFileHash = String.valueOf(response.data().hash());

        System.out.println("Uploaded file URL: " + response.data().url());
    }

    @Test
    @Order(2)
    @DisplayName("Test getting file domains")
    void testGetDomains() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty()) {
            return;
        }

        var response = client.getDomains();
        assertNotNull(response);
        assertEquals(200, response.code());
        assertNotNull(response.getDomains());
    }

    @Test
    @Order(3)
    @DisplayName("Test deleting a file")
    void testDelete() throws SeeException {
        if (getApiKey() == null || getApiKey().isEmpty() || uploadedFileHash.isEmpty()) {
            return;
        }

        var response = client.delete(uploadedFileHash);
        assertNotNull(response);
        // Assuming success returns 200 or 0, check Response generic fields if needed
    }
}
