package s.ee.examples;

import s.ee.Config;
import s.ee.SeeException;
import s.ee.model.DeleteRequest;
import s.ee.model.DomainResponse;
import s.ee.text.TextClient;
import s.ee.text.model.TextCreateRequest;
import s.ee.text.model.TextResponse;
import s.ee.text.model.TextUpdateRequest;

/**
 * Example for Text operations.
 */
public class TextExample {

    public static void main(String[] args) {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set SEE_API_KEY environment variable");
            return;
        }

        Config config = new Config("https://s.ee/api/v1", apiKey, 30);
        TextClient client = new TextClient(config);

        try {
            // 1. Get available domains
            System.out.println("Fetching available domains...");
            DomainResponse domains = client.getDomains();
            for (String domain : domains.getDomains()) {
                System.out.println("- " + domain);
            }

            // 2. Create text sharing
            System.out.println("\nCreating text sharing...");
            TextCreateRequest createRequest = new TextCreateRequest("Hello, World!", "My First Text");
            TextResponse response = client.create(createRequest);

            System.out.println("Text created: " + response.getShortUrl());
            System.out.println("Slug: " + response.getSlug());

            // 3. Update text sharing
            System.out.println("\nUpdating text sharing...");
            // Start from valid request, assuming proper builder or constructor usage
            // The generated TextUpdateRequest might need fields
            TextUpdateRequest updateRequest = new TextUpdateRequest(
                "Updated content",
                "s.ee",
                response.getSlug(),
                 "Updated Title"
            );
            client.update(updateRequest);
            System.out.println("Text updated successfully");

            // 4. Delete text sharing
            System.out.println("\nDeleting text sharing...");
            client.delete(DeleteRequest.of("s.ee", response.getSlug()));
            System.out.println("Text deleted successfully");

        } catch (SeeException e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
