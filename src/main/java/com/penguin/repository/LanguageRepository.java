package com.penguin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.penguin.model.Language;

@Stateless
public class LanguageRepository {

    private static final Logger logger = LoggerFactory.getLogger(LanguageRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    public void save(Language language) {
        try {
            if (language == null) {
                throw new IllegalArgumentException("Language must not be null");
            }
            entityManager.persist(language);
        } catch (Exception e) {
            logger.error("Error occurred when saving the language", e);
            throw new RuntimeException(e);
        }
    }

    public Language find(Long id) {
        try {
            return entityManager.find(Language.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the language", e);
            throw new RuntimeException(e);
        }
    }

    public Language findByName(String name) {
        try {
            CriteriaQuery<Language> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Language.class);
            Root<Language> root = criteriaQuery.from(Language.class);
            criteriaQuery.select(root).where(entityManager.getCriteriaBuilder().equal(root.get("name"), name));
            List<Language> languages = entityManager.createQuery(criteriaQuery).getResultList();
            if (!languages.isEmpty()) {
                return languages.get(0);
            }
        } catch (Exception e) {
            logger.error("Error occurred when finding the language by name", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Language> findAll() {
        try {
            CriteriaQuery<Language> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Language.class);
            criteriaQuery.select(criteriaQuery.from(Language.class));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding all languages", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            Language language = find(id);
            if (language != null && entityManager != null) {
                entityManager.remove(language);
            }
        } catch (Exception e) {
            logger.error("Error occurred when deleting the language", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Long id, String newName) {
        try {
            Language language = find(id);
            if (language != null) {
                language.setName(newName);
                entityManager.merge(language);
            }
        } catch (Exception e) {
            logger.error("Error occurred when updating the language", e);
            throw new RuntimeException(e);
        }
    }
}
