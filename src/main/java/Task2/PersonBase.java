package Task2;

import java.util.ArrayList;
import java.util.Scanner;

public class PersonBase {
    public static void start() {
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
                    System.out.println("Отправье имя персоны, а затем отправьте возраст:");
                    persons.add(new Person(scanner.nextLine(), scanner.nextInt()));
                }

                case 2 -> System.out.println(persons);

            }

        }




    }
}
class Person {
    private String name;
    private int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ':' + age;
    }
}