package s.ee.examples;

import s.ee.common.Config;
import s.ee.common.DomainResponse;
import s.ee.common.SeeException;
import s.ee.file.FileClient;
import s.ee.file.model.FileResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Example for File operations.
 */
public class FileExample {

    public static void main(String[] args) throws IOException {
        String apiKey = System.getenv("SEE_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set SEE_API_KEY environment variable");
            return;
        }

        // Initialize FileClient
        FileClient client = new FileClient(Config.builder().apiKey(apiKey).build());

        try {
            // 1. Get available domains
            System.out.println("Fetching available domains...");
            DomainResponse domains = client.getDomains();

            assert domains != null && domains.getDomains().length > 0;
            for (String domain : domains.getDomains()) {
                System.out.println("- " + domain);
            }

            // 2. Upload a file
            System.out.println("\nUploading file...");
            File tempFile = createTempFile();
            FileResponse response = client.upload(tempFile);

            System.out.println("File uploaded: " + response.getUrl());
            String hash = response.data().hash();
            System.out.println("Hash: " + hash);

            // 3. Delete file
            System.out.println("\nDeleting file...");
            client.delete(hash);
            System.out.println("File deleted successfully");

            // Clean up local temp file
            tempFile.delete();

        } catch (SeeException e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static File createTempFile() throws IOException {
        File temp = File.createTempFile("see-example-", ".txt");
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write("Hello from SEE Java SDK File Example!\n" + "with Timestamp: " + System.currentTimeMillis());
            writer.flush();
        }
        return temp;
    }
}
