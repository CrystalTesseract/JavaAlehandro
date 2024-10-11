package SergoProj;

import lombok.SneakyThrows;

import java.io.Console;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static DataBaseService db = new DataBaseService();
    static ConsoleUtils cons = new ConsoleUtils();
    static MailUtils mail = new MailUtils();

    @SneakyThrows
    public static void main(String[] args) {
        Boolean IsNotEnded = true;

        db.connect();
        db.execute("create table if not exists person (ID int, name varchar(255), age int, Email varchar(255))");
        mail.setProperties();

        PersonBase personBase = new PersonBase();

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
                        if (!personBase.isAlive()) {
                            personBase = new PersonBase();
                            personBase.start();
                        }
                        personBase.join();
                    }
                    case 3 -> {IsNotEnded=false;}
                }
            } catch (InputMismatchException e) {
                cons.println("Введено неверное значение");
                cons.next();
            } catch (InterruptedException e) {
                cons.println("Возвращение к выбору программ");
            }
        }
        db.execute("drop table person");


    }
}