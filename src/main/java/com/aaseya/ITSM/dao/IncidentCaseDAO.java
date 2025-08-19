package com.aaseya.ITSM.dao;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aaseya.ITSM.dto.IncidentCaseDTO;
import com.aaseya.ITSM.model.IncidentCase;
import com.aaseya.ITSM.model.PatientDetails;
import com.aaseya.ITSM.model.StaffDetails;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class IncidentCaseDAO {

	private static final Logger log = LoggerFactory.getLogger(IncidentCaseDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(IncidentCase incidentCase) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			session.save(incidentCase); // Save the incident case and its attachments due to cascading
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback(); // Rollback in case of an error
			}
			throw e; // Optionally, you can throw a custom exception here
		} finally {
			session.close();
		}
	}

	public StaffDetails findById(String staffId) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(StaffDetails.class, staffId);
		} finally {
			session.close();
		}
	}

	public PatientDetails findById1(String patientId) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(PatientDetails.class, patientId);
		} finally {
			session.close();
		}
	}

	//// Get the incident details by created by user(nurse/doctor)//
	public List<IncidentCase> findByCreatedBy(String createdBy) {
		Transaction transaction = null;
		List<IncidentCase> incidentCases = null;
		Session session = null;

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<IncidentCase> criteriaQuery = criteriaBuilder.createQuery(IncidentCase.class);
			Root<IncidentCase> root = criteriaQuery.from(IncidentCase.class);
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("createdBy"), createdBy));
			incidentCases = session.createQuery(criteriaQuery).getResultList();
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

		return incidentCases;
	}
	// Get the incident details by created by user(nurse/doctor)//

	// Add the attachement in incident case//
	public void saveIncidentCase(IncidentCase incidentCase) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			// Use merge instead of saveOrUpdate
			session.merge(incidentCase);

			// Commit the transaction
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error saving incident case", e);
		} finally {
			session.close();
		}
	}
	// Add the attachement in incident case//

	///// Submit the recommendation for reviewer///////

	// Method to merge detached PreventiveActions
//	public PreventiveActions mergePreventiveActions(PreventiveActions action) {
//		Session session = sessionFactory.openSession();
//		Transaction transaction = session.beginTransaction();
//		try {
//			PreventiveActions mergedAction = (PreventiveActions) session.merge(action); // Merge detached entity
//			transaction.commit();
//			return mergedAction;
//		} catch (Exception e) {
//			transaction.rollback();
//			throw new RuntimeException("Error while merging PreventiveActions: " + e.getMessage(), e);
//		} finally {
//			session.close();
//		}
//	}

	// Method to update case status
	public void updateCaseStatus(String caseId, String status) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			IncidentCase incidentCase = session.get(IncidentCase.class, caseId);
			if (incidentCase != null) {
				incidentCase.setStatus(status);
				session.update(incidentCase);
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new RuntimeException("Error while updating case status: " + e.getMessage(), e);
		} finally {
			session.close();
		}
	}

