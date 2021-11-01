package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import java.io.IOException;
import java.util.List;

import javax.mail.*;
import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ForgotServlet", value = "/ForgotServlet")
public class ForgotPassword extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPassword.class);
	private ApplicationProperties appProp = ApplicationProperties.getInstance();
    private String message = "";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	EntityManager em = null;
        try {
            String email = request.getParameter("email"); 
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<SampleEntity> tq = em.createQuery("SELECT t FROM test t WHERE t.email = :email", SampleEntity.class);
        	tq.setParameter("email", email);
        	List<SampleEntity> test = tq.getResultList();
        	
            if (!test.isEmpty()) {  
				String HTMLCode = "<h3>Click this link to change your password: </h3>"
									+ "<a href='" + appProp.getUriServer() + "/change-password.jsp?email=" + email + "'>"
									+ "Change password</a>";
				ApplicationProperties.sendEmail("Password recovery", email, HTMLCode);
            }
            else {
            	message = "No existing mail in system!";
                request.setAttribute("message", message);
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        } catch (MessagingException MessagingException) {
        	LOGGER.error("Error while sending the e-mail: ", MessagingException);
        }
    }

}
