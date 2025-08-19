package com.aaseya.ITSM.model;
 
import jakarta.persistence.*;
import java.util.Arrays;
 
@Entity
@Table(name = "IncidentReport")
public class IncidentReport {
 
    @Id
    @Column(name = "reportId")
    private String reportId;  // Will be set dynamically
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caseId", referencedColumnName = "caseId")
    private IncidentCase incidentCase;  // Maps to IncidentCase
 
    @Lob
    @Column(name = "reportData")
    private byte[] reportData;  // Store PDF data
 
    public String getReportId() {
        return reportId;
    }
 
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
 
    public IncidentCase getIncidentCase() {
        return incidentCase;
    }
 
    public void setIncidentCase(IncidentCase incidentCase) {
        this.incidentCase = incidentCase;
    }
 
    public byte[] getReportData() {
        return reportData;
    }
 
    public void setReportData(byte[] reportData) {
        this.reportData = reportData;
    }
 
    @Override
    public String toString() {
        return "IncidentReport [reportId=" + reportId + ", incidentCase=" + incidentCase + ", reportData="
                + Arrays.toString(reportData) + "]";
    }
}