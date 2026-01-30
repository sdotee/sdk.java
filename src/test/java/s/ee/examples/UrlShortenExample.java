package s.ee.examples;

import s.ee.Config;
import s.ee.SeeException;
import s.ee.model.DeleteRequest;
import s.ee.urlshorten.ShortenClient;
import s.ee.urlshorten.model.ShortenRequest;
import s.ee.urlshorten.model.ShortenResponse;
import s.ee.urlshorten.model.UpdateRequest;

/**
 * Example for URL Shortening operations.
 */
public class UrlShortenExample {

    public static void main(String[] args) {
        // Configure the client
        // You can get API_KEY from https://s.ee/
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set SEE_API_KEY environment variable");
            return;
        }

        Config config = new Config("https://s.ee/api/v1", apiKey, 30);
        ShortenClient client = new ShortenClient(config);

        try {
            // 1. Create a shortened URL
            System.out.println("Creating shortened URL...");
            ShortenRequest createRequest = ShortenRequest.of("s.ee", "https://example.com")
                .withTitle("Example URL");

            ShortenResponse response = client.create(createRequest);
            System.out.println("Shortened URL: " + response.getShortUrl());
            System.out.println("Domain: " + "s.ee");
            System.out.println("Slug: " + response.getSlug());

            // 2. Update the shortened URL
            System.out.println("\nUpdating shortened URL...");
            UpdateRequest updateRequest = UpdateRequest.of("s.ee", response.getSlug())
                .withTitle("Updated Example URL");

            client.update(updateRequest);
            System.out.println("URL updated successfully");

            // 3. Delete the shortened URL
            System.out.println("\nDeleting shortened URL...");
            DeleteRequest deleteRequest = DeleteRequest.of("s.ee", response.getSlug());
            client.delete(deleteRequest);
            System.out.println("URL deleted successfully");

        } catch (SeeException e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
