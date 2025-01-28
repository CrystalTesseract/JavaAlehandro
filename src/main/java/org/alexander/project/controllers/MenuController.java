package org.alexander.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.alexander.project.repository.DataBaseStmtRepository;
import org.alexander.project.service.calculator.CalculatorService;
import org.alexander.project.service.person.PersonJpaService;
import org.alexander.project.service.person.PersonOrmService;
import org.alexander.project.service.person.PersonStmtService;
import org.alexander.project.utilities.ConsoleUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@RequiredArgsConstructor
public class MenuController {

    static ExecutorService executor = Executors.newFixedThreadPool(1);
    private final PersonStmtService personStmtService;
    private final CalculatorService calculatorService;
    private final PersonOrmService personOrmService;
    private final PersonJpaService personJpaService;
    private final ConsoleUtils cons;
    private final DataBaseStmtRepository db;
    static Future<?> futurePersonJpaService = null;
    static Future<?> futurePersonOrmService = null;
    static Future<?> futurePersonStmtService = null;
    static Future<?> futureCalculator = null;

    @SneakyThrows
    @GetMapping("/startProgramm")
    public String start() {
        boolean IsNotEnded = true;
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
                    cons.print("Использовать...: 1 - Statement, 2 - Hibernate, 3 - JPA >>> ");
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
                        case 3 -> {
                            if (futurePersonJpaService == null || futurePersonJpaService.isDone()) {
                                futurePersonJpaService = executor.submit(() -> personJpaService.perform());
                            }
                            futurePersonJpaService.get();
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
        return "Программа завершила цикл работы!";
    }
}

