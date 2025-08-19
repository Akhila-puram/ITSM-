package com.aaseya.ITSM.dao;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
 
import com.aaseya.ITSM.model.IncidentReport;
 
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
 
@Repository
public class IncidentReportDAO {
 
    private static final Logger logger = LoggerFactory.getLogger(IncidentReportDAO.class);
 
    @PersistenceContext
    private EntityManager entityManager;
 
    @Transactional
    public String save(IncidentReport incidentReport) { // Changed return type to String
        if (incidentReport == null) {
            logger.error("Attempted to save a null IncidentReport");
            throw new IllegalArgumentException("IncidentReport cannot be null");
        }
 
        try {
            entityManager.persist(incidentReport);
            logger.info("Incident report saved successfully: {}", incidentReport);
            
            return incidentReport.getIncidentCase() != null
                ? incidentReport.getIncidentCase().getCaseId()
                : null; // Handle accordingly
        } catch (PersistenceException e) {
            logger.error("Persistence error saving incident report: {}", e.getMessage(), e);
            throw e; // Handle specific exception if needed
        } catch (Exception e) {
            logger.error("Error saving incident report: {}", e.getMessage(), e);
            throw e; // Rethrow to trigger rollback
        }
    }
}