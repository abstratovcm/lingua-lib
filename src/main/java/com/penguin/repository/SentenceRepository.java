package com.penguin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.penguin.model.Language;
import com.penguin.model.Sentence;

@Stateless
public class SentenceRepository {

    private static final Logger logger = LoggerFactory.getLogger(SentenceRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    @EJB
    private LanguageRepository languageRepository;

    public void save(Sentence sentence, String languageName) {
        try {
            if (sentence == null || languageName == null || languageName.isEmpty()) {
                throw new IllegalArgumentException("Sentence and language name must not be null or empty");
            }
            Language language = languageRepository.findByName(languageName);
            if (language == null) {
                throw new IllegalStateException("Language does not exist: " + languageName);
            }
            sentence.setLanguage(language);
            entityManager.persist(sentence);
        } catch (Exception e) {
            logger.error("Error occurred when saving the sentence", e);
            throw new RuntimeException(e);
        }
    }

    public Sentence find(Long id) {
        try {
            return entityManager.find(Sentence.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the sentence", e);
            throw new RuntimeException(e);
        }
    }

    public List<Sentence> findAll() {
        try {
            CriteriaQuery<Sentence> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Sentence.class);
            criteriaQuery.select(criteriaQuery.from(Sentence.class));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding all sentences", e);
            throw new RuntimeException(e);
        }
    }

    public List<Sentence> findAllEagerly() {
    try {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sentence> criteriaQuery = criteriaBuilder.createQuery(Sentence.class);
        Root<Sentence> root = criteriaQuery.from(Sentence.class);
        root.fetch("language", JoinType.LEFT); // Eagerly fetch the language association
        criteriaQuery.select(root);

        return entityManager.createQuery(criteriaQuery).getResultList();
    } catch (Exception e) {
        logger.error("Error occurred when finding all sentences", e);
        throw new RuntimeException(e);
    }
}

    public List<Sentence> findByLanguage(String languageName) {
        try {
            Language language = languageRepository.findByName(languageName);
            if (language == null) {
                throw new IllegalStateException("Language does not exist: " + languageName);
            }
            CriteriaQuery<Sentence> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Sentence.class);
            Root<Sentence> root = criteriaQuery.from(Sentence.class);
            criteriaQuery.select(root).where(entityManager.getCriteriaBuilder().equal(root.get("language"), language));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding sentences by language", e);
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
            logger.error("Error occurred when deleting the sentence", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, String newSentenceText, String languageName) {
        try {
            Sentence sentence = find(id);
            Language language = languageRepository.findByName(languageName);
            if (sentence != null && language != null) {
                sentence.setText(newSentenceText);
                sentence.setLanguage(language);
                entityManager.merge(sentence);
            }
        } catch (Exception e) {
            logger.error("Error occurred when updating the sentence", e);
            throw new RuntimeException(e);
        }
    }
}
