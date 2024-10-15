package org.alexander.project.utilities;

import java.util.Scanner;

public class ConsoleUtils {
    private final Scanner scanner = new Scanner(System.in);

    public int getInt() {
        return scanner.nextInt();
    }

    public String getLine() {
        return scanner.nextLine();
    }

    public void next() {
        scanner.next();
    }

    public void nextLine() {
        scanner.nextLine();
    }

    public void print(String m) {
        System.out.print(m);
    }

    public void println(String m) {
        System.out.println(m);
    }
}
