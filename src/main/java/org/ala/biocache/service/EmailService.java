package org.ala.biocache.service;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A service responsible for sending emails.
 * 
 * @author Natasha Carter (natasha.carter@csiro.au)
 *
 */
@Component("emailService")
public class EmailService {
    /** The default sender for emails from the biocache */
    private String sender = "data@ala.org.au";
    private static final Logger logger = Logger.getLogger(EmailService.class);
    private Properties properties =new Properties();
    private String host = "localhost";
    private String port = "25";
   

    @PostConstruct
    protected void init(){        
        properties.put("mail.smtp.host", host);        
        properties.put("mail.smtp.port", port);
                
    }
    /**
     * Sends an email with the supplied details. 
     * 
     * @param recipient
     * @param subject
     * @param content
     * @param sender
     */
    public void sendEmail(String recipient, String subject, String content, String sender){
        
        logger.debug("Send email to : " + recipient);
        logger.debug("Body: " + content);
        Session session = Session.getDefaultInstance(properties);

        
        try{

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO,
                                     new InternetAddress(recipient));
            message.setSubject(subject);
            message.setContent(content,
                               "text/html" );
            Transport.send(message);
        } catch (Exception e){
            logger.error("Unable to send email to " + recipient + ".\n"+content, e);
        }
    }
    /**
     * Sends an email from the default sender using the supplied details.
     * 
     * @param recipient
     * @param subject
     * @param content
     */
    public void sendEmail(String recipient, String subject, String content){
        sendEmail(recipient, subject, content, sender);
    }
    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }
    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }
    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }
    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    
}