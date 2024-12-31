package net.web;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.core.PersistenceUtility;
import net.entities.User;
import net.entities.UserStatus;
import net.utils.ApplicationProperties;
import net.utils.PasswordManager;

@WebServlet(name = "RegisterServlet", value = "/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);
    private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            LOGGER.warn("Confirmation email parameter is missing.");
            request.setAttribute("message", "Invalid confirmation link.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        EntityManager em = null;
        try {
            em = PersistenceUtility.createEntityManager();
            em.getTransaction().begin();

            Query query = em.createQuery("UPDATE User u SET u.userStatus = :status WHERE u.email = :email");
            query.setParameter("status", UserStatus.CONFIRMED);
            query.setParameter("email", email);

            int updatedRows = query.executeUpdate();
            em.getTransaction().commit();

            if (updatedRows > 0) {
                request.setAttribute("message", "Account confirmed successfully!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                LOGGER.warn("No user found with the provided email for confirmation.");
                request.setAttribute("message", "Invalid confirmation link.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception exception) {
            LOGGER.error("Error during account confirmation: ", exception);
            request.setAttribute("message", "An error occurred during account confirmation. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("firstName");
        String surname = request.getParameter("lastName");
        String email = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");
        String repeatPassword = request.getParameter("repeatPassword");

        // Input validation
        if (name == null || surname == null || email == null || inputPassword == null || repeatPassword == null ||
            name.isEmpty() || surname.isEmpty() || email.isEmpty() || inputPassword.isEmpty() || repeatPassword.isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!inputPassword.equals(repeatPassword)) {
            request.setAttribute("message", "Passwords do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        EntityManager em = null;

        try {
            em = PersistenceUtility.createEntityManager();

            User user = new User(name, surname, email, PasswordManager.createHash(inputPassword), 
                                 UserStatus.NOT_CONFIRMED, new Date());

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            // Send confirmation email
            String content = "<h3>By clicking on the link you accept the processing of your data for"
                    + " access to our service, thank you.</h3><a href='" 
                    + applicationProperties.getUriServer() + "/register?email=" 
                    + email + "'>Confirm registration</a>";

            ApplicationProperties.sendEmail("Confirm New User Registration", email, content);

            request.setAttribute("message", "Registration successful! Please check your email to confirm your account.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception exception) {
            LOGGER.error("Error during registration: ", exception);
            request.setAttribute("message", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}