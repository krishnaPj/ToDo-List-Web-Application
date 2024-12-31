package net.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage application properties and email sending functionality.
 */
public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
    private static final String PROPERTIES_FILE = "bundle.properties";
    private final Properties properties = new Properties();

    // Private constructor to load properties (Singleton Pattern)
    private ApplicationProperties() {
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            if (inputStream == null) {
                throw new FileNotFoundException("Property file '" + PROPERTIES_FILE + "' not found in the classpath.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Failed to load properties file: {}", PROPERTIES_FILE, e);
            throw new RuntimeException("Could not initialize properties.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.warn("Failed to close input stream for properties file: {}", PROPERTIES_FILE, e);
                }
            }
        }
    }

    // Singleton Helper Class
    private static class SingletonHelper {
        private static final ApplicationProperties INSTANCE = new ApplicationProperties();
    }

    /**
     * Returns the singleton instance of ApplicationProperties.
     */
    public static ApplicationProperties getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Retrieves a property value by key.
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Accessor methods for specific properties

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

    /**
     * Sends an email using the configured SMTP settings.
     *
     * @param subject        the email subject
     * @param recipientEmail the recipient's email address
     * @param content        the email content
     */
    public static void sendEmail(String subject, String recipientEmail, String content) {
        try {
            ApplicationProperties appProp = getInstance();

            // Email configuration properties
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

            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(appProp.getUserMail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // Send email
            Transport.send(message);
            LOGGER.info("Email sent successfully to {}", recipientEmail);

        } catch (MessagingException e) {
            LOGGER.error("Failed to send email to {}: {}", recipientEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send email.", e);
        }
    }
}