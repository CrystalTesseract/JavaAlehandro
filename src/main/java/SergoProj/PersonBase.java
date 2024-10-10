package SergoProj;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;

import static SergoProj.Main.*;


public class PersonBase extends Thread {
    static ArrayList<Person> persons = new ArrayList<>();

    public static void setArr(ArrayList<Person> arr) {
        persons = arr;
    }

    public static ArrayList<Person> getArr() {
        return persons;
    }

    @SneakyThrows
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Faker fakeNamer = new Faker();

        String pn;
        int pa;
        String fpn;
        int fpa;
        int idCounter = 0;

        Connection con = DriverManager.getConnection(url, user, password);
        Statement stmt = con.createStatement();

        while (!isInterrupted()) {
            System.out.print("Выберите операцию: 0 - вернуться к выбору программ, 1 - просмотреть личности, 2 - просмотреть личности из таблицы, 3 - добавить личность, 4 - добавить n случайных личностей >>> ");
            int d = scanner.nextInt();
            scanner.nextLine();
            switch (d) {
                case 0 -> {
                    return;
                }
                case 1 -> System.out.println(persons);
                case 2 -> {
                    ResultSet rs = stmt.executeQuery("select * from person");
                    while (rs.next()) {
                        System.out.println("ID:"+rs.getInt("id")+" | Name:"+rs.getString("name")+" | Age:"+rs.getInt("age"));
                    }
                }
                case 3 -> {
                    System.out.println("Отправьте имя персоны, а затем отправьте возраст:");

                    pn = scanner.nextLine();
                    pa = scanner.nextInt();
                    persons.add(new Person(pn, pa));
                    scanner.nextLine();

                    stmt.execute("insert into person (ID, name, age) values ('"+idCounter+"', '"+pn+"', '"+pa+"')");
                    idCounter++;
                }
                case 4 -> {
                    System.out.print("Отправьте целое число n >>> ");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < n; i++) {
                        fpn = fakeNamer.name().firstName();
                        fpa = (int) Math.round(Math.random()*120);

                        persons.add(new Person(fpn, fpa));

                        stmt.execute("insert into person (ID, name, age) values ('"+idCounter+"', '"+fpn+"', '"+fpa+"')");
                        idCounter++;
                    }
                }
            }
        }
    }
}

