package com.todo.todolist;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ForgotServlet", value = "/ForgotServlet")
public class ForgotServlet extends HttpServlet {

    private ApplicationProperties appProp = ApplicationProperties.getInstance();
    private String idUser;
    private String newPass;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(appProp.getUriMySql(), appProp.getUserMySql(), appProp.getPswMySql());
            String sql = "UPDATE users SET password=? WHERE idusers=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, this.newPass);
            statement.setString(2, this.idUser);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(appProp.getUriMySql(), appProp.getUserMySql(), appProp.getPswMySql());
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst() ) {
                String message = "No existing mail in system! :(";
                request.setAttribute("message", message);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else {
                while (resultSet.next()) {
                    idUser = resultSet.getString("idusers");
                    newPass = resultSet.getString("newPass");
                    String repPass = resultSet.getString("repPass");
                    if(newPass.equals(repPass)){
                        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                    }
                    else{
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Errors w/ur code! :(");
        }
    }
}
