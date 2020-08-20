package iss.workshop.jsonparsingexample.Models;

import java.util.ArrayList;
import java.util.List;

public class DeptRequisition {
    private int id;
    private Employee employee;
    private RequisitionApprovalStatus requisitionApprovalStatus;
    private RequisitionFulfillmentStatus requisitionFulfillmentStatus;
    private List<RequisitionDetail> requisitionDetails = new ArrayList<>();

    public DeptRequisition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public RequisitionApprovalStatus getRequisitionApprovalStatus() {
        return requisitionApprovalStatus;
    }

    public void setRequisitionApprovalStatus(RequisitionApprovalStatus requisitionApprovalStatus) {
        this.requisitionApprovalStatus = requisitionApprovalStatus;
    }

    public RequisitionFulfillmentStatus getRequisitionFulfillmentStatus() {
        return requisitionFulfillmentStatus;
    }

    public void setRequisitionFulfillmentStatus(RequisitionFulfillmentStatus requisitionFulfillmentStatus) {
        this.requisitionFulfillmentStatus = requisitionFulfillmentStatus;
    }

    public List<RequisitionDetail> getRequisitionDetails() {
        return requisitionDetails;
    }

    public void setRequisitionDetails(List<RequisitionDetail> requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }

    @Override
    public String toString() {
        return "DeptRequisition{" +
                "id=" + id +
                ", requisitionApprovalStatus=" + requisitionApprovalStatus +
                ", requisitionFulfillmentStatus=" + requisitionFulfillmentStatus +
                '}';
    }
}
