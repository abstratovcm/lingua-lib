package com.penguin.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penguin.model.UserRole;

import java.util.List;

@Stateless
public class UserRoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    public UserRole save(UserRole userRole) {
        try {
            entityManager.persist(userRole);
            return userRole;
        } catch (Exception e) {
            logger.error("Error occurred when saving the user role", e);
            throw new RuntimeException(e);
        }
    }

    public UserRole find(Long id) {
        try {
            return entityManager.find(UserRole.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the user role", e);
            throw new RuntimeException(e);
        }
    }

    public List<UserRole> findByUserId(Long userId) {
        try {
            CriteriaQuery<UserRole> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(UserRole.class);
            Root<UserRole> root = criteriaQuery.from(UserRole.class);
            criteriaQuery.select(root).where(entityManager.getCriteriaBuilder().equal(root.get("user").get("id"), userId));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding the user role by user id", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            UserRole userRole = find(id);
            if (userRole != null) {
                entityManager.remove(userRole);
            }
        } catch (Exception e) {
            logger.error("Error occurred when deleting the user role", e);
            throw new RuntimeException(e);
        }
    }

    public UserRole update(UserRole userRole) {
        try {
            return entityManager.merge(userRole);
        } catch (Exception e) {
            logger.error("Error occurred when updating the user role", e);
            throw new RuntimeException(e);
        }
    }

    public List<UserRole> findAll() {
        try {
            CriteriaQuery<UserRole> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(UserRole.class);
            criteriaQuery.select(criteriaQuery.from(UserRole.class));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding all user roles", e);
            throw new RuntimeException(e);
        }
    }
}
