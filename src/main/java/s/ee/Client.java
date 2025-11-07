package s.ee;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import s.ee.urlshorten.exception.UrlShortenException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class Client {
    public static final short NO_ERROR = 200;

    private static final String ENDPOINT = "/v1/shorten";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    protected final OkHttpClient httpClient;
    protected final ObjectMapper objectMapper;
    private final Config config;

    public Client(Config config) {
        this.config = config;

        // Set up OkHttpClient and configure timeouts
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getTimeout(), TimeUnit.SECONDS)
                .build();

        // Set up ObjectMapper
        this.objectMapper = new ObjectMapper();
    }

    public String getBaseUrl() {
        return config.getBaseUrl();
    }

    public String getApiKey() {
        return config.getApiKey();
    }

    /**
     * Execute a POST request with JSON body
     *
     * @param requestBody  The request object to be serialized to JSON
     * @param responseType The expected response class type
     * @param <T>          Request type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    protected <T, R> R post(T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("POST", requestBody, responseType);
    }

    /**
     * Execute a PUT request with JSON body
     *
     * @param requestBody  The request object to be serialized to JSON
     * @param responseType The expected response class type
     * @param <T>          Request type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    protected <T, R> R put(T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("PUT", requestBody, responseType);
    }

    /**
     * Execute a DELETE request
     *
     * @param responseType The expected response class type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    protected <R> R delete(Class<R> responseType) throws SeeException {
        Request request = buildRequest(ENDPOINT)
                .delete()
                .build();

        return executeRequest(request, responseType);
    }

    /**
     * Execute a DELETE request with JSON body
     *
     * @param requestBody  The request object to be serialized to JSON
     * @param responseType The expected response class type
     * @param <T>          Request type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    protected <T, R> R delete(T requestBody, Class<R> responseType) throws SeeException {
        return executeWithBody("DELETE", requestBody, responseType);
    }

    /**
     * Build a base request with common headers
     *
     * @param endpoint The API endpoint
     * @return Request.Builder with base configuration
     */
    private Request.Builder buildRequest(String endpoint) {
        return new Request.Builder()
                .url(getBaseUrl() + endpoint)
                .addHeader("Authorization", getApiKey());
    }

    /**
     * Execute an HTTP request with JSON body
     *
     * @param method       HTTP method (POST, PUT, DELETE)
     * @param requestBody  The request object to be serialized to JSON
     * @param responseType The expected response class type
     * @param <T>          Request type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    private <T, R> R executeWithBody(String method, T requestBody, Class<R> responseType) throws SeeException {
        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);

            Request.Builder builder = buildRequest(ENDPOINT)
                    .addHeader("Content-Type", "application/json");

            Request request = switch (method) {
                case "POST" -> builder.post(body).build();
                case "PUT" -> builder.put(body).build();
                case "DELETE" -> builder.delete(body).build();
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            return executeRequest(request, responseType);
        } catch (IOException e) {
            throw new SeeException("Failed to serialize request body or execute " + method + " request", e);
        }
    }

    /**
     * Execute the HTTP request and handle the response
     *
     * @param request      The OkHttp request object
     * @param responseType The expected response class type
     * @param <R>          Response type
     * @return Deserialized response object
     * @throws UrlShortenException If the request fails or response cannot be parsed
     */
    private <R> R executeRequest(Request request, Class<R> responseType) throws SeeException {
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                var errorResponse = objectMapper.readValue(errorBody, Response.class);
                throw new SeeException(errorResponse);
            }

            if (response.body() == null) {
                throw new SeeException("Response body is null");
            }

            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, responseType);
        } catch (IOException e) {
            throw new SeeException("Failed to execute request or parse response", e);
        }
    }
}
