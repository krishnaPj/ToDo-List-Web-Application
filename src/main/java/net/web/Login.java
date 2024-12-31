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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Input validation
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("message", "Error: Email and Password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        EntityManager em = null;

        try {
            em = PersistenceUtility.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);

            List<User> users = query.getResultList();

            if (users.isEmpty()) {
                // No user found with the given email
                request.setAttribute("message", "Error: Invalid email.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                User user = users.get(0);
                if (PasswordManager.validatePassword(password, user.getPassword())) {
                    // Successful login
                    String name = user.getName();
                    String displayName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    request.setAttribute("message", displayName);
                    request.getRequestDispatcher("main.jsp").forward(request, response);
                } else {
                    // Invalid password
                    request.setAttribute("message", "Error: Invalid password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Error during login: ", exception);
            request.setAttribute("message", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}