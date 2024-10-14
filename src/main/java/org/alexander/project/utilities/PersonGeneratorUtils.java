package org.alexander.project.utilities;

import com.github.javafaker.Faker;

public class PersonGeneratorUtils {
    Faker faker = new Faker();

    public String generateName() {
        return faker.name().firstName();
    }

    public String generateEmail() {
        return faker.internet().emailAddress();
    }
}
