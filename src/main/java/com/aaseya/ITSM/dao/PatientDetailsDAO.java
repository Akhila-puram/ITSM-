	package com.aaseya.ITSM.dao;
	
	import java.util.List;
	
	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.Transaction;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Repository;
	
	import com.aaseya.ITSM.model.PatientDetails;
	
	import jakarta.persistence.criteria.CriteriaBuilder;
	import jakarta.persistence.criteria.CriteriaQuery;
	import jakarta.persistence.criteria.Root;
	import jakarta.transaction.Transactional;
	
	@Repository
	@Transactional
	public class PatientDetailsDAO {
	
	    private static final Logger logger = LoggerFactory.getLogger(PatientDetailsDAO.class);
	
	    @Autowired
	    private SessionFactory sessionFactory;
	
	    public void save(PatientDetails patientDetails) {
	        Transaction transaction = null;
	        Session session = null;
	        try {
	            session = sessionFactory.openSession();
	            transaction = session.beginTransaction();
	            session.save(patientDetails);
	            transaction.commit();
	            logger.info("Patient details saved successfully: " + patientDetails);
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            logger.error("Error saving patient details: " + e.getMessage(), e);
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	    }
	
	    public PatientDetails getPatientById(String patientId) {
	        Session session = null;
	        try {
	            session = sessionFactory.openSession();
	            return session.get(PatientDetails.class, patientId);
	        } catch (Exception e) {
	            logger.error("Error fetching patient details: " + e.getMessage(), e);
	            return null;
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	    }
	
	    public List<PatientDetails> getAllPatients() {
	        Session session = null;
	        try {
	            session = sessionFactory.openSession();
	            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
	            CriteriaQuery<PatientDetails> criteriaQuery = criteriaBuilder.createQuery(PatientDetails.class);
	            Root<PatientDetails> root = criteriaQuery.from(PatientDetails.class);
	            criteriaQuery.select(root);
	            return session.createQuery(criteriaQuery).getResultList();
	        } catch (Exception e) {
	            logger.error("Error fetching all patients: " + e.getMessage(), e);
	            return null;
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	    }
	
	    public void update(PatientDetails patientDetails) {
	        Transaction transaction = null;
	        Session session = null;
	        try {
	            session = sessionFactory.openSession();
	            transaction = session.beginTransaction();
	            session.update(patientDetails);
	            transaction.commit();
	            logger.info("Patient details updated successfully: " + patientDetails);
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            logger.error("Error updating patient details: " + e.getMessage(), e);
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	    }
	
	    public void delete(String patientId) {
	        Transaction transaction = null;
	        Session session = null;
	        try {
	            session = sessionFactory.openSession();
	            transaction = session.beginTransaction();
	            PatientDetails patientDetails = session.get(PatientDetails.class, patientId);
	            if (patientDetails != null) {
	                session.delete(patientDetails);
	                logger.info("Patient details deleted successfully: " + patientDetails);
	            }
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            logger.error("Error deleting patient details: " + e.getMessage(), e);
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	    }
	}