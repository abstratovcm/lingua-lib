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
            System.out.println(e);
        }
    }

    public Sentence find(Long id) {
        try {
            return entityManager.find(Sentence.class, id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void delete(Long id) {
        try {
            Sentence sentence = find(id);
            if (sentence != null && entityManager != null) {
                entityManager.remove(sentence);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update(Long id, String newSentence) {
        try {
            Sentence sentence = find(id);
            if (sentence != null) {
                sentence.setMandarinSentence(newSentence);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}