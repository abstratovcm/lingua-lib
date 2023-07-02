package com.penguin;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.InitialContext;

public class HelloServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            InitialContext ic = new InitialContext();
            DataSource dataSource = (DataSource) ic.lookup("java:/LanguageStudyDS");
            Connection connection = dataSource.getConnection();
            
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Test (id INT PRIMARY KEY, name VARCHAR(255))");
            statement.executeUpdate("INSERT INTO Test (id, name) VALUES (1, 'Test Name')");
            ResultSet rs = statement.executeQuery("SELECT * FROM Test");

            while(rs.next()){
                out.println("<h1>Id: " + rs.getInt("id") + ", Name: " + rs.getString("name") + "</h1>");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.println("<h1>Error: " + ex.getMessage() + "</h1>");
        } catch (NamingException ex) {
            ex.printStackTrace();
            out.println("<h1>Error: " + ex.getMessage() + "</h1>");
        }
    }
}