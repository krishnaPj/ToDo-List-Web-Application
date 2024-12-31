package net.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import net.entities.Lista;

@WebServlet(name = "InsertData", value = "/InsertData")
public class InsertData extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InsertData.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");
        if (content == null || content.trim().isEmpty()) {
            LOGGER.warn("Content parameter is missing or empty.");
            response.getWriter().println("<b>Error: Content cannot be empty.</b>");
            return;
        }

        EntityManager em = null;

        try {
            // Persist the new content
            em = PersistenceUtility.createEntityManager();
            em.getTransaction().begin();
            
            Lista list = new Lista();
            list.setContent(content);
            em.persist(list);
            em.getTransaction().commit();
            
            // Retrieve all entries from the database
            TypedQuery<Lista> tq = em.createQuery("SELECT l FROM Lista l", Lista.class);
            List<Lista> lista = tq.getResultList();
            
            // Build the response with all entries
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            writer.println("<h3>List of Items:</h3>");
            for (Lista item : lista) {
                writer.println("<b>" + item.getContent() + "</b><br>");
            }

            // Set attributes and forward to JSP
            request.setAttribute("content", content);
            request.getRequestDispatcher("main.jsp").forward(request, response);

        } catch (Exception exception) {
            LOGGER.error("Error during inserting data: ", exception);
            response.getWriter().println("<b>Error: Unable to process the request.</b>");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}