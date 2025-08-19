package com.aaseya.ITSM.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aaseya.ITSM.model.IncidentCategory;
import com.aaseya.ITSM.model.IncidentType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

@Repository
public class IncidentTypeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<IncidentCategory> getIncidentCategoriesByIncidentType(String incidentType) {
        Transaction transaction = null;
        List<IncidentCategory> categories = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // Create CriteriaQuery
            CriteriaQuery<IncidentCategory> criteriaQuery = builder.createQuery(IncidentCategory.class);

            // Define the root for IncidentCategory
            Root<IncidentCategory> root = criteriaQuery.from(IncidentCategory.class);

            // Join with the IncidentType entity
            Join<IncidentCategory, IncidentType> incidentTypeJoin = root.join("incidentType");

            // Add criteria where incidentType.incidentType = :incidentType
            criteriaQuery.select(root).where(builder.equal(incidentTypeJoin.get("incidentType"), incidentType));

            // Execute the query
            categories = session.createQuery(criteriaQuery).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return categories;
    }
}