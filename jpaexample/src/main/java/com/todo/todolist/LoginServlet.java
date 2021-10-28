package com.todo.todolist;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private ApplicationProperties appProp = ApplicationProperties.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received login request");
        response.setContentType("text/html");

        String message = "";
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(appProp.getUriMySql(), appProp.getUserMySql(), appProp.getPswMySql());
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next() && PasswordManager.validatePassword(password, resultSet.getString("password"))) {
                message = resultSet.getString("name") + " " + resultSet.getString("surname");
                request.setAttribute("message", message);
                request.getRequestDispatcher("main.jsp").forward(request, response);
            }
            else {
                request.setAttribute("message", "ERROR: No user found! :(");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Error during login: ", e);
        }

    }
}
