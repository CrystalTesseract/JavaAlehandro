package org.alexander.project;

import lombok.SneakyThrows;
import org.alexander.project.service.CalculatorService;
import org.alexander.project.service.PersonService;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseUtils;

import java.util.InputMismatchException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        ConsoleUtils cons = new ConsoleUtils();
        boolean IsNotEnded = true;
        DataBaseUtils db = new DataBaseUtils();
        db.setConnection();
        db.dropPersonTable();
        db.createPersonTable();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        PersonService personService = new PersonService();
        CalculatorService calculatorService = new CalculatorService();
        Future<?> futurePersonService = null;
        Future<?> futureCalculator = null;

        while (IsNotEnded) {
            cons.print("Выберите программу: 1 - калькулятор, 2 - База данных персон, 3 - закончить работу >>> ");
            try {
                int q = cons.getInt();
                switch (q) {
                    case 1 -> {
                        futureCalculator = executor.submit(() -> calculatorService.perform());
                        futureCalculator.get();
                    }
                    case 2 -> {
                        if (futurePersonService==null || futurePersonService.isDone() ) {
                            futurePersonService = executor.submit(() -> personService.perform());
                        }
                        futurePersonService.get();
                    }
                    case 3 -> {
                        IsNotEnded = false;
                    }
                }
            } catch (InputMismatchException e) {
                cons.println("Введено неверное значение");
                cons.next();
            } catch (InterruptedException e) {
                cons.println("Возвращение к выбору программ");
            }
        }
        db.dropPersonTable();
        executor.shutdown();
    }
}