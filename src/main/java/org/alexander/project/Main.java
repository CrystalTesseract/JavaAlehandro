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
    static ExecutorService executor = Executors.newFixedThreadPool(1);
    static PersonStmtService personStmtService;
    static CalculatorService calculatorService = new CalculatorService();
    static PersonOrmService personOrmService;
    static Future<?> futurePersonOrmService = null;
    static Future<?> futurePersonStmtService = null;
    static Future<?> futureCalculator = null;

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        personStmtService = context.getBean(PersonStmtService.class);
        calculatorService = context.getBean(CalculatorService.class);
        personOrmService = context.getBean(PersonOrmService.class);
        ConsoleUtils cons = new ConsoleUtils();
        boolean IsNotEnded = true;
        DataBaseStmtUtils db = new DataBaseStmtUtils();
        db.setConnection();
        db.dropPersonTable();
        db.createPersonTable();


        while (IsNotEnded) {
            cons.print("Выберите программу: 1 - калькулятор, 2 - База данных персон, 3 - закончить работу >>> ");
            int firstChoice = cons.getInt();
            switch (firstChoice) {
                case 1 -> {
                    futureCalculator = executor.submit(() -> calculatorService.perform());
                    futureCalculator.get();
                }
                case 2 -> {
                    cons.print("Использовать...: 1 - Statement, 2 - Hibernate >>> ");
                    int secondChoice = cons.getInt();
                    switch (secondChoice) {
                        case 1 -> {
                            if (futurePersonStmtService == null || futurePersonStmtService.isDone()) {
                                futurePersonStmtService = executor.submit(() -> personStmtService.perform());
                            }
                            futurePersonStmtService.get();
                        }
                        case 2 -> {
                            if (futurePersonOrmService == null || futurePersonOrmService.isDone()) {
                                futurePersonOrmService = executor.submit(() -> personOrmService.perform());
                            }
                            futurePersonOrmService.get();
                        }
                        default -> cons.println("Введено неверное значение");
                    }
                }
                case 3 -> {
                    IsNotEnded = false;

                }
                default -> cons.println("Введено неверное значение");
            }
        }
        db.dropPersonTable();
        executor.shutdown();
    }
}