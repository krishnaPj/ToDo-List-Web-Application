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
	private static String message = "";
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String content = request.getParameter("content");
		EntityManager em = null;      
        try {
			em = PersistenceUtility.createEntityManager();
			Lista list = new Lista();
			list.setContent(content);
			em.getTransaction().begin();
			
			em.persist(list); 
			em.getTransaction().commit();
			
			em = null;
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<Lista> tq = em.createQuery("SELECT l FROM Lista l", Lista.class);
        	List<Lista> lista = tq.getResultList();
        	
        	PrintWriter printWriter = response.getWriter();
			message = lista.get(lista.size() - 1).getContent();
			for(int i = 0; i < lista.size(); i++) {
				printWriter.println("<b>" + lista.get(i).getContent() + "</b><br>");
			}
        	request.setAttribute("content", message);
            request.getRequestDispatcher("main.jsp");
        } catch (Exception exception) {
        	LOGGER.error("Error during inserting data: ", exception);
        }
    }

}
