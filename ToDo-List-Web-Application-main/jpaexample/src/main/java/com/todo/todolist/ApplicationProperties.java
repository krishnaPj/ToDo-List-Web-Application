package com.todo.todolist;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

    private static ApplicationProperties istance = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
    Properties prop;

    private ApplicationProperties(){
        try {
        	prop = new Properties();
        	prop.load(this.getClass().getClassLoader().getResourceAsStream("bundle.properties"));
        } catch (IOException IOException) { LOGGER.error("Error while reading a file: ", IOException); }
    }
    
    public static void sendEmail(String subject, String email, String content) throws MessagingException, IOException {
    	LOGGER.info("Trying to send email to ", email);
    	ApplicationProperties appProp = new ApplicationProperties();
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		Session messageSession = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				try { return new PasswordAuthentication(appProp.getUserMail(), appProp.getPswMail()); }
				catch (IOException IOException) { LOGGER.error("Unable to authenticate Google Account: ", IOException); }
				return null;
			}
		});
    	MimeMessage mimeMessage = new MimeMessage(messageSession);
		mimeMessage.setSubject(subject);
		mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
		mimeMessage.setContent(content, "text/html; charset=utf-8");
		Transport.send(mimeMessage);
		properties.clear();
		LOGGER.info("Email correctly sent to ", email);
    }

    public static ApplicationProperties getInstance(){
        if(istance == null) { istance = new ApplicationProperties(); }
        return istance;
    }

    public String getUriServer() throws IOException {
        return (String) prop.get("uriServer");
    }

    public String getUriMySql() throws IOException {
        return (String) prop.get("uriMySql");
    }

    public String getUserMySql() throws IOException {
        return (String) prop.get("userMySql");
    }

    public String getPswMySql() throws IOException {
        return (String) prop.get("pswMySql");
    }

    public String getUserMail() throws IOException {
        return (String) prop.get("userMail");
    }

    public String getPswMail() throws IOException {
        return (String) prop.get("pswMail");
    }
}

