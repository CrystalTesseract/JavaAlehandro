package Task2;

import java.util.ArrayList;
import java.util.Scanner;

public class PersonBase {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Person> persons = new ArrayList<>();
        Person pers;
        int d;
        while (true) {
            System.out.print("Выберите операцию: 1 - добавить личность, 2 - просмотреть личности >>> ");
            d = scanner.nextInt();
            scanner.nextLine();
            switch (d){
                case 1 -> {
                    System.out.println("Введите имя персоны, а затем возраст:");
                    persons.add(new Person(scanner.nextLine(), scanner.nextInt()));
                }

                case 2 -> System.out.println(persons);

            }

        }




    }
}
