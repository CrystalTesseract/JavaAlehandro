package org.alexander.project;

import lombok.SneakyThrows;
import org.alexander.project.entity.Person;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseUtils;
import org.alexander.project.utilities.MailUtils;
import org.alexander.project.utilities.PersonGeneratorUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
        DataBaseUtils db = new DataBaseUtils();
        ConsoleUtils cons = new ConsoleUtils();
        MailUtils mail = new MailUtils();
        mail.setProperties();

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
                    ResultSet rs = db.getPersonTable();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email"));
                    }
                }
                case 3 -> {
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");

                    pn = cons.getLine();
                    pa = cons.getInt();
                    persons.add(new Person(pn, pa));
                    cons.nextLine();

                    fakeEmail = fakeNamer.generateEmail();

                    db.insertPerson(idCounter, pn, pa, fakeEmail);
                    idCounter++;
                }
                case 4 -> {
                    cons.print("Отправьте целое число n >>> ");
                    int n = cons.getInt();
                    cons.nextLine();
                    for (int i = 0; i < n; i++) {
                        fpn = fakeNamer.generateName();
                        fpa = (int) Math.round(Math.random() * 120);
                        fakeEmail = fakeNamer.generateEmail();

                        persons.add(new Person(fpn, fpa));

                        db.insertPerson(idCounter, fpn, fpa, fakeEmail);
                        idCounter++;
                    }
                }
                case 5 -> {
                    cons.print("Отправьте почту получателя >>>");
                    String realEmail = cons.getLine();
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    pn = cons.getLine();
                    pa = cons.getInt();

                    persons.add(new Person(pn, pa));
                    cons.nextLine();

                    db.insertPerson(idCounter, pn, pa, realEmail);
                    idCounter++;
                }
                case 6 -> {
                    ResultSet rs = db.executeQuery("select * from person");
                    while (rs.next()) {
                        mail.sendMessage(rs.getString("email"));
                    }
                }
                case 7 -> {
                    ResultSet rs = db.getPersonTable();
                    Map<Integer, String> emails = new HashMap<>();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email"));
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

