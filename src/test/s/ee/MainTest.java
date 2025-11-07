package s.ee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.Main.main;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @BeforeEach
    void setUp() {
        // Setup code before each test if needed
    }

    @Test
    void testMainStatic() {
        main(new String[]{"TestUser"});
        // Add assertions to verify the expected output or behavior
    }

    @AfterEach
    void tearDown() {
        // Cleanup code after each test if needed
    }
}