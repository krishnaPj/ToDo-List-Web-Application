package net.web;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import net.utils.PasswordManager;

@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePassword.class);
    private String message = "";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	EntityManager em = null;
        try {
        	String email = request.getParameter("email"),
        		   password = request.getParameter("password"),
        		   repeatpassword = request.getParameter("repeatPassword");
	            
            if(password.equals(repeatpassword)) {
            	em = PersistenceUtility.createEntityManager();
            	TypedQuery<User> tq = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            	tq.setParameter("email", email);
            	List<User> test = tq.getResultList();
            	
            	if (!test.isEmpty()) {
            		int id = test.get(0).getId();
                	em.getTransaction().begin();
                	
                	Query query = em.createQuery("UPDATE User u SET password = :password WHERE id = :id");
                	password = PasswordManager.createHash(repeatpassword);
                	query.setParameter("password", password);
                	query.setParameter("id", id);
                	
                	query.executeUpdate();
                	em.getTransaction().commit();
                	
                	request.getRequestDispatcher("login.jsp").forward(request, response);
            	}
            }
            else {
            	message = "Passwords are different: please retry";
                request.setAttribute("message", message);
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
            }
        }  
    	catch (Exception Exception) {
    		LOGGER.error("Error while changing password: ", Exception);
    	}
    }
}
