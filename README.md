# SEE Java SDK

A lightweight Java SDK for the SEE content sharing service, supporting URL shortening, text sharing, file uploads, domain management, and tag operations.

## Features

- **URL Shortening**: Create, update, and delete shortened URLs
- **URL Analytics**: List links and retrieve visit statistics
- **File Management**: Multipart upload, private download URLs, history, and TUS large-file uploads
- **Text Sharing**: Create, update, delete, and list text snippets
- **Bio Pages and QR Codes**: Create, update/list, and delete resources
- **Token Validation**: Validate API tokens
- **Domain Management**: List available domains
- **Tag Management**: Retrieve and manage tags
- **Modern Java**: Java 17 baseline with records, immutable collections, and switch expressions

## Requirements

- Java 17 or higher
- Maven 3.6+ (for building from source)

## Installation

### Maven

```xml
<dependency>
    <groupId>dev.s.ee</groupId>
    <artifactId>see-java-sdk</artifactId>
    <version>1.3.0</version>
</dependency>
```

### Build from Source

```bash
git clone https://github.com/sdotee/sdk.java see-java-sdk -b main
cd see-java-sdk
mvn clean package
```

## Quick Start

```java
Config config = new Config("YOUR_API_KEY");
UrlClient urls = new UrlClient(config);

var created = urls.create(CreateRequest.of("s.ee", "https://example.com"));
var statistics = urls.getVisitStatistics("s.ee", created.data().slug(), "monthly");
```

Available clients:

| Client         | Operations                                                                      |
| -------------- | ------------------------------------------------------------------------------- |
| `UrlClient`    | create/update/delete, simple mode, domains, history, visit statistics, usage    |
| `TextClient`   | create/update/delete, domains, history                                          |
| `FileClient`   | multipart upload/delete, domains/history, private URL, large-file/TUS lifecycle |
| `TagClient`    | list tags                                                                       |
| `BioClient`    | create/update/delete, history                                                   |
| `QrcodeClient` | create/delete, history                                                          |
| `TokenClient`  | token validation                                                                |

Large uploads use `createLargeFileUpload`, `getLargeFileUploadOffset`,
`uploadLargeFileChunk`, and `completeLargeFileUpload`. The chunk method returns
the server-confirmed offset for resumable uploads.

## Configuration

All clients accept a configuration object with:

- `baseUrl`: API base URL (default: `https://s.ee/api/v1`)
- `apiKey`: Your API authentication key
- `timeout`: Request timeout in seconds (default: 5); use a larger value for large uploads

Configuration is validated when constructed. The API key and base URL must not
be blank, the base URL must be an absolute HTTP(S) URL without a query or
fragment, and the timeout must be greater than zero.

## Testing

```bash
mvn clean verify
```

Contract tests use a local mock server. Live integration tests are skipped unless
`SEE_API_KEY` is set; `SEE_API_BASE_URL`, `SEE_TEST_DOMAIN`, and
`SEE_TEST_TIMEOUT` can override their defaults.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
