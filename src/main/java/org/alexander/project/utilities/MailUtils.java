package org.alexander.project.utilities;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;

import java.util.Properties;

public class MailUtils {
    private Session realSession;
    private Session fakeSession;
    private Message realMessage;
    private Message fakeMessage;
    private final String username = "nicolaurgant@gmail.com";
    private final String password = "xhnjcszcwnhddxtd"; //12345511Jav

    public void setProperties() {
        Properties realProps = new Properties();
        realProps.put("mail.smtp.auth", "true");
        realProps.put("mail.smtp.starttls.enable", "true");
        realProps.put("mail.smtp.host", "smtp.gmail.com");
        realProps.put("mail.smtp.port", "587");
        realSession = Session.getInstance(realProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Properties fakeProps = new Properties();
        fakeProps.put("mail.smtp.auth", true);
        fakeProps.put("mail.smtp.starttls.enable", "true");
        fakeProps.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        fakeProps.put("mail.smtp.port", "25");
        fakeProps.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");
        fakeSession = Session.getInstance(fakeProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("825d4fd56a1242", "2785b4fa7b3e7f");
            }
        });
    }

    @SneakyThrows
    public void createRealMessage(String msg) {
        realMessage = new MimeMessage(realSession);
        realMessage.setSubject("Приветственное письмо"); // Тема письма
        realMessage.setText(msg); // Текст письма

    }

    @SneakyThrows
    public void sendRealMessage(String to) {
        realMessage.setFrom(new InternetAddress(username));
        realMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        Transport.send(realMessage);
        System.out.println("Отправлено!");
    }

    @SneakyThrows
    public void createFakeMessage(String msg) {
        fakeMessage = new MimeMessage(fakeSession);
        fakeMessage.setSubject("Приветственное письмо"); // Тема письма
        fakeMessage.setText(msg); // Текст письма

    }

    @SneakyThrows
    public void sendFakeMessage(String to) {
        fakeMessage.setFrom(new InternetAddress("CompanyBuildAnotherCompany@gayil.cock"));
        fakeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        Transport.send(fakeMessage);
        System.out.println("Отправлено!");
    }

}
