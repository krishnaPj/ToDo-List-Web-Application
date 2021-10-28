package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received login request");
        response.setContentType("text/html");

        String message = "", name = "", surname = "";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
		EntityManager em = null;
        
        try {
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<SampleEntity> tq = em.createQuery("SELECT t FROM test t WHERE t.email = :email", SampleEntity.class);
        	tq.setParameter("email", email);
        	List<SampleEntity> test = tq.getResultList();
        	
        	if (!test.isEmpty()) {
                String pwd = test.get(0).getPassword();
                if(PasswordManager.validatePassword(password, pwd)) {
                    name = test.get(0).getName();
                    surname = test.get(0).getSurname();
                    message = name + " " + surname; 
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("main.jsp").forward(request, response);
                }
                else {
                	request.setAttribute("message", "Error during login: invalid credentials :(");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        	else {
        		request.setAttribute("message", "Error during login: invalid credentials :(");
                request.getRequestDispatcher("login.jsp").forward(request, response);
        	}
        	
        } catch (Exception e) {
            LOGGER.error("Error during login :(", e);
        }
    }
	
}
