package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ForgotServlet", value = "/ForgotServlet")
public class ForgotPassword extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ApplicationProperties appProp = ApplicationProperties.getInstance();
    private String message = "";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", "smtp.gmail.com");

		Session messageSession = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				try { return new PasswordAuthentication(appProp.getUserMail(), appProp.getPswMail()); }
				catch (IOException IOException) { IOException.printStackTrace(); }
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
				String HTMLCode = "<h3>Click this link to change your password: </h3>"
									+ "<a href='" + appProp.getUriServer() + "/change-password.jsp?email=" + email + "'>"
									+ "Change password</a>";
				mimeMessage.setContent(HTMLCode, "text/html; charset=utf-8");
				Transport.send(mimeMessage);
            }
            else {
            	message = "No existing mail in system!";
                request.setAttribute("message", message);
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        } catch (MessagingException MessagingException) {
        	MessagingException.printStackTrace();
        }
    }

}
