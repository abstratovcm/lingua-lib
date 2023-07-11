package com.penguin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.penguin.model.Sentence;
import com.penguin.model.Translation;

@Stateless
public class TranslationRepository {

    private static final Logger logger = LoggerFactory.getLogger(TranslationRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    @EJB
    private SentenceRepository sentenceRepository;

    public void save(Translation translation, Long originalSentenceId, Long translatedSentenceId) {
        try {
            if (translation == null || originalSentenceId == null || translatedSentenceId == null) {
                throw new IllegalArgumentException("Translation and sentence IDs must not be null");
            }
            Sentence originalSentence = sentenceRepository.find(originalSentenceId);
            Sentence translatedSentence = sentenceRepository.find(translatedSentenceId);
            if (originalSentence == null || translatedSentence == null) {
                throw new IllegalStateException("One or both sentences do not exist");
            }
            translation.setSentence1(originalSentence);
            translation.setSentence2(translatedSentence);
            entityManager.persist(translation);
        } catch (Exception e) {
            logger.error("Error occurred when saving the translation", e);
            throw new RuntimeException(e);
        }
    }

    public Translation find(Long id) {
        try {
            return entityManager.find(Translation.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the translation", e);
            throw new RuntimeException(e);
        }
    }

    public List<Translation> findAll() {
        try {
            CriteriaQuery<Translation> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Translation.class);
            criteriaQuery.select(criteriaQuery.from(Translation.class));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding all translations", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            Translation translation = find(id);
            if (translation != null && entityManager != null) {
                entityManager.remove(translation);
            }
        } catch (Exception e) {
            logger.error("Error occurred when deleting the translation", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, Long originalSentenceId, Long translatedSentenceId, String comments) {
        try {
            Translation translation = find(id);
            Sentence originalSentence = sentenceRepository.find(originalSentenceId);
            Sentence translatedSentence = sentenceRepository.find(translatedSentenceId);
            if (translation != null && originalSentence != null && translatedSentence != null) {
                translation.setSentence1(originalSentence);
                translation.setSentence2(translatedSentence);
                translation.setComments(comments);
                entityManager.merge(translation);
            }
        } catch (Exception e) {
            logger.error("Error occurred when updating the translation", e);
            throw new RuntimeException(e);
        }
    }
}
