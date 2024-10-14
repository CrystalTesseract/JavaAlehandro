package org.alexander.project;

import java.util.Scanner;

public class Calc implements Runnable {
    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите первое число >>> ");
        Double element1 = scan.nextDouble();
        scan.nextLine();
        System.out.print("Укажите одну из четырёх базовых математических операций (+,-,*,/) >>> ");
        String op = scan.nextLine();
        System.out.print("Введите второе число >>> ");
        Double element2 = scan.nextDouble();
        scan.nextLine();
        int z = 0;
        while (true) {
            if (z > 0) {
                op = scan.nextLine();

                if (op.isEmpty()) {
                    System.out.println("Финальный результат - " + element1 + "\nГойда, братья!");
                    break;
                }
                System.out.print("Введите второе число >>> ");
                element2 = scan.nextDouble();
                scan.nextLine();
            }
            element1 = switch (op) {
                case "-" -> element1 - element2;
                case "+" -> element1 + element2;
                case "/" -> element1 / element2;
                case "*" -> element1 * element2;
                default -> element1;
            };

            System.out.println("Результат операции - " + element1);
            System.out.print("Для продолжения укажите одну из четырёх базовых математических операций (+,-,*,/) или отправьте пустую строку для завершения >>> ");
            z++;
        }


    }
}