package com.todo.todolist;

import net.agmsolutions.app.PersistenceUtility;
import net.agmsolutions.entities.SampleEntity;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ChangePassword extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private String message = "";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	EntityManager em = null;
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String repeatpassword = request.getParameter("repeatPassword");
            
            if(password.equals(repeatpassword)) {
            	em = PersistenceUtility.createEntityManager();
            	TypedQuery<SampleEntity> tq = em.createQuery("SELECT t FROM test t WHERE t.email = :email", SampleEntity.class);
            	tq.setParameter("email", email);
            	List<SampleEntity> test = tq.getResultList();
            	if (!test.isEmpty()) {
            		int id = test.get(0).getId();
                	em.getTransaction().begin();
                	Query query = em.createQuery("UPDATE test t SET password = :password WHERE id = :id");
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
        } catch (Exception Exception) {
        	Exception.printStackTrace();
        }
    }
}
