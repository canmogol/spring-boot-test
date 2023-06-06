package dev.canm.sbtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SpringBootTestApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {
            // This test is empty on purpose, it is just to check that the context loads
        });
    }

}
