package com.aaseya.ITSM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaseya.ITSM.dao.MedicationClassDAO;
import com.aaseya.ITSM.dto.MedicationClassDTO;
import com.aaseya.ITSM.model.MedicationClass;

import jakarta.transaction.Transactional;

@Service
public class MedicationClassService {

    @Autowired
    private MedicationClassDAO medicationClassDAO;

    public List<MedicationClassDTO> getAllMedicationClasses() {
        List<MedicationClass> medicationClasses = medicationClassDAO.getMedicationClasses();
        return medicationClasses.stream()
                .map(mc -> new MedicationClassDTO(mc.getMedicationId(), mc.getMedicationClass()))
                .collect(Collectors.toList());
    }
}
