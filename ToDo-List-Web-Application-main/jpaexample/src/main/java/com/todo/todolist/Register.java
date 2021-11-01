package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.KeyGenerator;
import javax.mail.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);
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
				LOGGER.error("Error inserting data into the table: " + Exception.getStackTrace().toString());
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
			LOGGER.error("The secret code is invalid: ", new RuntimeException());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		name = request.getParameter("firstName");
		surname = request.getParameter("lastName");
		email = request.getParameter("inputEmail");
		inputPassword = request.getParameter("inputPassword");
		repeatPassword = request.getParameter("repeatPassword");

		if(inputPassword.equals(repeatPassword)){
			try {
				secretKey = PasswordManager.createHash(KeyGenerator.getInstance("AES").generateKey().toString()).split(":")[1];
				String content =
						"<h3>By clicking on the link you accept the processing of your data for access to our service, "
								+ "thank you.</h3><a href='" + appProp.getUriServer() + "/register?secret=" + this.secretKey + "'>"
								+"Confirm registration</a>";
				ApplicationProperties.sendEmail("Confirm new user registration", email, content);
				request.setAttribute("message", "Hey " + name + "We have sent you a confirmation email: check your GMail");
				request.getServletContext().getRequestDispatcher("/login.jsp" ).include(request,response);
			} catch (MessagingException | NoSuchAlgorithmException Exceptions) {
				LOGGER.error("Error while sending the e-mail: ", Exceptions);
			}
		}
	}
}
