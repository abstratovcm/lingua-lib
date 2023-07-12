package com.penguin.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penguin.model.Role;

import java.util.List;

@Stateless
public class RoleRepository {

    private static final Logger logger = LoggerFactory.getLogger(RoleRepository.class);

    @PersistenceContext(unitName = "LanguageStudyDS")
    private EntityManager entityManager;

    public Role save(Role role) {
        try {
            entityManager.persist(role);
            return role;
        } catch (Exception e) {
            logger.error("Error occurred when saving the role", e);
            throw new RuntimeException(e);
        }
    }

    public Role find(Long id) {
        try {
            return entityManager.find(Role.class, id);
        } catch (Exception e) {
            logger.error("Error occurred when finding the role", e);
            throw new RuntimeException(e);
        }
    }

    public Role findByName(String roleName) {
        try {
            CriteriaQuery<Role> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
            criteriaQuery.select(root).where(entityManager.getCriteriaBuilder().equal(root.get("role_name"), roleName));
            List<Role> roles = entityManager.createQuery(criteriaQuery).getResultList();
            if (!roles.isEmpty()) {
                return roles.get(0);
            }
        } catch (Exception e) {
            logger.error("Error occurred when finding the role by name", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public void delete(Long id) {
        try {
            Role role = find(id);
            if (role != null) {
                entityManager.remove(role);
            }
        } catch (Exception e) {
            logger.error("Error occurred when deleting the role", e);
            throw new RuntimeException(e);
        }
    }

    public Role update(Role role) {
        try {
            return entityManager.merge(role);
        } catch (Exception e) {
            logger.error("Error occurred when updating the role", e);
            throw new RuntimeException(e);
        }
    }

    public List<Role> findAll() {
        try {
            CriteriaQuery<Role> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Role.class);
            criteriaQuery.select(criteriaQuery.from(Role.class));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error occurred when finding all roles", e);
            throw new RuntimeException(e);
        }
    }
}
