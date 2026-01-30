# SEE Java SDK

A lightweight Java SDK for the SEE content sharing service, supporting URL shortening, text sharing, file uploads, domain management, and tag operations.

## Features

- **URL Shortening**: Create, update, and delete shortened URLs
- **File Management**: Upload and manage files
- **Text Sharing**: Create and manage text snippets
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
    <version>1.1.0</version>
</dependency>
```

### Build from Source

```bash
git clone https://github.com/sdotee/sdk.java see-java-sdk -b main
cd see-java-sdk
mvn clean package
```

## Quick Start

Detailed usage examples can be found in the `src/test/java/s/ee/examples/` directory.

## Configuration

All clients accept a configuration object with:

- `baseUrl`: API base URL (default: `https://s.ee/api/v1`)
- `apiKey`: Your API authentication key
- `timeout`: Request timeout in seconds (default: 30)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
