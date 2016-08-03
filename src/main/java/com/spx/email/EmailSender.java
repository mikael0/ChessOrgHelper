package com.spx.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by timofb on 07-Dec-15.
 */
public class EmailSender {

    private String fromAddress;
    private String fromPassword;
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);


    public EmailSender(String fromAddress, String fromPassword) {
        this.fromAddress = fromAddress;
        this.fromPassword = fromPassword;
    }

    public void sendEmail(EmailEntity entity) {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromAddress, fromPassword);
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromAddress));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(entity.getAddress()));

            message.setSubject(entity.getTheme());

            message.setContent(entity.getHtml(), "text/html" );

            Transport.send(message);
            LOGGER.info("Sent message successfully....");
        }catch (MessagingException mex) {
            LOGGER.error("Error sending message", mex);
        }
    }
}
