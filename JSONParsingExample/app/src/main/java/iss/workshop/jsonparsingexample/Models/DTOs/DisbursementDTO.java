package iss.workshop.jsonparsingexample.Models.DTOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DisbursementStatus;

public class DisbursementDTO {

    private int Id;
    private DeptRequisitionDto DeptRequisition;
    private String AcknowledgementCode;
    private DisbursementStatus DisbursementStatus;
    private List<DisbursementDetailDto> DisbursementDetails = new ArrayList<>();

    public DisbursementDTO() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public DeptRequisitionDto getDeptRequisition() {
        return DeptRequisition;
    }

    public void setDeptRequisition(DeptRequisitionDto deptRequisition) {
        DeptRequisition = deptRequisition;
    }

    public List<DisbursementDetailDto> getDisbursementDetails() {
        return DisbursementDetails;
    }

    public void setDisbursementDetails(List<DisbursementDetailDto> disbursementDetails) {
        DisbursementDetails = disbursementDetails;
    }

    public String getAcknowledgementCode() {
        return AcknowledgementCode;
    }

    public void setAcknowledgementCode(String acknowledgementCode) {
        AcknowledgementCode = acknowledgementCode;
    }

    public iss.workshop.jsonparsingexample.Models.DisbursementStatus getDisbursementStatus() {
        return DisbursementStatus;
    }

    public void setDisbursementStatus(iss.workshop.jsonparsingexample.Models.DisbursementStatus disbursementStatus) {
        DisbursementStatus = disbursementStatus;
    }
}
