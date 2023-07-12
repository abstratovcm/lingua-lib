package com.penguin.servlet;

import com.penguin.model.User;
import com.penguin.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserRepository userRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userRepository.findUserByUsername(username);
            if (user == null) {
                request.setAttribute("error", "Invalid username");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            String hashedPassword = new Sha256Hash(password).toHex();

            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, hashedPassword);
                currentUser.login(token);
            }
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            request.setAttribute("error", "Invalid password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
