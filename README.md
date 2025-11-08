# SEE Java SDK

A lightweight Java SDK for the SEE URL shortening service, supporting URL shortening, domain management, and tag operations.

## Features

- **URL Shortening**: Create, update, and delete shortened URLs
- **Domain Management**: List available domains
- **Tag Management**: Retrieve and manage tags
- **CLI Tool**: Command-line interface for quick operations
- **Modern Java**: Built with Java 17 and optimized dependencies

## Requirements

- Java 17 or higher
- Maven 3.6+ (for building from source)

## Installation

### Maven

```xml
<dependency>
    <groupId>s.ee</groupId>
    <artifactId>see-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Build from Source

```bash
git clone https://github.com/yourusername/see-java-sdk.git
cd see-java-sdk
mvn clean package
```

## Quick Start

### URL Shortening

```java
// Initialize client
var config = new ShortenConfig("https://api.s.ee", "your-api-key", 30);
var client = new ShortenClient(config);

// Create shortened URL
var request = ShortenRequest.of("s.ee", "https://example.com");
var response = client.create(request);
System.out.println("Short URL: " + response.data().shortUrl());

// With custom options
var customRequest = ShortenRequest.of("s.ee", "https://example.com")
    .withCustomSlug("my-link")
    .withTitle("My Example Link");
```

### Domain Management

```java
var config = new DomainConfig("https://api.s.ee", "your-api-key", 30);
var client = new DomainClient(config);

var domains = client.get();
System.out.println("Available domains: " + domains.data());
```

### Tag Management

```java
var config = new TagConfig("https://api.s.ee", "your-api-key", 30);
var client = new TagClient(config);

var tags = client.get();
System.out.println("Available tags: " + tags.data());
```

## CLI Usage

Build the CLI tool:

```bash
mvn clean package
```

Use the CLI:

```bash
# Shorten a URL
java -jar target/see-cli.jar shorten --domain s.ee --url https://example.com

# With custom slug
java -jar target/see-cli.jar shorten --domain s.ee --url https://example.com --slug my-link
```

## Configuration

All clients accept a configuration object with:

- `baseUrl`: API base URL (default: `https://api.s.ee`)
- `apiKey`: Your API authentication key
- `timeout`: Request timeout in seconds (default: 30)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
