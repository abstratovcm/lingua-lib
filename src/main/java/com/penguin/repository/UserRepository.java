package com.penguin.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penguin.model.User;

import java.util.List;

@Stateless
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    public User save(User user) {
        try {
            String hashedPassword = new Sha256Hash(user.getPassword()).toHex();
            user.setPassword(hashedPassword);
            entityManager.persist(user);
            return user;
        } catch (Exception e) {
            logger.error("Error occurred when saving the user", e);
            throw new RuntimeException(e);
        }
    }

    public User find(Long id) {
        try {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the user", e);
            throw new RuntimeException(e);
        }
    }

    public User findUserByUsername(String username) {
        try {
            CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(entityManager.getCriteriaBuilder().equal(root.get("username"), username));
            List<User> users = entityManager.createQuery(criteriaQuery).getResultList();
            if (!users.isEmpty()) {
                return users.get(0);
            }
        } catch (Exception e) {
            logger.error("Error occurred when finding the user by username", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public void delete(Long id) {
        try {
            User user = find(id);
            if (user != null) {
                entityManager.remove(user);
            }
        } catch (Exception e) {
            logger.error("Error occurred when deleting the user", e);
            throw new RuntimeException(e);
        }
    }

    public User update(User user) {
        try {
            return entityManager.merge(user);
        } catch (Exception e) {
            logger.error("Error occurred when updating the user", e);
            throw new RuntimeException(e);
        }
    }

    public boolean validateCredentials(String username, String password) {
        try {
            User user = findUserByUsername(username);
            if (user != null) {
                String hashedPassword = new Sha256Hash(password).toHex();
                return hashedPassword.equals(user.getPassword());
            }
            return false;
        } catch (Exception e) {
            logger.error("Error occurred when validating user credentials", e);
            throw new RuntimeException(e);
        }
    }
}
