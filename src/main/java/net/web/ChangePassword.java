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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = null;

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String repeatPassword = request.getParameter("repeatPassword");

            if (password == null || password.isEmpty() || repeatPassword == null || repeatPassword.isEmpty()) {
                request.setAttribute("message", "Passwords cannot be empty. Please retry.");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            if (!password.equals(repeatPassword)) {
                request.setAttribute("message", "Passwords do not match. Please retry.");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            em = PersistenceUtility.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> users = query.getResultList();

            if (users.isEmpty()) {
                request.setAttribute("message", "No user found with the provided email.");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            User user = users.get(0);
            em.getTransaction().begin();

            String hashedPassword = PasswordManager.createHash(password);
            Query updateQuery = em.createQuery("UPDATE User u SET u.password = :password WHERE u.id = :id");
            updateQuery.setParameter("password", hashedPassword);
            updateQuery.setParameter("id", user.getId());
            updateQuery.executeUpdate();

            em.getTransaction().commit();

            request.setAttribute("message", "Password successfully changed.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.error("Error while changing password: ", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            request.setAttribute("message", "An error occurred. Please try again later.");
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}