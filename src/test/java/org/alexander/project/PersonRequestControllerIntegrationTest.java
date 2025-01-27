package org.alexander.project;

import org.alexander.project.config.IntegrationTestBase;
import org.alexander.project.entity.Person;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {"/data.sql"})
public class PersonRequestControllerIntegrationTest extends IntegrationTestBase {
    @Test
    public void testSearchPerson() {
        String name = "John";
        int page = 1;

        List<Person> persons = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/personService/search")
                        .queryParam("name", name)
                        .queryParam("page", page)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)  // Указываем, что ожидаем список объектов Person
                .returnResult()
                .getResponseBody();

        // Проверяем результаты
        assertThat(persons).isNotNull();
        assertThat(persons).isNotEmpty();
        assertThat(persons.get(0).getName()).isEqualTo(name);
    }


}

