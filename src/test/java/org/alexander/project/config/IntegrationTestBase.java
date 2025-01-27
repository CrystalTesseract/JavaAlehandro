package org.alexander.project.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15.6")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpassword");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeAll
    public static void StartContainers() {
        System.out.println("ZZZ");
        postgresContainer.start();
    }
}
