package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService {

    public void send(String email, String link){
        final String username = "easyemploy@yahoo.com";
        final String password = "xfzlhhneuafgzcmg";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mail.yahoo.com");
        prop.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("easyemploy@yahoo.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Confirm registration");
            message.setText("Hello! You're one step away from creating your EasyEmploy account!. Click here to confirm your registration: " + link);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }    }}
