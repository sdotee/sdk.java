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

import s.ee.common.Client;
import s.ee.common.Config;
import s.ee.common.SeeException;
import s.ee.common.DomainResponse;
import s.ee.file.model.DeleteResponse;
import s.ee.file.model.FileResponse;
import s.ee.file.model.HistoryResponse;
import s.ee.file.model.PrivateDownloadUrlResponse;
import s.ee.file.model.LargeFileModels;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
        return upload(file, null, null);
    }

    /**
     * Uploads a file with options.
     *
     * @param file       the file to upload
     * @param domain     the domain to be used for the short link
     * @param customSlug preferred custom slug
     * @return the file response
     * @throws SeeException if the operation fails
     */
    public FileResponse upload(File file, String domain, String customSlug) throws SeeException {
        Map<String, String> params = new HashMap<>();
        if (domain != null) params.put("domain", domain);
        if (customSlug != null) params.put("custom_slug", customSlug);
        return postMultipart("/file/upload", file, params, FileResponse.class);
    }

    /**
     * Deletes a file.
     *
     * @param hash the file hash
     * @return the response
     * @throws SeeException if the operation fails
     */
    public DeleteResponse delete(String hash) throws SeeException {
        return get("/file/delete/" + pathSegment(hash), DeleteResponse.class);
    }

    /**
     * Retrieves available file domains.
     *
     * @return the file domain response
     * @throws SeeException if the operation fails
     */
    public DomainResponse getDomains() throws SeeException {
        return get("/file/domains", DomainResponse.class);
    }

    public HistoryResponse getHistory(Integer page) throws SeeException {
        return get("/files", Map.of("page", page == null ? 1 : page), HistoryResponse.class);
    }

    public PrivateDownloadUrlResponse getPrivateDownloadUrl(long fileId) throws SeeException {
        return get("/file/private/download-url", Map.of("file_id", fileId), PrivateDownloadUrlResponse.class);
    }

    public LargeFileModels.CreateResponse createLargeFileUpload(LargeFileModels.CreateRequest request)
        throws SeeException {
        return post("/file/large-file/create", request, LargeFileModels.CreateResponse.class);
    }

    public LargeFileModels.ProgressResponse getLargeFileUploadProgress(String uploadId) throws SeeException {
        return get("/file/large-file/progress", Map.of("upload_id", uploadId),
            LargeFileModels.ProgressResponse.class);
    }

    public long getLargeFileUploadOffset(String uploadId) throws SeeException {
        var headers = executeForHeaders("HEAD", tusEndpoint(uploadId), null, tusHeaders());
        return parseUploadOffset(headers.get("Upload-Offset"));
    }

    public long uploadLargeFileChunk(String uploadId, long offset, byte[] chunk) throws SeeException {
        var headers = new HashMap<>(tusHeaders());
        headers.put("Upload-Offset", Long.toString(offset));
        headers.put("Content-Type", "application/offset+octet-stream");
        var body = RequestBody.create(chunk, MediaType.get("application/offset+octet-stream"));
        var responseHeaders = executeForHeaders("PATCH", tusEndpoint(uploadId), body, headers);
        return parseUploadOffset(responseHeaders.get("Upload-Offset"));
    }

    public void deleteLargeFileUpload(String uploadId) throws SeeException {
        executeForHeaders("DELETE", tusEndpoint(uploadId), null, tusHeaders());
    }

    public s.ee.common.Response cancelLargeFileUpload(String uploadId) throws SeeException {
        return delete("/file/large-file/cancel", new LargeFileModels.UploadRequest(uploadId),
            s.ee.common.Response.class);
    }

    public LargeFileModels.CompleteResponse completeLargeFileUpload(String uploadId) throws SeeException {
        return post("/file/large-file/complete", new LargeFileModels.UploadRequest(uploadId),
            LargeFileModels.CompleteResponse.class);
    }

    private String tusEndpoint(String uploadId) {
        return "/file/large-file-tus/" + pathSegment(uploadId);
    }

    private Map<String, String> tusHeaders() {
        return Map.of("Tus-Resumable", "1.0.0");
    }

    private long parseUploadOffset(String value) throws SeeException {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new SeeException("Missing or invalid Upload-Offset response header", e);
        }
    }
}
