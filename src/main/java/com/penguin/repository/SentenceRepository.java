package com.penguin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;
import com.penguin.model.Sentence;

@Stateless
public class SentenceRepository {

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    public void save(Sentence sentence) {
        try {
            entityManager.persist(sentence);
        } catch (Exception e) {
            // log the error with more details
            System.err.println("Error occurred when saving the sentence: " + e.getMessage());
            e.printStackTrace();
            // propagate the error
            throw new RuntimeException(e);
        }
    }

    public Sentence find(Long id) {
        try {
            return entityManager.find(Sentence.class, id);
        } catch (Exception e) {
            System.err.println("Error occurred when finding the sentence: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            Sentence sentence = find(id);
            if (sentence != null && entityManager != null) {
                entityManager.remove(sentence);
            }
        } catch (Exception e) {
            System.err.println("Error occurred when deleting the sentence: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, String newSentence) {
        try {
            Sentence sentence = find(id);
            if (sentence != null) {
                sentence.setMandarinSentence(newSentence);
                entityManager.merge(sentence);
            }
        } catch (Exception e) {
            System.err.println("Error occurred when updating the sentence: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
