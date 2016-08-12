/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author 2015127
 */
public class SendEmail {

    public void sendEmail(String receptionist, String subject, String msg) {
        final String username = "admin@vut.ac.za";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        // props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "196.21.64.9");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, "Supp0rt.123");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("admin@vut.ac.za"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receptionist));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            // System.out.println("Done");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }
}
