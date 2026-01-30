package s.ee.examples;

import s.ee.common.Config;
import s.ee.common.DomainResponse;
import s.ee.common.SeeException;
import s.ee.text.TextClient;
import s.ee.text.model.CreateRequest;
import s.ee.text.model.DeleteRequest;
import s.ee.text.model.Response;
import s.ee.text.model.UpdateRequest;

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

            assert domains.getDomains().length != 0;
            var defaultDomain = domains.getDomains()[0];

            // 2. Create text sharing
            System.out.println("\nCreating text sharing...");
            var createRequest = CreateRequest.of("Hello, World!", "My First Text").withDomain(defaultDomain);
            Response response = client.create(createRequest);

            System.out.println("Text created: " + response.getShortUrl());
            System.out.println("Slug: " + response.getSlug());

            // 3. Update text sharing
            System.out.println("\nUpdating text sharing...");
            // Start from valid request, assuming proper builder or constructor usage
            // The generated TextUpdateRequest might need fields
            var updateRequest = UpdateRequest.of(defaultDomain, response.getSlug(), "Updated content", "Updated Title");
            client.update(updateRequest);
            System.out.println("Text updated successfully");

            // 4. Delete text sharing
            System.out.println("\nDeleting text sharing...");
            client.delete(DeleteRequest.of(defaultDomain, response.getSlug()));
            System.out.println("Text deleted successfully");

        } catch (SeeException e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
