package org.alexander.project;

import lombok.RequiredArgsConstructor;
import org.alexander.project.config.IntegrationTestBase;
import org.alexander.project.entity.Person;
import org.alexander.project.repository.DataBaseJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class PersonControllerIntegrationTest extends IntegrationTestBase {
    @Autowired
    private DataBaseJpaRepository db;

    @Test
    public void testSearchPerson() throws Exception {
        Person person = new Person("John", 13);
        db.save(person);
        String name = "John";
        int page = 1;

        MvcResult mvcResult = mockMvc.perform(get("/api/search")
                        .param("name", name)
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(jsonResponse, new TypeReference<List<Person>>() {});

        assertThat(persons).isNotNull();
        assertThat(persons).isNotEmpty();
        assertThat(persons.get(0).getName()).isEqualTo(name);
    }
}

