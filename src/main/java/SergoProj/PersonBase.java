package SergoProj;

import com.github.javafaker.Faker;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.random.RandomGenerator;

import SergoProj.Main;

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
        PersonGeneratorUtils fakeNamer = new PersonGeneratorUtils();


        String pn = null;
        int pa = 0;
        String fpn = null;
        int fpa = 0;
        int idCounter = 0;
        String fakeEmail = null;
        mail.createMessage("Наша компания по производству компаний приветствует вас! Мы предлагаем вам открыть компанию через нашу компанию для продвижения компаний.");



        while (!isInterrupted()) {
            cons.print("Выберите операцию: 0 - вернуться к выбору программ, 1 - просмотреть личности, 2 - просмотреть личности из таблицы, 3 - добавить личность, 4 - добавить n случайных личностей, 5 - добавить личность с почтой, 6 - разослать всем личностям приветственное письмо, 7 - отправить конкретной личности приветственное письмо >>> ");
            int d = cons.getInt();
            cons.nextLine();
            switch (d) {
                case 0 -> {
                    return;
                }
                case 1 -> System.out.println(persons);
                case 2 -> {
                    ResultSet rs = db.executeQuery("select * from person");
                    while (rs.next()) {
                        cons.println("ID:"+rs.getInt("id")+" | Name:"+rs.getString("name")+" | Age:"+rs.getInt("age")+" | Email:"+rs.getString("email"));
                    }
                }
                case 3 -> {
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");

                    pn = cons.getLine();
                    pa = cons.getInt();
                    persons.add(new Person(pn, pa));
                    cons.nextLine();

                    fakeEmail = fakeNamer.generateEmail();

                    db.execute("insert into person (ID, name, age, email) values ('"+idCounter+"', '"+pn+"', '"+pa+"', '"+fakeEmail+"')");
                    idCounter++;
                }
                case 4 -> {
                    cons.print("Отправьте целое число n >>> ");
                    int n = cons.getInt();
                    cons.nextLine();
                    for (int i = 0; i < n; i++) {
                        fpn = fakeNamer.generateName();
                        fpa = (int) Math.round(Math.random()*120);
                        fakeEmail = fakeNamer.generateEmail();

                        persons.add(new Person(fpn, fpa));

                        db.execute("insert into person (ID, name, age, email) values ('"+idCounter+"', '"+fpn+"', '"+fpa+"', '"+fakeEmail+"')");
                        idCounter++;
                    }
                }
                case 5 -> {
                    cons.print("Отправьте почту получателя >>>");
                    String realMail = cons.getLine();
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    pn = cons.getLine();
                    pa = cons.getInt();

                    persons.add(new Person(pn, pa));
                    cons.nextLine();

                    db.execute("insert into person (ID, name, age, email) values ('"+idCounter+"', '"+pn+"', '"+pa+"', '"+realMail+"')");
                    idCounter++;
                }
                case 6 ->{
                    ResultSet rs = db.executeQuery("select * from person");
                    while (rs.next()) {
                        mail.sendMessage(rs.getString("email"));
                    }
                }
                case 7 -> {
                    ResultSet rs = db.executeQuery("select * from person");
                    Map<Integer, String> emails = new HashMap<>();
                    while (rs.next()) {
                        cons.println("ID:"+rs.getInt("id")+" | Name:"+rs.getString("name")+" | Age:"+rs.getInt("age")+" | Email:"+rs.getString("email"));
                        emails.put(rs.getInt("id"), rs.getString("email"));
                    }
                    cons.print("Отправьте ID получателя >>>");
                    int claimerId = cons.getInt();
                    cons.nextLine();
                    mail.sendMessage(emails.get(claimerId));
                }
            }
        }
    }
}

