package org.alexander.project;

import lombok.SneakyThrows;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseUtils;

import java.util.InputMismatchException;

public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        ConsoleUtils cons = new ConsoleUtils();
        boolean IsNotEnded = true;
        DataBaseUtils db = new DataBaseUtils();
        db.setConnection();
        db.dropPersonTable();
        db.createPersonTable();

        PersonBase personBase = new PersonBase();
        Thread pbThread = new Thread(personBase);

        while (IsNotEnded) {
            cons.print("Выберите программу: 1 - калькулятор, 2 - База данных персон, 3 - закончить работу >>> ");
            try {
                int q = cons.getInt();
                switch (q) {
                    case 1 -> {
                        Calc calc = new Calc();
                        Thread calcThread = new Thread(calc);
                        calcThread.start();
                        calcThread.join();
                    }
                    case 2 -> {
                        if (!pbThread.isAlive()) {
                            pbThread = new Thread(personBase);
                            pbThread.start();
                        }
                        pbThread.join();
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


    }
}