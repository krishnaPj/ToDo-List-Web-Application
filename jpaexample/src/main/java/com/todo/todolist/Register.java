package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import javax.persistence.EntityManager;
import javax.crypto.KeyGenerator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@WebServlet(name = "RegisterServlet", value = "/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ApplicationProperties appProp = ApplicationProperties.getInstance();
	private String name = "", surname = "", email = "", inputPassword = "", repeatPassword = "", secretKey = "";

	private void insertData(String secret) {
		EntityManager em = null;
		if(secret.equals(this.secretKey)) {
			try {
				em = PersistenceUtility.createEntityManager();
				SampleEntity se = new SampleEntity();
				se.setName(name);
				se.setSurname(surname);
				se.setEmail(email);
				se.setPassword(PasswordManager.createHash(inputPassword));
				em.getTransaction().begin();
				em.persist(se); 
				em.getTransaction().commit();
			}
			catch (Exception Exception) {
				Exception.printStackTrace();
				if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String secret = request.getParameter("secret");
		if(secret != null) {
			insertData(secret);
			request.setAttribute("message", "");
			request.getServletContext().getRequestDispatcher("/login.jsp" ).include(request,response);
		}
		else {
			throw new RuntimeException("Wrong secret key");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		name = request.getParameter("firstName");
		surname = request.getParameter("lastName");
		email = request.getParameter("inputEmail");
		inputPassword = request.getParameter("inputPassword");
		repeatPassword = request.getParameter("repeatPassword");

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

		if(inputPassword.equals(repeatPassword)){
			try {
				MimeMessage mimeMessage = new MimeMessage(messageSession);
				mimeMessage.setSubject("Confirm user");
				mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				keyGen.init(128);
				secretKey = keyGen.generateKey().toString();
				String HTMLCode =
						"<h3>By clicking on the link you accept the processing of your data for access to our service, "
								+ "thank you.</h3><a href='" + appProp.getUriServer() + "/register?secret=" + this.secretKey + "'>"
								+"Confirm registration" +
								"</a>";
				mimeMessage.setContent(HTMLCode, "text/html; charset=utf-8");
				Transport.send(mimeMessage);
			} catch (MessagingException | NoSuchAlgorithmException Exceptions) {
				Exceptions.printStackTrace();
			}
		}
	}



}
