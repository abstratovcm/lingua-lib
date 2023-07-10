package com.penguin.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.penguin.model.Sentence;
import com.penguin.repository.SentenceRepository;

import jakarta.ejb.EJB;
import java.io.IOException;

@WebServlet(urlPatterns = "/sentence")
public class SentenceServlet extends HttpServlet {

    @EJB
    private SentenceRepository sentenceRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // set response content type and character encoding
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("sentences", sentenceRepository.findAll());
        request.getRequestDispatcher("/sentences.jsp").forward(request, response);
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
                sentenceRepository.delete(idDelete);
                break;
            case "edit":
                Long idEdit = Long.parseLong(request.getParameter("id"));
                Sentence sentenceToEdit = sentenceRepository.find(idEdit);
                request.setAttribute("sentenceToEdit", sentenceToEdit);
                break;
            case "update":
                Long idUpdate = Long.parseLong(request.getParameter("id"));
                String mandarinUpdate = request.getParameter("mandarinSentence");
                sentenceRepository.update(idUpdate, mandarinUpdate);
                break;
            default:
                String mandarinAdd = request.getParameter("mandarinSentence");
                Sentence sentenceToAdd = new Sentence();
                sentenceToAdd.setMandarinSentence(mandarinAdd);
                sentenceRepository.save(sentenceToAdd);
                break;
        }

        response.sendRedirect("sentence");
    }
}
