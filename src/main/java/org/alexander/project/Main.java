package org.alexander.project;

import lombok.SneakyThrows;
import org.alexander.project.service.calculatorService.CalculatorService;
import org.alexander.project.service.personService.PersonOrmService;
import org.alexander.project.service.personService.PersonStmtService;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseStmtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}