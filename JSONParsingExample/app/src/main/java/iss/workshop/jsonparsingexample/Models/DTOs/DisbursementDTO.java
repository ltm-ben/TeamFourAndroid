package iss.workshop.jsonparsingexample.Models.DTOs;

import java.util.ArrayList;
import java.util.List;

public class DisbursementDTO {

    private int Id;
    private DeptRequisitionDto DeptRequisition;
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
}
