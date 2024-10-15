package org.alexander.project.service;

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


public class PersonService{
    private int id = 0;
    @SneakyThrows
    public void perform() {
        PersonGeneratorUtils fakeNamer = new PersonGeneratorUtils();
        DataBaseUtils db = new DataBaseUtils();
        ConsoleUtils cons = new ConsoleUtils();
        MailUtils mail = new MailUtils();
        mail.setProperties();

        String personName = null;
        int personAge = 0;
        String fakePersonName = null;
        int fakePersonAge = 0;
        String fakeEmail = null;
        mail.createMessage("Наша компания по производству компаний приветствует вас! Мы предлагаем вам открыть компанию через нашу компанию для продвижения компаний.");


        while (!Thread.currentThread().isInterrupted()) {
            cons.print("Выберите операцию: 0 - вернуться к выбору программ, 1 - просмотреть личности, 2 - просмотреть личности из таблицы, 3 - добавить личность, 4 - добавить n случайных личностей, 5 - добавить личность с почтой, 6 - разослать всем личностям приветственное письмо, 7 - отправить конкретной личности приветственное письмо >>> ");
            int d = cons.getInt();
            cons.nextLine();
            switch (d) {
                case 0 -> {
                    return;
                }
                case 1 -> System.out.println("Эта опция более неактуальна");
                case 2 -> {
                    ResultSet rs = db.getPersonTable();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email"));
                    }
                }
                case 3 -> {
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");

                    personName = cons.getLine();
                    personAge = cons.getInt();
                    cons.nextLine();

                    fakeEmail = fakeNamer.generateEmail();

                    db.insertPerson(id, personName, personAge, fakeEmail);
                    id++;
                }
                case 4 -> {
                    cons.print("Отправьте целое число n >>> ");
                    int n = cons.getInt();
                    cons.nextLine();
                    for (int i = 0; i < n; i++) {
                        fakePersonName = fakeNamer.generateName();
                        fakePersonAge = (int) Math.round(Math.random() * 120);
                        fakeEmail = fakeNamer.generateEmail();


                        db.insertPerson(id, fakePersonName, fakePersonAge, fakeEmail);
                        id++;
                    }
                }
                case 5 -> {
                    cons.print("Отправьте почту получателя >>>");
                    String realEmail = cons.getLine();
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    personName = cons.getLine();
                    personAge = cons.getInt();

                    cons.nextLine();

                    db.insertPerson(id, personName, personAge, realEmail);
                    id++;
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

