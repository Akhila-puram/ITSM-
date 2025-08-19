package com.aaseya.ITSM.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Patient_Details")
public class PatientDetails {
	@Id
	@Column(name = "patientId")
	private String patientId;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private int age;
	@Column(name = "gender")
	private String gender;
	@Column(name = "contactNo")
	private String contactNo;
	@Column(name = "familyContactNo")
	private String familyContactNo;
	
	@OneToMany(mappedBy = "patientDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<IncidentCase> incidentCase;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFamilyContactNo() {
		return familyContactNo;
	}

	public void setFamilyContactNo(String familyContactNo) {
		this.familyContactNo = familyContactNo;
	}

	public List<IncidentCase> getIncidentCase() {
		return incidentCase;
	}

	public void setIncidentCase(List<IncidentCase> incidentCase) {
		this.incidentCase = incidentCase;
	}

	@Override
	public String toString() {
		return "PatientDetails [patientId=" + patientId + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", contactNo=" + contactNo + ", familyContactNo=" + familyContactNo + "]";
	}

    
}
