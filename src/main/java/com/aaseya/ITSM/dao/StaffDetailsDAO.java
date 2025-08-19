package com.aaseya.ITSM.dao;

import com.aaseya.ITSM.dto.StaffDetailsDTO;
import com.aaseya.ITSM.model.StaffDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StaffDetailsDAO {

	@PersistenceContext
	private EntityManager entityManager;

	// Method to find all staff members by name using Criteria API
	public List<StaffDetailsDTO> findByStaffName(String staffName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StaffDetails> criteriaQuery = criteriaBuilder.createQuery(StaffDetails.class);

		Root<StaffDetails> staffRoot = criteriaQuery.from(StaffDetails.class);
		criteriaQuery.select(staffRoot).where(criteriaBuilder.equal(staffRoot.get("staffName"), staffName));

		List<StaffDetails> staffList = entityManager.createQuery(criteriaQuery).getResultList();
		entityManager.close();
		// Convert to DTO list
		return staffList.stream().map(staffDetails -> new StaffDetailsDTO(staffDetails.getStaffId(),
				staffDetails.getStaffName(), staffDetails.getStaffRole())).collect(Collectors.toList());
	}
}
