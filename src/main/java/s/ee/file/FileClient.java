/**
 * Copyright (c) 2026 S.EE Development Team,. Ltd
 * <p>
 * This source code is licensed under the MIT License,
 * which is located in the LICENSE file in the source tree's root directory.
 * <p>
 * File: FileClient.java
 * Author: S.EE Development Team <dev@s.ee>
 * File Created: 2026-01-20 11:33:15
 * <p>
 * Modified By: S.EE Development Team <dev@s.ee>
 * Last Modified: 2026-01-20 12:04:34
 */

package s.ee.file;

import s.ee.Client;
import s.ee.Config;
import s.ee.SeeException;
import s.ee.domain.model.DomainResponse;
import s.ee.file.model.DeleteResponse;
import s.ee.file.model.FileResponse;

import java.io.File;

/**
 * Client for File operations.
 */
public class FileClient extends Client {

    public FileClient(Config config) {
        super(config);
    }

    /**
     * Uploads a file.
     *
     * @param file the file to upload
     * @return the file response
     * @throws SeeException if the operation fails
     */
    public FileResponse upload(File file) throws SeeException {
        return postMultipart("/file/upload", file, FileResponse.class);
    }

    /**
     * Deletes a file.
     *
     * @param hash the file hash
     * @return the response
     * @throws SeeException if the operation fails
     */
    public DeleteResponse delete(String hash) throws SeeException {
        return get("/file/delete/" + hash, null, DeleteResponse.class);
    }

    /**
     * Retrieves available file domains.
     *
     * @return the file domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse getDomains() throws SeeException {
        return get("/file/domains", null, DomainResponse.class);
    }
}
