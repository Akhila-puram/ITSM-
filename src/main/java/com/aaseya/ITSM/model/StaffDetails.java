package com.aaseya.ITSM.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "StaffDetails")
public class StaffDetails {
	@Id
	@Column(name = "staffId")
	private String staffId;
	@Column(name = "staffName")
	private String staffName;
	@Column(name = "staffRole")
	private String staffRole;
	

    @ManyToMany(mappedBy = "staffDetails", cascade = { CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<IncidentCase> incidentCases;


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	public String getStaffRole() {
		return staffRole;
	}


	public void setStaffRole(String staffRole) {
		this.staffRole = staffRole;
	}


	public Set<IncidentCase> getIncidentCases() {
		return incidentCases;
	}


	public void setIncidentCases(Set<IncidentCase> incidentCases) {
		this.incidentCases = incidentCases;
	}


	@Override
	public String toString() {
		return "StaffDetails [staffId=" + staffId + ", staffName=" + staffName + ", staffRole=" + staffRole + "]";
	}

    
	
}
