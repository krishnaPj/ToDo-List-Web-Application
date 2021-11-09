package net.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.core.PersistenceUtility;
import net.entities.*;
import net.utils.PasswordManager;
import java.io.IOException;
import java.util.List;
import javax.persistence.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email"), password = request.getParameter("password");
		EntityManager em = null;      
        try {
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<User> tq = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        	tq.setParameter("email", email);
        	List<User> user = tq.getResultList();
        	if (user.size() > 0 && PasswordManager.validatePassword(password, user.get(0).getPassword())) {
            	request.setAttribute("name", user.get(0).getName());
                request.getRequestDispatcher("main.jsp").forward(request, response);
        	}
        	else {
            	request.setAttribute("message", "Error during login: invalid credentials");
                request.getRequestDispatcher("login.jsp").forward(request, response);
        	}	
        } catch (Exception Exception) { LOGGER.error("Error during login: ", Exception); }
    }
}
