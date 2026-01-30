package s.ee.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base client for SEE API interactions.
 * Provides common HTTP operations and JSON handling.
 */
public abstract class Client {
    public static final short NO_ERROR = 200;
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    protected final OkHttpClient httpClient;
    protected final ObjectMapper objectMapper;
    private final Config config;

    /**
     * Constructs a client with the specified configuration.
     *
     * @param config the client configuration
     */
    public Client(Config config) {
        this.config = config;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(config.timeout(), TimeUnit.SECONDS)
            .readTimeout(config.timeout(), TimeUnit.SECONDS)
            .build();
        this.objectMapper = new ObjectMapper();
    }

    public String getBaseUrl() {
        return config.baseUrl();
    }

    public String getApiKey() {
        return config.apiKey();
    }

    protected <T, R> R post(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("POST", endpoint, requestBody, responseType);
    }

    protected <R> R postMultipart(String endpoint, File file, Class<R> responseType) throws SeeException {
        var body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.getName(),
                RequestBody.create(file, MediaType.parse("application/octet-stream")))
            .build();

        var request = buildRequest(endpoint)
            .post(body)
            .build();

        return executeRequest(request, responseType);
    }

    protected <T, R> R put(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("PUT", endpoint, requestBody, responseType);
    }

    protected <T, R> R get(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("GET", endpoint, requestBody, responseType);
    }

    protected <R> R delete(String endpoint, Class<R> responseType) throws SeeException {
        var request = buildRequest(endpoint).delete().build();
        return executeRequest(request, responseType);
    }

    protected <T, R> R delete(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("DELETE", endpoint, requestBody, responseType);
    }

    private Request.Builder buildRequest(String endpoint) {
        return new Request.Builder()
            .url(getBaseUrl() + endpoint)
            .addHeader("Authorization", getApiKey());
    }

    private <T, R> R executeWithBody(String method, String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        try {
            var jsonBody = objectMapper.writeValueAsString(requestBody);
            var body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);

            var builder = buildRequest(endpoint).addHeader("Content-Type", "application/json");
            var request = switch (method) {
                case "POST" -> builder.post(body).build();
                case "PUT" -> builder.put(body).build();
                case "DELETE" -> builder.delete(body).build();
                case "GET" -> builder.get().build();
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            return executeRequest(request, responseType);
        } catch (IOException e) {
            throw new SeeException("Failed to serialize request body or execute %s request".formatted(method), e);
        }
    }

    private <R> R executeRequest(Request request, Class<R> responseType) throws SeeException {
        try (var response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                var responseBody = response.body();
                var errorBody = responseBody != null ? responseBody.string() : "No error body";
                var errorResponse = objectMapper.readValue(errorBody, Response.class);
                throw new SeeException(errorResponse);
            }

            var responseBody = response.body();
            if (responseBody == null) {
                throw new SeeException("Response body is null");
            }

            var body = responseBody.string();
            return objectMapper.readValue(body, responseType);
        } catch (IOException e) {
            throw new SeeException("Failed to execute request or parse response", e);
        }
    }
}

