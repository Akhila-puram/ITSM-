package com.aaseya.ITSM.service;

import com.aaseya.ITSM.dao.StaffDetailsDAO;
import com.aaseya.ITSM.dto.StaffDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffDetailsService {

    private final StaffDetailsDAO staffDetailsDAO;

    @Autowired
    public StaffDetailsService(StaffDetailsDAO staffDetailsDAO) {
        this.staffDetailsDAO = staffDetailsDAO;
    }

    
    public List<StaffDetailsDTO> getStaffDetailsByName(String staffName) {
        return staffDetailsDAO.findByStaffName(staffName);
    }
}
