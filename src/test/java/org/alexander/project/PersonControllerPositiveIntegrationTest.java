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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PersonControllerPositiveIntegrationTest extends IntegrationTestBase {
    @Autowired
    private PersonService db;

    @Test
    public void testSearchPerson() throws Exception {
        Person person = new Person("John", 13);
        db.save(person);
        String name = "John";
        int page = 1;

        MvcResult mvcResult = mockMvc.perform(get("/v1/persons/search")
                        .param("name", name)
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(jsonResponse, new TypeReference<List<Person>>() {
        });

        assertThat(persons).isNotNull();
        assertThat(persons).isNotEmpty();
        assertThat(persons.get(0).getName()).isEqualTo(name);
    }

    @Test
    public void testFindById() throws Exception {
        Person person = new Person("John", 13);
        db.save(person);
        MvcResult mvcResult = mockMvc.perform(get("/v1/persons")
                        .param("id", String.valueOf(person.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Person responsePerson = objectMapper.readValue(jsonResponse, new TypeReference<Person>() {
        });

        assertThat(responsePerson.getId()).isEqualTo(person.getId());
        assertThat(responsePerson.getName()).isEqualTo(person.getName());

    }

    @Test
    public void testCreate() throws Exception {
        Person person = new Person(0, "John", 13, "sa@yahoo.sex", "1231231312");

        ObjectMapper objectMapper = new ObjectMapper();
        String personJson = objectMapper.writeValueAsString(person);
        mockMvc.perform(post("/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testEdit() throws Exception {
        Person person = new Person(0, "John", 13, "sa@yahoo.sex", "1231231312");
        db.save(person);
        person.setName("Bob");

        ObjectMapper objectMapper = new ObjectMapper();
        String personJson = objectMapper.writeValueAsString(person);
        mockMvc.perform(put("/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isNoContent())
                .andReturn();
    }

}

