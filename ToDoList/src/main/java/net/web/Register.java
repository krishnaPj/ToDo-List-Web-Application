package net.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.core.PersistenceUtility;
import net.entities.*;
import net.utils.*;
import jakarta.servlet.ServletException;
import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "RegisterServlet", value = "/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);
	private ApplicationProperties appProp = ApplicationProperties.getInstance();
	private String name = "", surname = "", email = "", inputPassword = "", repeatPassword = "";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		EntityManager em = null;
		em = PersistenceUtility.createEntityManager();
		em.getTransaction().begin();
    	Query query = em.createQuery("UPDATE User u SET u.userStatus = :status WHERE email = :email");
    	query.setParameter("status", UserStatus.CONFIRMED);
    	query.setParameter("email", email);
    	query.executeUpdate();
    	em.getTransaction().commit();
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
				String content = "<h3>By clicking on the link you accept the processing of your data for"
								 + " access to our service, thank you.</h3><a href='" 
								 + appProp.getUriServer() + "/register?email=" + email + ">Confirm registration</a>";
				ApplicationProperties.sendEmail("Confirm new user registration", email, content);
				EntityManager em = null;
				em = PersistenceUtility.createEntityManager();
				User user = new User(name, surname, email, PasswordManager.createHash(inputPassword), 
									 UserStatus.NOT_CONFIRMED, new Date());
				em.getTransaction().begin();
				em.persist(user); 
				em.getTransaction().commit();
			} catch (MessagingException Exceptions) {
				LOGGER.error("Error while sending the e-mail: ", Exceptions);
			}
		}
	}
}
