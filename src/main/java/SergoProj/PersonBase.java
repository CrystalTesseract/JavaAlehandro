package SergoProj;

import java.util.ArrayList;
import java.util.Scanner;
import com.github.javafaker.Faker;

public class PersonBase extends Thread {
    static ArrayList<Person> persons = new ArrayList<>();

    public static void setArr(ArrayList<Person> arr) {
        persons = arr;
    }

    public static ArrayList<Person> getArr() {
        return persons;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Faker fakeNamer = new Faker();

        while (!isInterrupted()) {
            System.out.print("Выберите операцию: 0 - вернуться к выбору программ, 1 - добавить личность, 2 - просмотреть личности, 3 - добавить n случайных личностей >>> ");
            int d = scanner.nextInt();
            scanner.nextLine();
            switch (d) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    System.out.println("Отправьте имя персоны, а затем отправьте возраст:");
                    persons.add(new Person(scanner.nextLine(), scanner.nextInt()));
                    scanner.nextLine();
                }
                case 2 -> System.out.println(persons);
                case 3 -> {
                    System.out.print("Отправьте целое число n >>> ");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < n; i++) {
                        String fakeName = fakeNamer.name().firstName();
                        persons.add(new Person(fakeName, 0));
                    }
                }
            }
        }
    }
}

