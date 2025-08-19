package com.aaseya.ITSM.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aaseya.ITSM.model.AssessmentDetails;

@Repository
public class AssessmentDetailsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveAssessmentDetails(AssessmentDetails assessmentDetails) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Persisting the assessment details object
            session.persist(assessmentDetails);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if error occurs
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Ensure session is closed
            }
        }
    }
}
