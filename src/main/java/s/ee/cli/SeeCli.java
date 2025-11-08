package s.ee.cli;

import s.ee.SeeException;
import s.ee.domain.DomainClient;
import s.ee.domain.config.DomainConfig;
import s.ee.tag.TagClient;
import s.ee.tag.config.TagConfig;
import s.ee.urlshorten.ShortenClient;
import s.ee.urlshorten.config.ShortenConfig;
import s.ee.urlshorten.model.DeleteRequest;
import s.ee.urlshorten.model.ShortenRequest;
import s.ee.urlshorten.model.UpdateRequest;

import java.util.Arrays;
import java.util.List;

/**
 * Command-line interface for SEE API operations.
 * Provides simple commands for URL shortening, domain, and tag management.
 *
 * <p>This class uses Java 17 features including records, pattern matching,
 * and enhanced switch expressions for cleaner, more maintainable code.</p>
 */
public class SeeCli {
    private static final String DEFAULT_BASE_URL = "https://s.ee/api";
    private static final int DEFAULT_TIMEOUT = 10;

    private final String apiKey;
    private final String baseUrl;
    private final int timeout;

    public SeeCli(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL, DEFAULT_TIMEOUT);
    }

    public SeeCli(String apiKey, String baseUrl, int timeout) {
        // Validate API key
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key is required");
        }
        this.apiKey = apiKey;

        // Load baseUrl from environment variable if not provided
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            this.baseUrl = System.getenv("SEE_API_BASE_URL");
        } else {
            this.baseUrl = baseUrl;
        }

        // Set timeout
        this.timeout = timeout;
    }

    /**
     * Main method for command-line execution.
     * Uses sealed interfaces and records for type-safe command dispatch.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        var apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("Error: SEE_API_KEY environment variable is not set");
            System.exit(1);
        }

        var baseUrl = System.getenv("SEE_API_BASE_URL");
        var cli = new SeeCli(apiKey, baseUrl, DEFAULT_TIMEOUT);
        var commandName = args[0].toLowerCase();

        try {
            var command = switch (commandName) {
                case "shorten" -> new ShortenCommand();
                case "delete" -> new DeleteCommand();
                case "update" -> new UpdateCommand();
                case "domains" -> new ListCommand("domains");
                case "tags" -> new ListCommand("tags");
                default -> {
                    System.err.println("Error: Unknown command '" + commandName + "'");
                    printUsage();
                    System.exit(1);
                    yield null; // Unreachable, but required for compilation
                }
            };

            if (args.length < command.requiredArgs()) {
                System.err.println("Error: " + command.errorMessage());
                printUsage();
                System.exit(1);
            }

            command.execute(cli, args);
        } catch (SeeException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar see-cli.jar <command> [args...]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  shorten <domain> <target-url> [custom-slug]");
        System.out.println("      Create a shortened URL");
        System.out.println();
        System.out.println("  delete <domain> <slug>");
        System.out.println("      Delete a shortened URL");
        System.out.println();
        System.out.println("  update <domain> <slug> <new-target-url>");
        System.out.println("      Update a shortened URL");
        System.out.println();
        System.out.println("  domains");
        System.out.println("      List available domains");
        System.out.println();
        System.out.println("  tags");
        System.out.println("      List available tags");
        System.out.println();
        System.out.println("Environment variables:");
        System.out.println("  SEE_API_KEY - API key for authentication (required)");
    }

    /**
     * Creates a ShortenClient with current configuration.
     *
     * @return configured ShortenClient instance
     */
    private ShortenClient createShortenClient() {
        return new ShortenClient(new ShortenConfig(baseUrl, apiKey, timeout));
    }

    /**
     * Creates a shortened URL.
     *
     * @param domain    the domain for the shortened URL
     * @param targetUrl the target URL to shorten
     * @return the shortened URL
     * @throws SeeException if the operation fails
     */
    public String shorten(String domain, String targetUrl) throws SeeException {
        return createShortenClient()
                .create(ShortenRequest.of(domain, targetUrl))
                .getShortUrl();
    }

    /**
     * Creates a shortened URL with custom slug.
     *
     * @param domain     the domain for the shortened URL
     * @param targetUrl  the target URL to shorten
     * @param customSlug the custom slug
     * @return the shortened URL
     * @throws SeeException if the operation fails
     */
    public String shortenWithSlug(String domain, String targetUrl, String customSlug) throws SeeException {
        return createShortenClient()
                .create(ShortenRequest.of(domain, targetUrl).withCustomSlug(customSlug))
                .getShortUrl();
    }

    /**
     * Creates a shortened URL with title and tags.
     *
     * @param domain    the domain for the shortened URL
     * @param targetUrl the target URL to shorten
     * @param title     the title for the shortened URL
     * @param tagIds    the tag IDs
     * @return the shortened URL
     * @throws SeeException if the operation fails
     */
    public String shortenWithTitleAndTags(String domain, String targetUrl, String title, List<Integer> tagIds) throws SeeException {
        return createShortenClient()
                .create(ShortenRequest.of(domain, targetUrl)
                        .withTitle(title)
                        .withTagIds(tagIds))
                .getShortUrl();
    }

    /**
     * Deletes a shortened URL.
     *
     * @param domain the domain of the shortened URL
     * @param slug   the slug to delete
     * @return true if deletion was successful
     * @throws SeeException if the operation fails
     */
    public boolean deleteShortUrl(String domain, String slug) throws SeeException {
        return createShortenClient()
                .delete(new DeleteRequest(domain, slug))
                .code() == 200;
    }

    /**
     * Updates a shortened URL.
     *
     * @param domain    the domain of the shortened URL
     * @param slug      the slug to update
     * @param targetUrl the new target URL
     * @return true if update was successful
     * @throws SeeException if the operation fails
     */
    public boolean updateShortUrl(String domain, String slug, String targetUrl) throws SeeException {
        return createShortenClient()
                .update(UpdateRequest.of(domain, slug).withTargetUrl(targetUrl))
                .code() == 200;
    }

    /**
     * Lists available domains.
     *
     * @return list of available domains
     * @throws SeeException if the operation fails
     */
    public List<String> listDomains() throws SeeException {
        return Arrays.asList(
                new DomainClient(new DomainConfig(baseUrl, apiKey, timeout))
                        .get()
                        .getDomains()
        );
    }

    /**
     * Lists available tags.
     *
     * @return list of tag names
     * @throws SeeException if the operation fails
     */
    public List<String> listTags() throws SeeException {
        return Arrays.stream(
                        new TagClient(new TagConfig(baseUrl, apiKey, timeout))
                                .get()
                                .getTags()
                )
                .map(tag -> tag.name())
                .toList();
    }

    /**
     * Command execution record for cleaner main method implementation.
     */
    private sealed interface Command permits ShortenCommand, DeleteCommand, UpdateCommand, ListCommand {
        void execute(SeeCli cli, String[] args) throws SeeException;

        int requiredArgs();

        String errorMessage();
    }

    private record ShortenCommand() implements Command {
        @Override
        public void execute(SeeCli cli, String[] args) throws SeeException {
            var domain = args[1];
            var targetUrl = args[2];
            var customSlug = args.length > 3 ? args[3] : null;

            var shortUrl = customSlug != null
                    ? cli.shortenWithSlug(domain, targetUrl, customSlug)
                    : cli.shorten(domain, targetUrl);
            System.out.println("Shortened URL: " + shortUrl);
        }

        @Override
        public int requiredArgs() {
            return 3;
        }

        @Override
        public String errorMessage() {
            return "shorten requires domain and target URL";
        }
    }

    private record DeleteCommand() implements Command {
        @Override
        public void execute(SeeCli cli, String[] args) throws SeeException {
            var success = cli.deleteShortUrl(args[1], args[2]);
            System.out.println(success ? "Deleted successfully" : "Delete failed");
        }

        @Override
        public int requiredArgs() {
            return 3;
        }

        @Override
        public String errorMessage() {
            return "delete requires domain and slug";
        }
    }

    private record UpdateCommand() implements Command {
        @Override
        public void execute(SeeCli cli, String[] args) throws SeeException {
            var success = cli.updateShortUrl(args[1], args[2], args[3]);
            System.out.println(success ? "Updated successfully" : "Update failed");
        }

        @Override
        public int requiredArgs() {
            return 4;
        }

        @Override
        public String errorMessage() {
            return "update requires domain, slug, and new target URL";
        }
    }

    private record ListCommand(String type) implements Command {
        @Override
        public void execute(SeeCli cli, String[] args) throws SeeException {
            var items = type.equals("domains") ? cli.listDomains() : cli.listTags();
            System.out.println("Available " + type + ":");
            items.forEach(item -> System.out.println("  - " + item));
        }

        @Override
        public int requiredArgs() {
            return 1;
        }

        @Override
        public String errorMessage() {
            return "";
        }
    }
}
