package com.aaseya.ITSM.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.aaseya.ITSM.model.MedicationClass;


@Repository
public class MedicationClassDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Fetch all MedicationClasses
    public List<MedicationClass> getMedicationClasses() {
        Session session = null;
        List<MedicationClass> medicationClasses = null;
        try {
            session = sessionFactory.openSession(); // Open session
            session.beginTransaction();

            // Create CriteriaBuilder and CriteriaQuery for MedicationClass
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<MedicationClass> query = builder.createQuery(MedicationClass.class);
            Root<MedicationClass> root = query.from(MedicationClass.class);
            query.select(root); // Select all

            // Execute query and get results
            medicationClasses = session.createQuery(query).getResultList();

            session.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback(); // Rollback in case of error
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Close session after execution
            }
        }
        return medicationClasses;
    }

   
}
