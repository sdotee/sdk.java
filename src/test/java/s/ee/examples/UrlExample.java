package s.ee.examples;

import s.ee.common.Config;
import s.ee.common.SeeException;
import s.ee.url.UrlClient;
import s.ee.url.model.CreateRequest;
import s.ee.url.model.DeleteRequest;
import s.ee.url.model.Response;
import s.ee.url.model.UpdateRequest;

/**
 * Example for URL Shortening operations.
 */
public class UrlExample {

    public static void main(String[] args) {
        // Configure the client
        // You can get API_KEY from https://s.ee/
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set SEE_API_KEY environment variable");
            return;
        }

        Config config = new Config("https://s.ee/api/v1", apiKey, 30);
        UrlClient client = new UrlClient(config);

        try {
            // 1. Create a shortened URL
            System.out.println("Creating shortened URL...");
            CreateRequest createRequest = CreateRequest.of("s.ee", "https://example.com")
                    .withTitle("Example URL");

            Response response = client.create(createRequest);
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
