package org.alexander.project.service.person;

import lombok.RequiredArgsConstructor;
import org.alexander.project.api.FnsApi;
import org.alexander.project.entity.Person;
import org.alexander.project.repository.DataBaseJpaRepository;
import org.alexander.project.storage.Storage;
import org.alexander.project.utilities.ConsoleUtils;
import org.alexander.project.utilities.MailUtils;
import org.alexander.project.utilities.PersonGeneratorUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.alexander.project.storage.Storage.nextId;

@Service
@RequiredArgsConstructor
public class PersonJpaService {
    private final DataBaseJpaRepository db;
    private final PersonGeneratorUtils fakeNamer;
    private final ConsoleUtils cons;
    private final MailUtils mail;
    private final FnsApi fnsApi;


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
            int choice = cons.getInt();
            cons.nextLine();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    for (int i = 0; i < Storage.getId(); i++) {
                        Person person = db.findById(i).orElseThrow(() -> new IllegalArgumentException("Person not found"));
                        cons.println("ID:" + person.getId() + " | Name:" + person.getName() + " | Age:" + person.getAge() + " | Email:" + person.getEmail() + " | INN:" + person.getInn());
                    }
                    System.out.print("Введите ID личности>>>");
                    int id = cons.getInt();
                    Person person = db.findById(id).orElseThrow(() -> new IllegalArgumentException("Person not found"));
                    String organizationData = fnsApi.findOrganizationData(person.getInn()).toString();
                    person.setOrganizationdata(organizationData);
                    db.save(person);
                    cons.println(organizationData);
                }
                case 2 -> {
                    for (int i = 0; i < Storage.getId(); i++) {
                        Person person = db.findById(i).orElseThrow(() -> new IllegalArgumentException("Person not found"));
                        cons.println("ID:" + person.getId() + " | Name:" + person.getName() + " | Age:" + person.getAge() + " | Email:" + person.getEmail() + " | INN:" + person.getInn());
                    }
                }
                case 3 -> {
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    Person person = new Person();
                    personName = cons.getLine();
                    personAge = cons.getInt();
                    cons.nextLine();

                    person.setName(personName);
                    person.setAge(personAge);
                    person.setEmail(fakeNamer.generateEmail());
                    person.setId(nextId());
                    person.setInn(fakeNamer.generateInn());
                    db.save(person);
                }
                case 4 -> {
                    cons.print("Отправьте целое число n >>> ");
                    int n = cons.getInt();
                    cons.nextLine();
                    for (int i = 0; i < n; i++) {
                        fakePersonAge = (int) Math.round(Math.random() * 120);
                        Person person = new Person();
                        person.setAge(fakePersonAge);
                        person.setInn(fakeNamer.generateInn());
                        person.setName(fakeNamer.generateName());
                        person.setEmail(fakeNamer.generateEmail());
                        person.setId(nextId());
                        db.save(person);
                    }
                }
                case 5 -> {
                    cons.print("Отправьте почту получателя >>>");
                    String realEmail = cons.getLine();
                    cons.println("Отправьте имя персоны, а затем отправьте возраст:");
                    personName = cons.getLine();
                    personAge = cons.getInt();
                    cons.nextLine();
                    Person person = new Person();
                    person.setAge(personAge);
                    person.setInn(fakeNamer.generateInn());
                    person.setName(personName);
                    person.setEmail(realEmail);
                    person.setId(nextId());
                    db.save(person);
                }
                case 6 -> {
                    for (int i = 0; i < Storage.getId(); i++) {
                        Person person = db.findById(i).orElse(null);
                        mail.sendFakeMessage(person.getEmail());
                    }

                }
                case 7 -> {
                    Map<Integer, String> emails = new HashMap<>();
                    for (int i = 0; i < Storage.getId(); i++) {
                        Person person = db.findById(i).orElse(null);
                        cons.println("ID:" + person.getId() + " | Name:" + person.getName() + " | Age:" + person.getAge() + " | Email:" + person.getEmail() + " | INN:" + person.getInn());
                        emails.put(person.getId(), person.getEmail());
                    }
                    cons.print("Отправьте ID получателя >>>");
                    int claimerId = cons.getInt();
                    cons.nextLine();
                    mail.sendFakeMessage(emails.get(claimerId));
                }
                case 8 -> {
                    Map<Integer, String> emails = new HashMap<>();
                    for (int i = 0; i < Storage.getId(); i++) {
                        Person person = db.findById(i).orElse(null);
                        cons.println("ID:" + person.getId() + " | Name:" + person.getName() + " | Age:" + person.getAge() + " | Email:" + person.getEmail() + " | INN:" + person.getInn());
                        emails.put(person.getId(), person.getEmail());
                    }
                    cons.print("Отправьте ID получателя >>>");
                    int claimerId = cons.getInt();
                    cons.nextLine();
                    mail.sendRealMessage(emails.get(claimerId));
                }
                case 777 -> {
                    Person person = new Person();
                    person.setInn("507407629014");
                    person.setEmail("SeregaVaga@dota.tv");
                    person.setId(nextId());
                    person.setAge(52);
                    person.setName("Сергей");
                    db.save(person);
                }
            }
        }
    }
}
