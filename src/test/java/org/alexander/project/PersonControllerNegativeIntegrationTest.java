package org.alexander.project;

import org.alexander.project.config.IntegrationTestBase;
import org.alexander.project.entity.Person;
import org.alexander.project.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerNegativeIntegrationTest extends IntegrationTestBase {
    @Autowired
    private PersonService db;

    @Test
    public void testSearchPerson() throws Exception {
        Person person = new Person("John", 13);
        db.save(person);
        int page = 0;

        mockMvc.perform(get("/v1/persons/search")
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/v1/persons")
                        .param("id", String.valueOf(99999))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/v1/persons"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testEdit() throws Exception {
        Person person = new Person(0, "John", 13, "sa@yahoo.sex", "1231231312");
        db.save(person);
        person.setName("Bob");
        person.setId(99999);

        ObjectMapper objectMapper = new ObjectMapper();
        String personJson = objectMapper.writeValueAsString(person);
        mockMvc.perform(put("/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
