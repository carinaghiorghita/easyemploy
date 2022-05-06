package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import ubb.thesis.easyemploy.Domain.Entities.Token;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
@AllArgsConstructor
//@NoArgsConstructor
public class EmailService {

    public void send(String email, String link){
        //todo see why this is not working
        String user = "b690463fa5f14b";
        String password = "ec842ef72b8824";
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });


        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("easyemployapp@gmail.com"));

            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Confirm registration");

            String msg = "Hello! You're one step away from creating your EasyEmploy account!. Click here to confirm your registration: " + link;


            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }}
