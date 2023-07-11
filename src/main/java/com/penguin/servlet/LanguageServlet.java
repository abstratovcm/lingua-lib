package com.penguin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.penguin.model.Language;
import com.penguin.repository.LanguageRepository;

import jakarta.ejb.EJB;
import java.io.IOException;

@WebServlet(urlPatterns = "/language")
public class LanguageServlet extends HttpServlet {

    @EJB
    private LanguageRepository languageRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set response content type and character encoding
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("languages", languageRepository.findAll());
        request.getRequestDispatcher("/languages.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set request character encoding
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");

        // set response content type and character encoding
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        switch (action == null ? "" : action) {
            case "delete":
                Long idDelete = Long.parseLong(request.getParameter("id"));
                languageRepository.delete(idDelete);
                break;
            default:
                String nameAdd = request.getParameter("name");
                Language languageToAdd = new Language();
                languageToAdd.setName(nameAdd);
                languageRepository.save(languageToAdd);
                break;
        }

        response.sendRedirect("language");
    }
}
