package net.web;

import java.io.IOException;
import java.util.List;

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
import net.utils.PasswordManager;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = "", email = request.getParameter("email"), 
        	   password = request.getParameter("password");
		EntityManager em = null;      
        try {
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<User> tq = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        	tq.setParameter("email", email);
        	List<User> test = tq.getResultList();
        	
        	if (!test.isEmpty()) {
                String pwd = test.get(0).getPassword();
                if(PasswordManager.validatePassword(password, pwd)) {
                    name = test.get(0).getName();
                    request.setAttribute("message", name.substring(0, 1).toUpperCase() + name.substring(1));
                    request.getRequestDispatcher("main.jsp").forward(request, response);
                }
                else {
                	request.setAttribute("message", "Error during login: invalid password");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        	else {
        		request.setAttribute("message", "Error during login: invalid email");
                request.getRequestDispatcher("login.jsp").forward(request, response);
        	}
        	
        } catch (Exception exception) {
        	LOGGER.error("Error during login: ", exception);
        }
    }
	
}
