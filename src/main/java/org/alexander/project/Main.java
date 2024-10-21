package org.alexander.project;

import lombok.SneakyThrows;
import org.alexander.project.service.CalculatorService;
import org.alexander.project.service.PersonService;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    static ExecutorService executor = Executors.newFixedThreadPool(1);
    static PersonService personService = new PersonService();
    static CalculatorService calculatorService = new CalculatorService();
    static Future<?> futurePersonService = null;
    static Future<?> futureCalculator = null;

    @SneakyThrows
    public static void main(String[] args) {
        ConsoleUtils cons = new ConsoleUtils();
        boolean IsNotEnded = true;
        DataBaseUtils db = new DataBaseUtils();
        db.setConnection();
        db.dropPersonTable();
        db.createPersonTable();


        while (IsNotEnded) {
            cons.print("Выберите программу: 1 - калькулятор, 2 - База данных персон, 3 - закончить работу >>> ");
            int q = cons.getInt();
            switch (q) {
                case 1 -> {
                    futureCalculator = executor.submit(() -> calculatorService.perform());
                    futureCalculator.get();
                }
                case 2 -> {
                    if (futurePersonService == null || futurePersonService.isDone()) {
                        futurePersonService = executor.submit(() -> personService.perform());
                    }
                    futurePersonService.get();
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