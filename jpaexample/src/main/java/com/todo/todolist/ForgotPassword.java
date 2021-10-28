package com.todo.todolist;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;
import javax.crypto.KeyGenerator;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;

@WebServlet(name = "ForgotServlet", value = "/ForgotServlet")
public class ForgotPassword extends HttpServlet{
	
	private static final long serialVersionUID = -1956077038103846785L;
	private ApplicationProperties appProp = ApplicationProperties.getInstance();
    private String message = "", secretKey = "";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	EntityManager em = null;
    	
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String repeatpassword = request.getParameter("repeatPassword");
            
            if(password.equals(repeatpassword)) {
            	em = PersistenceUtility.createEntityManager();
            	em.getTransaction().begin();
            	Query query = em.createQuery("UPDATE test t SET password = :pwd WHERE email = :email");      
            	query.setParameter("password", password);
            	query.setParameter("email", email);
            	em.getTransaction().commit();
            	request.getRequestDispatcher("login.jsp");
            }
            else {
            	message = "Passwords not same";
                request.setAttribute("message", message);
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		Session messageSession = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					return new PasswordAuthentication(appProp.getUserMail(), appProp.getPswMail());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
    	
    	EntityManager em = null;
    	
        try {
            String email = request.getParameter("email");
            
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<SampleEntity> tq = em.createQuery("SELECT t FROM test t WHERE t.email = :email", SampleEntity.class);
        	tq.setParameter("email", email);
        	List<SampleEntity> test = tq.getResultList();
        	
            if (!test.isEmpty()) {  
            	MimeMessage mimeMessage = new MimeMessage(messageSession);
				mimeMessage.setSubject("Password recovery");
				mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));

				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				keyGen.init(128);
				secretKey = keyGen.generateKey().toString();
				
				String HTMLCode =
						"<h3>Click this link to change your password: "
								+ "thank you.</h3><a href='" + appProp.getUriServer() + "/change-password.jsp?email=" + email + "'>"
								+"Change password" +
								"</a>";
				mimeMessage.setContent(HTMLCode, "text/html; charset=utf-8");
				Transport.send(mimeMessage);
            }
            else {
            	message = "No existing mail in system!";
                request.setAttribute("message", message);
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        } catch (MessagingException | NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
    }

}
