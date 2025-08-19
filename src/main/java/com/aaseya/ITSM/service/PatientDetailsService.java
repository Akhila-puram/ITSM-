package com.aaseya.ITSM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aaseya.ITSM.dao.PatientDetailsDAO;
import com.aaseya.ITSM.model.PatientDetails;

@Service
public class PatientDetailsService {

    @Autowired
    private PatientDetailsDAO patientDetailsDAO;

    /**
     * Save patient details to the database.
     *
     * @param patientDetails The patient details to be saved.
     */
    public void save(PatientDetails patientDetails) {
        patientDetailsDAO.save(patientDetails);
    }

    /**
     * Find patient details by patientId.
     *
     * @param patientId The ID of the patient to find.
     * @return PatientDetails if found, otherwise null.
     */
    public PatientDetails findById(String patientId) {
        return patientDetailsDAO.getPatientById(patientId);
    }
}
