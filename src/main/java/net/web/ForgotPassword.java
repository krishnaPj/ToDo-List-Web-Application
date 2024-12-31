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
    private final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = null;

        try {
            String email = request.getParameter("email");

            if (email == null || email.isEmpty()) {
                request.setAttribute("message", "Email address cannot be empty. Please try again.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }

            em = PersistenceUtility.createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> users = query.getResultList();

            if (!users.isEmpty()) {
                String resetLink = applicationProperties.getUriServer() + "/change-password.jsp?email=" + email;
                String emailContent = "<h3>Click the link below to reset your password:</h3>" +
                                      "<a href='" + resetLink + "'>Reset Password</a>";

                ApplicationProperties.sendEmail("Password Recovery", email, emailContent);

                LOGGER.info("Password recovery email sent to: {}", maskEmail(email));
                request.setAttribute("message", "Password recovery email sent. Please check your inbox.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            } else {
                LOGGER.warn("Password recovery requested for non-existing email: {}", maskEmail(email));
                request.setAttribute("message", "No account is associated with the provided email address.");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error during password recovery: ", e);
            request.setAttribute("message", "An unexpected error occurred. Please try again later.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Masks the email address for logging purposes.
     * Example: user@example.com -> u***@example.com
     *
     * @param email The email address to mask.
     * @return The masked email address.
     */
    private String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "unknown";
        int atIndex = email.indexOf("@");
        if (atIndex > 1) {
            return email.charAt(0) + "***" + email.substring(atIndex);
        }
        return "masked-email";
    }
}