package com.aaseya.ITSM.dto;

public class MedicationClassDTO {
    private Long medicationClassId;
    private String medicationClass;


	public MedicationClassDTO(Long medicationClassId, String medicationClass) {
        this.medicationClassId = medicationClassId;
        this.medicationClass = medicationClass;
    }

    public Long getmedicationClassId() {
        return medicationClassId;
    }

    public void setmedicationClassId(Long medicationClassId) {
        this.medicationClassId = medicationClassId;
    }

    public String getMedicationClass() {
        return medicationClass;
    }

    public void setMedicationClass(String medicationClass) {
        this.medicationClass = medicationClass;
    }
    
    @Override
	public String toString() {
		return "MedicationClassDTO [medicationClassId=" + medicationClassId + ", medicationClass=" + medicationClass
				+ "]";
	}
}
