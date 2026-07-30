package s.ee.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import s.ee.url.model.UsageResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
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
        this.httpClient = new OkHttpClient.Builder().connectTimeout(config.timeout(), TimeUnit.SECONDS).readTimeout(config.timeout(), TimeUnit.SECONDS).build();
        this.objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String getBaseUrl() {
        return config.baseUrl();
    }

    public String getApiKey() {
        return config.apiKey();
    }

    protected static String pathSegment(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    protected <T, R> R post(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("POST", endpoint, requestBody, responseType);
    }

    protected <R> R postMultipart(String endpoint, File file, Class<R> responseType) throws SeeException {
        return postMultipart(endpoint, file, Collections.emptyMap(), responseType);
    }

    protected <R> R postMultipart(String endpoint, File file, Map<String, String> params, Class<R> responseType) throws SeeException {
        var bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("application/octet-stream")));

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }

        var body = bodyBuilder.build();

        var request = buildRequest(endpoint).post(body).build();

        return executeRequest(request, responseType);
    }

    protected <T, R> R put(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("PUT", endpoint, requestBody, responseType);
    }

    protected <T, R> R get(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return get(endpoint, responseType);
    }

    protected <R> R get(String endpoint, Class<R> responseType) throws SeeException {
        var request = buildRequest(endpoint).get().build();
        return executeRequest(request, responseType);
    }

    protected <R> R get(String endpoint, Map<String, ?> queryParams, Class<R> responseType) throws SeeException {
        var urlBuilder = HttpUrl.get(getBaseUrl() + endpoint).newBuilder();
        if (queryParams != null) {
            queryParams.forEach((name, value) -> {
                if (value != null) {
                    urlBuilder.addQueryParameter(name, String.valueOf(value));
                }
            });
        }
        var request = new Request.Builder().url(urlBuilder.build()).addHeader("Authorization", getApiKey()).get().build();
        return executeRequest(request, responseType);
    }

    protected <R> R delete(String endpoint, Class<R> responseType) throws SeeException {
        var request = buildRequest(endpoint).delete().build();
        return executeRequest(request, responseType);
    }

    protected <T, R> R delete(String endpoint, T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("DELETE", endpoint, requestBody, responseType);
    }

    protected Headers executeForHeaders(String method, String endpoint, RequestBody body,
                                        Map<String, String> headers) throws SeeException {
        var builder = buildRequest(endpoint);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        try (var response = httpClient.newCall(builder.method(method, body).build()).execute()) {
            if (!response.isSuccessful()) {
                throw createHttpException(response);
            }
            return response.headers();
        } catch (IOException e) {
            throw new SeeException("Failed to execute %s request".formatted(method), e);
        }
    }

    private Request.Builder buildRequest(String endpoint) {
        return new Request.Builder().url(getBaseUrl() + endpoint).addHeader("Authorization", getApiKey());
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
                throw createHttpException(response);
            }

            if (responseType == Void.class) {
                return null;
            }

            var responseBody = response.body();
            if (responseBody == null) {
                throw new SeeException("Response body is null");
            }

            var body = responseBody.string();
            if (body == null || body.isEmpty()) {
                throw new SeeException("Response body is empty");
            }

            return objectMapper.readValue(body, responseType);
        } catch (JsonParseException e) {
            throw new SeeException("Failed to parse JSON response", e);
        } catch (IOException e) {
            throw new SeeException("Failed to execute request or parse response", e);
        }
    }

    private SeeException createHttpException(okhttp3.Response response) throws IOException {
        var responseBody = response.body();
        var errorBody = responseBody != null ? responseBody.string() : "";
        try {
            return new SeeException(objectMapper.readValue(errorBody, Response.class));
        } catch (IOException ignored) {
            return new SeeException("HTTP %d: %s".formatted(response.code(),
                errorBody.isBlank() ? response.message() : errorBody));
        }
    }

    /**
     * Get the usage of the short link service.
     *
     * @return the usage response
     * @throws SeeException if the operation fails
     */
    public UsageResponse getUsage() throws SeeException {
        return get("/usage", UsageResponse.class);
    }
}
