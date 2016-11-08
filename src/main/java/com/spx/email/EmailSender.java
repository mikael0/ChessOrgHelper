package com.spx.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailSender {

    private String fromAddress;
    private String fromPassword;
    private String host;
    private int port;
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);


    public EmailSender(String fromAddress, String fromPassword, String host, int port) {
        this.fromAddress = fromAddress;
        this.fromPassword = fromPassword;
        this.host = host;
        this.port = port;
    }

    public void sendEmail(EmailEntity entity) {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.ssl.enable", "true");

        properties.put("mail.smtp.port", port);
        

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
