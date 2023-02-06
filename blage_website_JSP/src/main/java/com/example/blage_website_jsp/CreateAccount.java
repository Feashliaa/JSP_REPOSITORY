package com.example.blage_website_jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "CreateAccount", value = "/CreateAccount")
public class CreateAccount extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String hashedPassword = generateHash(password);

        try {
            if (createAccount(username, hashedPassword)) {
                response.setContentType("application/xml");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<result>Created</result>");
            } else {
                response.setContentType("application/xml");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<result>Already Exists</result>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("false");
        }
    }

    public static String generateHash(String password) {
        // Generate a salt
        String salt = BCrypt.gensalt();
        // Hash the password using the salt
        String hash = BCrypt.hashpw(password, salt);
        // Return the hash
        return hash;
    }

    public boolean createAccount(String username, String password) throws ClassNotFoundException, SQLException {

        // Get the application's context
        ServletContext context = getServletContext();
        // Get the database name from the context
        String dbName = context.getInitParameter("dbName");
        // Get the database user from the context
        String dbUser = context.getInitParameter("dbUser");
        // Get the database password from the context
        String dbPass = context.getInitParameter("dbPass");

        // Get the JDBC driver and connect to the database
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Get a connection to the database
        Connection conn = DriverManager.getConnection(dbName, dbUser, dbPass);

        // Creating a prepared statement to prevent sql injection
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        // Setting the parameters of the prepared statement
        ps.setString(1, username);
        // Executing the query
        ResultSet rs = ps.executeQuery();
        // if the result set is empty, add the user to the database
        if (!rs.next()) {

            // Add the user to the database
            ps = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            return true;
        }
        // if the result set is not empty, return false
        return false;
    }
}
