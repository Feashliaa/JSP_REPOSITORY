package com.example.blage_website_jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.mindrot.jbcrypt.BCrypt;


@WebServlet(name = "loginServlet", value = "/login")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (checkCredentials(username, password)) {
                response.setContentType("application/xml");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<result>true</result>");
            } else {
                response.setContentType("application/xml");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<result>false</result>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("false");
        }
    }

    public static boolean authenticatePassword(String plaintextPassword, String storedHash) {
        // Generate a new hash from the provided plaintext password
        String newHash = BCrypt.hashpw(plaintextPassword, storedHash);

        // Compare the new hash to the stored hash
        return BCrypt.checkpw(plaintextPassword, storedHash);
    }

    public boolean checkCredentials(String username, String password) throws ClassNotFoundException, SQLException {

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
        // select from users where username = ?
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ?");

        // Set the username
        ps.setString(1, username);

        // Execute the query
        ResultSet rs = ps.executeQuery();

        // Check if the user exists
        if (rs.next()) {
            // Get the stored hash
            String storedHash = rs.getString("password");
            // Check if the password is correct
            if (authenticatePassword(password, storedHash)) {
                // Return true if the password is correct
                return true;
            } else {
                // Return false if the password is incorrect
                return false;
            }
        }
        // Return false if the user does not exist
        return false;
    }

    public void destroy() {
    }
}