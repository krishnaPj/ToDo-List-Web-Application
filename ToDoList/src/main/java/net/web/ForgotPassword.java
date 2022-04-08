package net.web;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.core.PersistenceUtility;
import net.entities.User;
import net.utils.ApplicationProperties;

@WebServlet(name = "ForgotServlet", value = "/ForgotServlet")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPassword.class);
	private ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    private String message = "";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    	EntityManager em = null;
        try {
            String email = request.getParameter("email"); 
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<User> tq = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        	tq.setParameter("email", email);
        	List<User> test = tq.getResultList();
        	
            if (!test.isEmpty()) {  
				final String HTMLCode = "<h3>Click this link to change your password: </h3><a href='" 
								+ applicationProperties.getUriServer() + "/change-password.jsp?email=" 
								+ email + "'>Change password</a>";
				ApplicationProperties.sendEmail("Password recovery", email, HTMLCode);
            }
            else {
            	message = "No existing mail in system!";
                request.setAttribute("message", message);
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        } catch (MessagingException messagingException) {
        	LOGGER.error("Error while sending the e-mail: ", messagingException);
        }
    }

}
