package net.web;

import java.io.*;
import java.util.List;
import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.entities.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.core.PersistenceUtility;

@WebServlet(name = "RemoveSelected", value = "/RemoveSelected")
public class RemoveSelected extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
	private static String message = "";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = null;      
        try {
			em = PersistenceUtility.createEntityManager();
			em.getTransaction().begin();
			Query deletedQuery = em.createQuery("DELETE FROM Lista l WHERE l.id = :id");
			deletedQuery.setParameter("id", Integer.parseInt(request.getParameter("idItem")));
			deletedQuery.executeUpdate();
			em.getTransaction().commit();
			message = "";
			em = null;
        	em = PersistenceUtility.createEntityManager();
        	TypedQuery<Lista> tq = em.createQuery("SELECT l FROM Lista l", Lista.class);
        	List<Lista> lista = tq.getResultList(); 
        	
        	if(lista.size() > 0) {
	        	for(int i = 0; i < lista.size(); i++) {
	        		message += "<div class=\"card text-center mx-auto rounded-pill\" style=\"width:500px;margin-top: 10px;\">\r\n"
	        				+ "  <div class=\"card-body\">\r\n"
	        				+ "    <h5 class=\"card-title\" style=\"color:#743CCE>"
	        				+ "<span class=\"text-center\" style=\"color: #743CCE;\">\r\n" + lista.get(i).getContent()
	        				+ "			</span></h5>"
	        				+ "<a title='Remove this' style=\"color: #743CCE;\" href='./RemoveSelected?idItem=" + lista.get(i).getId() +"'>X</a>"
	        				+ "  </div>"     				
	        				+ "</div>" + ("</br>");
	        	}
        	}
        	else {
        		message += "<h1 class='text-center' style='color: #743CCE;'>Your list is empty</h1>";
        	}
        	request.setAttribute("content", message);
            request.getRequestDispatcher("main.jsp").forward(request, response);
        } catch (Exception Exception) {
        	LOGGER.error("Error during DELETE: ", Exception);
        }
    }

}
