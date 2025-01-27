package org.alexander.project;


import org.alexander.project.config.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KokaKola extends IntegrationTestBase {
    @Test
    public void kokaKola() {
        System.out.println("Z");
    }
}
