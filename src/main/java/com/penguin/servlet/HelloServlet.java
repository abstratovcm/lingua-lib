package com.penguin.servlet;

import com.penguin.model.Sentence;
import com.penguin.repository.SentenceRepository;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import jakarta.ejb.EJB;

public class HelloServlet extends HttpServlet {
    
    @EJB
    private SentenceRepository repo;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (repo == null) {
            out.println("<h1>Error: repo object is null</h1>");
            return;
        }

        Sentence sentence = null;
        try {
            // Inserting a sentence
            sentence = new Sentence("你好，世界");
            repo.save(sentence);
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("<h1>Error: " + ex.getMessage() + "</h1>");
        }

        try {
            // Fetching a sentence
            Sentence fetchedSentence = repo.find(sentence.getId());
            out.println("<h1>Id: " + fetchedSentence.getId() + ", Sentence: " + fetchedSentence.getMandarinSentence() + "</h1>");
        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("<h1>Error: " + ex.getMessage() + "</h1>");
        }
    }
}