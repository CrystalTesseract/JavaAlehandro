package org.alexander.project.service.personService;

import lombok.SneakyThrows;
import org.alexander.project.api.FnsApi;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.DataBaseStmtUtils;
import org.alexander.project.utilities.MailUtils;
import org.alexander.project.utilities.PersonGeneratorUtils;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.alexander.project.storage.Storage.nextId;


public class PersonStmtService {
    PersonGeneratorUtils fakeNamer = new PersonGeneratorUtils();
    DataBaseStmtUtils db = new DataBaseStmtUtils();
    ConsoleUtils cons = new ConsoleUtils();
    MailUtils mail = new MailUtils();
    FnsApi fnsApi = new FnsApi();

    @SneakyThrows
    public void perform() {
        mail.setProperties();

        String personName = null;
        int personAge = 0;
        String fakePersonName = null;
        int fakePersonAge = 0;
        String fakeEmail = null;
        mail.createRealMessage("Тестовое письмо. Если вы случайно его получили - забейте.");
        mail.createFakeMessage("Наша компания по производству компаний приветствует вас! Мы предлагаем вам открыть компанию через нашу компанию для продвижения компаний.");


        while (!Thread.currentThread().isInterrupted()) {
            cons.print("Выберите операцию: 0 - вернуться к выбору программ, 1 - Получить данные по ИНН личности, 2 - просмотреть личности из таблицы, 3 - добавить личность, 4 - добавить n случайных личностей, 5 - добавить личность с почтой, 6 - разослать всем личностям приветственное письмо, 7 - отправить конкретной личности приветственное письмо, 8 - отправить конкретной личности реальное письмо >>> ");
            int d = cons.getInt();
            cons.nextLine();
            switch (d) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    ResultSet rs = db.getPersonTable();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email") + " | INN:" + rs.getString("inn"));
                    }
                    System.out.print("Введите ID личности>>>");
                    int id = cons.getInt();
                    String organizationData = fnsApi.findOrganizationData(db.getInn(id)).toString();
                    db.insertOrganizationData(id, organizationData);
                    System.out.println(organizationData);
                }
                case 2 -> {
                    ResultSet rs = db.getPersonTable();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email") + " | INN:" + rs.getString("inn"));
                    }
                }
                case 3 -> {
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");

                    personName = cons.getLine();
                    personAge = cons.getInt();
                    cons.nextLine();

                    fakeEmail = fakeNamer.generateEmail();

                    db.insertPerson(nextId(), personName, personAge, fakeEmail, fakeNamer.generateInn());
                }
                case 4 -> {
                    cons.print("Отправьте целое число n >>> ");
                    int n = cons.getInt();
                    cons.nextLine();
                    for (int i = 0; i < n; i++) {
                        fakePersonName = fakeNamer.generateName();
                        fakePersonAge = (int) Math.round(Math.random() * 120);
                        fakeEmail = fakeNamer.generateEmail();


                        db.insertPerson(nextId(), fakePersonName, fakePersonAge, fakeEmail, fakeNamer.generateInn());
                    }
                }
                case 5 -> {
                    cons.print("Отправьте почту получателя >>>");
                    String realEmail = cons.getLine();
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    personName = cons.getLine();
                    personAge = cons.getInt();

                    cons.nextLine();

                    db.insertPerson(nextId(), personName, personAge, realEmail, fakeNamer.generateInn());
                }
                case 6 -> {
                    ResultSet rs = db.ZexecuteQuery("select * from person");
                    while (rs.next()) {
                        mail.sendFakeMessage(rs.getString("email"));
                    }
                }
                case 7 -> {
                    ResultSet rs = db.getPersonTable();
                    Map<Integer, String> emails = new HashMap<>();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email") + " | INN:" + rs.getString("inn"));
                        emails.put(rs.getInt("id"), rs.getString("email"));
                    }
                    cons.print("Отправьте ID получателя >>>");
                    int claimerId = cons.getInt();
                    cons.nextLine();
                    mail.sendFakeMessage(emails.get(claimerId));
                }
                case 8 -> {
                    ResultSet rs = db.getPersonTable();
                    Map<Integer, String> emails = new HashMap<>();
                    while (rs.next()) {
                        cons.println("ID:" + rs.getInt("id") + " | Name:" + rs.getString("name") + " | Age:" + rs.getInt("age") + " | Email:" + rs.getString("email") + " | INN:" + rs.getString("inn"));
                        emails.put(rs.getInt("id"), rs.getString("email"));
                    }
                    cons.print("Отправьте ID получателя >>>");
                    int claimerId = cons.getInt();
                    cons.nextLine();
                    mail.sendRealMessage(emails.get(claimerId));
                }
                case 777 -> {
                    db.insertPerson(nextId(), "Сергей", 52, "SeregaVaga@dota.tv", "507407629014");
                }
            }
        }
    }
}

