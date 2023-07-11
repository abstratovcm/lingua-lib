package com.penguin.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.penguin.model.Sentence;
import com.penguin.model.Translation;
import com.penguin.repository.SentenceRepository;
import com.penguin.repository.TranslationRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TranslationServlet", urlPatterns = { "/translation" })
public class TranslationServlet extends HttpServlet {

    @EJB
    private TranslationRepository translationRepository;

    @EJB
    private SentenceRepository sentenceRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Long id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Long.parseLong(request.getParameter("id"))
                : null;
        Long originalSentenceId = request.getParameter("originalSentenceId") != null
                && !request.getParameter("originalSentenceId").isEmpty()
                        ? Long.parseLong(request.getParameter("originalSentenceId"))
                        : null;
        Long translatedSentenceId = request.getParameter("translatedSentenceId") != null
                && !request.getParameter("translatedSentenceId").isEmpty()
                        ? Long.parseLong(request.getParameter("translatedSentenceId"))
                        : null;
        String comments = request.getParameter("comments");

        switch (action) {
            case "add":
                Translation newTranslation = new Translation();
                newTranslation.setComments(comments);
                translationRepository.save(newTranslation, originalSentenceId, translatedSentenceId);
                break;
            case "update":
                translationRepository.update(id, originalSentenceId, translatedSentenceId, comments);
                break;
            case "delete":
                translationRepository.delete(id);
                break;
        }

        response.sendRedirect("translation");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Translation> translations = translationRepository.findAll();
        List<Sentence> allSentences = sentenceRepository.findAll();

        request.setAttribute("translations", translations);
        request.setAttribute("allSentences", allSentences);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/translations.jsp");
        dispatcher.forward(request, response);
    }
}
