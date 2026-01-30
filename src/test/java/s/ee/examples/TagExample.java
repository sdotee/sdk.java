package s.ee.examples;

import s.ee.common.Config;
import s.ee.tag.TagClient;
import s.ee.tag.model.TagResponse;

/**
 * Example for Tag operations.
 */
public class TagExample {

    public static void main(String[] args) {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set SEE_API_KEY environment variable");
            return;
        }

        TagClient client = new TagClient(Config.builder().apiKey(apiKey).build());
        try {
            // 1. Get available tags
            System.out.println("Fetching available tags...");
            TagResponse tags = client.get();
            System.out.println("Available tags: " + tags.getTags());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
