package org.alexander.project.utilities;

import com.github.javafaker.Faker;

import java.util.Random;

public class PersonGeneratorUtils {
    private final Faker faker = new Faker();

    public String generateName() {
        return faker.name().firstName();
    }

    public String generateEmail() {
        return faker.internet().emailAddress();
    }

    public String generateInn() {
        Random random = new Random();
        StringBuilder inn = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10);
            inn.append(digit);
        }

        int controlDigit = random.nextInt(10);
        inn.append(controlDigit);
        return inn.toString();
    }
}
