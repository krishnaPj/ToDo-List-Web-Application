package net.utils;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
    private static final String PROPERTIES_FILE = "bundle.properties";
    private final Properties properties = new Properties();

    // Singleton instance holder (Thread-Safe)
    private ApplicationProperties() {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new IOException("Property file '" + PROPERTIES_FILE + "' not found in the classpath.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to load properties file: {}", PROPERTIES_FILE, e);
            throw new RuntimeException("Could not initialize properties.", e);
        }
    }

    private static class SingletonHelper {
        private static final ApplicationProperties INSTANCE = new ApplicationProperties();
    }

    public static ApplicationProperties getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Generic property retrieval with default value support
    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Accessors for specific properties
    public String getUriServer() {
        return getProperty("uriServer");
    }

    public String getUriMySql() {
        return getProperty("uriMySql");
    }

    public String getUserMySql() {
        return getProperty("userMySql");
    }

    public String getPswMySql() {
        return getProperty("pswMySql");
    }

    public String getUserMail() {
        return getProperty("userMail");
    }

    public String getPswMail() {
        return getProperty("pswMail");
    }

    // Method to send an email
    public static void sendEmail(String subject, String recipientEmail, String content) {
        try {
            ApplicationProperties appProp = getInstance();

            // Email configuration
            Properties emailProperties = new Properties();
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.put("mail.smtp.host", "smtp.gmail.com");
            emailProperties.put("mail.smtp.port", "587");

            // Email session with authentication
            Session session = Session.getInstance(emailProperties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(appProp.getUserMail(), appProp.getPswMail());
                }
            });

            // Creating email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appProp.getUserMail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // Sending email
            Transport.send(message);
            LOGGER.info("Email sent successfully to {}", recipientEmail);

        } catch (MessagingException e) {
            LOGGER.error("Failed to send email: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send email.", e);
        }
    }
}