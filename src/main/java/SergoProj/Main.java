package SergoProj;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PersonBase personBase = new PersonBase();

        while (true) {
            System.out.print("Выберите программу: 1 - калькулятор, 2 - База данных персон >>> ");
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
                }
            } catch (InputMismatchException e) {
                System.out.println("Введено неверное значение");
                sc.next();
            } catch (InterruptedException e) {
                System.out.println("Возвращение к выбору программ");
            }
        }
    }
}