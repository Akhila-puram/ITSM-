package com.aaseya.ITSM.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aaseya.ITSM.model.IncidentCategory;
import com.aaseya.ITSM.model.IncidentSubCategory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SubCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	public List<IncidentSubCategory> getIncidentSubCategories(String incidentCategory) {
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<IncidentCategory> cr = cb.createQuery(IncidentCategory.class);
		Root<IncidentCategory> root = cr.from(IncidentCategory.class);
		cr.select(root).where(cb.equal(root.get("incidentCategory"), incidentCategory));
		IncidentCategory incidentCategory2  = session.createQuery(cr).getSingleResultOrNull();
		incidentCategory2.getIncidentSubCategories();
		session.close();
		return incidentCategory2.getIncidentSubCategories();
	}
}

	


