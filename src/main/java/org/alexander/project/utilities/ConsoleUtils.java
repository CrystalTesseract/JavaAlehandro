package org.alexander.project.utilities;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;
@Component
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
