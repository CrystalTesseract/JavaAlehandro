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


public class PersonControllerIntegrationTest extends IntegrationTestBase {
    @Autowired
    private PersonService db;

    @Test
    public void searchPerson_WithValidParams_ShouldReturnPersonList() throws Exception {
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
    public void findPerson_ByValidId_ShouldReturnPerson() throws Exception {
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
    public void createPerson_WithValidData_ShouldReturnCreatedStatus() throws Exception {
        Person person = new Person(0, "John", 13, "sa@yahoo.sex", "1231231312");

        ObjectMapper objectMapper = new ObjectMapper();
        String personJson = objectMapper.writeValueAsString(person);
        mockMvc.perform(post("/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(db.findById(person.getId())).isEqualTo(person);
    }

    @Test
    public void editPerson_WithValidData_ShouldReturnNoContentStatus() throws Exception {
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

        assertThat(db.findById(person.getId()).getName()).isEqualTo(person.getName());
    }

    @Test
    public void searchPerson_WithInvalidPage_ShouldReturnBadRequest() throws Exception {
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
    public void findPerson_ByNonExistingId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/v1/persons")
                        .param("id", String.valueOf(99999))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void createPerson_WithoutRequestBody_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/v1/persons"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void editPerson_WithInvalidId_ShouldReturnNotFound() throws Exception {
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

        assertThat(db.findById(0).getName()).isNotEqualTo(person.getName());
    }


}

