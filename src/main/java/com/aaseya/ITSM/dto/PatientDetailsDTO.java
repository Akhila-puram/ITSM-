package com.aaseya.ITSM.dto;


public class PatientDetailsDTO {

	private String patientId;
	private String name;
	private int age;
	private String gender;
	private String contactNo;
	private String familyContactNo;
	private String patientCondition;
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
	public String getPatientCondition() {
		return patientCondition;
	}
	public void setPatientCondition(String patientCondition) {
		this.patientCondition = patientCondition;
	}
	@Override
	public String toString() {
		return "PatientDetailsDTO [patientId=" + patientId + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", contactNo=" + contactNo + ", familyContactNo=" + familyContactNo + ", patientCondition="
				+ patientCondition + "]";
	}
	
	
}
	