package SergoProj;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static String url = "jdbc:postgresql://localhost:5432/postgres";
    public static String user = "user";
    public static String password = "password";

    @SneakyThrows
    public static void main(String[] args) {
        Boolean IsNotEnded = true;

            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            stmt.execute("create table if not exists person (ID int, name varchar(255), age int)");

        Scanner sc = new Scanner(System.in);
        PersonBase personBase = new PersonBase();

        while (IsNotEnded) {
            System.out.print("Выберите программу: 1 - калькулятор, 2 - База данных персон, 3 - закончить работу >>> ");
            try {
                int q = sc.nextInt();
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
                System.out.println("Введено неверное значение");
                sc.next();
            } catch (InterruptedException e) {
                System.out.println("Возвращение к выбору программ");
            }
        }
        stmt.execute("drop table person");


    }
}