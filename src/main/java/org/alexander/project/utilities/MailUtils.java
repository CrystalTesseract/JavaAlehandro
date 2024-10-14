package org.alexander.project.utilities;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;

import java.util.Properties;

public class MailUtils {
    Session session;
    Message message;

    public void setProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("825d4fd56a1242", "2785b4fa7b3e7f");
            }
        });
    }

    @SneakyThrows
    public void createMessage(String msg) {
        message = new MimeMessage(session);
        message.setSubject("Приветственное письмо"); // Тема письма
        message.setText(msg); // Текст письма

    }

    @SneakyThrows
    public void sendMessage(String to) {
        message.setFrom(new InternetAddress("CompanyBuildAnotherCompany@gayil.cock"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        Transport.send(message);
        System.out.println("Отправлено!");
    }

}
