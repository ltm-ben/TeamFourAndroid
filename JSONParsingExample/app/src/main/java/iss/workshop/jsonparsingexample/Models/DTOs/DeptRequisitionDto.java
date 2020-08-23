package iss.workshop.jsonparsingexample.Models.DTOs;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.RequisitionApprovalStatus;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;
import iss.workshop.jsonparsingexample.Models.RequisitionFulfillmentStatus;

public class DeptRequisitionDto {

    private int Id;
    private List<RequisitionDetail> requisitionDetails = new ArrayList<>();

    public DeptRequisitionDto() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<RequisitionDetail> getRequisitionDetails() {
        return requisitionDetails;
    }

    public void setRequisitionDetails(List<RequisitionDetail> requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }
}
