package Task2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Выберите программу: 1 - калькулятор, 2 - База данных персон >>> ");
        try {
            int q = sc.nextInt();
            switch (q) {
                case 1 -> Calc.main();
                case 2 -> PersonBase.main();
            }
        } catch (InputMismatchException e) {
            System.out.println("Введено неверное значение");
        }

    }

}