/////Submit the recommendation for reviewer///////

	public IncidentCaseDTO findIncidentCaseById(String caseId) {
		Transaction transaction = null;
		IncidentCase incidentCase = null;
		Session session = null;
		IncidentCaseDTO incidentCaseDTO = new IncidentCaseDTO();

		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			// Fetch the IncidentCase including its attachments
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<IncidentCase> criteriaQuery = criteriaBuilder.createQuery(IncidentCase.class);
			Root<IncidentCase> root = criteriaQuery.from(IncidentCase.class);
			// root.fetch("attachments"); // Eagerly fetch attachments
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("caseId"), caseId));

			TypedQuery<IncidentCase> query = session.createQuery(criteriaQuery);
			try {
				incidentCase = query.getSingleResult();

				incidentCaseDTO = convertCaseDetailsByCaseId(incidentCase);

			} catch (NoResultException e) {
				System.out.println("No incident case found with caseId: " + caseId);
			}

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

		return incidentCaseDTO;
	}

	private IncidentCaseDTO convertCaseDetailsByCaseId(IncidentCase incidentCase) {
		IncidentCaseDTO dto = new IncidentCaseDTO();
		dto.setCaseId(incidentCase.getCaseId());

		dto.setIncidentCategory(incidentCase.getIncidentCategory());
		dto.setIncidentSubCategory(incidentCase.getIncidentSubCategory());
		dto.setIncidentPriority(incidentCase.getIncidentPriority());
		dto.setCreationDate(incidentCase.getCreationDate());
		dto.setCreationTime(incidentCase.getCreationTime());
		//If blook/roonNo is saved into database in "A/1001" format, then use the below code
		if(incidentCase.getBlockRoomNo()!=null && incidentCase.getBlockRoomNo().toString().trim().length()>0) {
			if(incidentCase.getBlockRoomNo().toString().contains("/")) {
			dto.setBlock(incidentCase.getBlockRoomNo().split("/")[0]);
			dto.setRoomNo(incidentCase.getBlockRoomNo().split("/")[1]);
			}
		//If blook/roonNo is not saved into database in "A/1001" format, then use the below code
			else {
				dto.setBlockRoomNo(incidentCase.getBlockRoomNo().toString());
			}
		}
		// Upto here
		// dto.setCreatedBy(incidentCase.getCreatedBy());
		dto.setStatus(incidentCase.getStatus());
		dto.setIncidentManager(incidentCase.getIncidentManager());
		dto.setInvestigator(incidentCase.getInvestigator());
		dto.setIncidentDate(incidentCase.getIncidentDate());
		dto.setIncidentType(incidentCase.getIncidentType());
		dto.setIncidentTime(incidentCase.getIncidentTime());

		dto.setState(incidentCase.getState());
		dto.setCity(incidentCase.getCity());
		dto.setBranch(incidentCase.getBranch());
		dto.setDepartment(incidentCase.getDepartment());
		dto.setReportedBy(incidentCase.getReportedBy());
		dto.setIncidentDescription(incidentCase.getIncidentDescription());
		dto.setMedicationClass(incidentCase.getMedicationClass());
		dto.setBlockRoomNo(incidentCase.getBlockRoomNo());
		dto.setFloor(incidentCase.getFloor());
		dto.setInitialActionTaken(incidentCase.getInitialActionTaken());
		dto.setCorrectiveActions(incidentCase.getCorrectiveActions());
		dto.setAssesmentDetails(incidentCase.getAssessmentDetails());
		dto.setPatientDetailsDTO(incidentCase.getPatientDetails());

		// Convert attachments to Base64 strings
		// Check if there are attachments
		if (incidentCase.getAttachments() != null && !incidentCase.getAttachments().isEmpty()) {
			// Convert attachments to Base64 strings
			List<String> base64Attachments = incidentCase.getAttachments().stream()
					.map(attachment -> Base64.getEncoder().encodeToString(attachment.getAttachmentData()))
					.collect(Collectors.toList());
			dto.setAttachments(base64Attachments);
		} else {
			// If there are no attachments, ensure the attachments field is null or an empty
			// list
			dto.setAttachments(Collections.emptyList());

		}
		return dto;

	}

	public IncidentCase findIncidentById(String caseId) {
		Session session = sessionFactory.openSession();
		try {
			IncidentCase incidentCase = session.get(IncidentCase.class, caseId);
			if (incidentCase == null) {
				throw new RuntimeException("Incident Case not found for caseId: " + caseId);
			}
			return incidentCase;
		} finally {
			session.close();
		}
	}

	// Use saveOrUpdate for both saving and updating the IncidentCase
	public void saveOrUpdate(IncidentCase incidentCase) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(incidentCase);
			transaction.commit(); // Ensure commit is called
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	public List<IncidentCase> findByInvestigationTeamAndUnderOrPendingInvestigationStatus(String investigator) {
	    Session session = null;
	    List<IncidentCase> cases = null;
	    try {
	        session = sessionFactory.openSession();
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<IncidentCase> criteriaQuery = builder.createQuery(IncidentCase.class);
	        Root<IncidentCase> root = criteriaQuery.from(IncidentCase.class);

	        criteriaQuery.select(root).where(
	            builder.and(
	                builder.equal(root.get("investigator"), investigator),
	                builder.or(
	                    builder.equal(root.get("status"), "under_investigation"),
	                    builder.equal(root.get("status"), "pending_investigation")
	                )
	            )
	        );

	        cases = session.createQuery(criteriaQuery).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return cases;
	}



	public List<IncidentCase> findByReportedByAndNotCompletedOrClosedStatus(String reportedBy) {
	    Session session = null;
	    List<IncidentCase> cases = null;
	    try {
	        session = sessionFactory.openSession();
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	        CriteriaQuery<IncidentCase> criteriaQuery = builder.createQuery(IncidentCase.class);
	        Root<IncidentCase> root = criteriaQuery.from(IncidentCase.class);

	        criteriaQuery.select(root).where(
	            builder.and(
	                builder.equal(root.get("reportedBy"), reportedBy),
	                builder.notEqual(root.get("status"), "completed"),
	                builder.notEqual(root.get("status"), "closed")
	            )
	        );

	        cases = session.createQuery(criteriaQuery).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return cases;
	}


	public List<IncidentCase> findByIncidentManagerAndNotCompletedStatus(String incidentManager) {
		Session session = null;
		List<IncidentCase> cases = null;
		try {
			session = sessionFactory.openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<IncidentCase> criteriaQuery = builder.createQuery(IncidentCase.class);
			Root<IncidentCase> root = criteriaQuery.from(IncidentCase.class);

			criteriaQuery.select(root).where(builder.and(builder.equal(root.get("incidentManager"), incidentManager),
					builder.equal(root.get("status"), "pending_review")));

			cases = session.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return cases;
	}

	public IncidentCase findIncidentByIncidentBusinessKey(String incidentBusinessKey) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(IncidentCase.class, incidentBusinessKey);
		} finally {
			session.close();
		}
	}

	public void updateIncidentCase(IncidentCase incidentCase) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(incidentCase);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e; // Rethrow to let the service handle it
		} finally {
			session.close();
		}
	}
	
	// Method to retrieve IncidentCase by ID
		public IncidentCase getCaseById(String caseId) {
			Session session = sessionFactory.openSession();
			try {
				return session.get(IncidentCase.class, caseId);
			} finally {
				session.close();
			}
		}

		// New method to update status
	    public boolean updateStatus(String incidentCaseId, String status) {
	        Transaction transaction = null;
	        boolean isUpdated = false;

	        try (Session session = sessionFactory.openSession()) {
	            transaction = session.beginTransaction();
	            
	            // Fetch the incident case by id
	            IncidentCase incidentCase = session.get(IncidentCase.class, incidentCaseId);
	            if (incidentCase != null) {
	                // Update the status
	                incidentCase.setStatus(status);
	                session.update(incidentCase);
	                isUpdated = true;
	            }

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return isUpdated;
	    }
	    
	    
	    public IncidentCase generateIncidentCaseById(String id) {
	        Session session = sessionFactory.openSession();
	        Transaction transaction = null;
	        IncidentCase incidentCase = null;
 
	        try {
	            transaction = session.beginTransaction();
	            incidentCase = session.get(IncidentCase.class, id);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace(); // Consider using a logger here
	        } finally {
	            session.close();
	        }
 
	        return incidentCase;
	    }

}
