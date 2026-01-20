# Multi-stage build for SEE Java SDK CLI
# Stage 1: Build the application
FROM maven:3-amazoncorretto-17-alpine AS builder

# Set working directory
WORKDIR /build

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies (cached if pom.xml hasn't changed)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application and create fat JAR
RUN mvn clean package -DskipTests -B

# Stage 2: Create minimal runtime image
FROM amazoncorretto:17-alpine

# Set metadata
LABEL maintainer="SEE SDK Team"
LABEL description="SEE Java SDK CLI - content sharing service command-line tool"
LABEL version="1.0.0"

# Install required packages
RUN apk add --no-cache \
    bash \
    curl \
    && rm -rf /var/cache/apk/*

# Create non-root user for security
RUN addgroup -g 1000 seeuser && \
    adduser -D -u 1000 -G seeuser seeuser

# Set working directory
WORKDIR /app

# Copy the fat JAR from builder stage
COPY --from=builder /build/target/see-cli.jar /app/see-cli.jar

# Change ownership to non-root user
RUN chown -R seeuser:seeuser /app

# Switch to non-root user
USER seeuser

# Set environment variables with defaults
ENV SEE_API_KEY="" \
    SEE_API_BASE_URL="https://s.ee/api" \
    JAVA_OPTS="-Xmx256m -Xms128m"

# Add healthcheck (optional, checks if JAR is accessible)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -version || exit 1

# Set entrypoint to run the CLI
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/see-cli.jar \"$@\"", "--"]

# Default command shows help
CMD []
