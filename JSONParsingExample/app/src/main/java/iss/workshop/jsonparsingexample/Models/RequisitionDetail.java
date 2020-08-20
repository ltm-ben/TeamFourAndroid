package iss.workshop.jsonparsingexample.Models;

public class RequisitionDetail {
    private int id;
    private Stationery stationery;
    private int qty;
    private int collectedQty;
    private DeptRequisition deptRequisition;

    public RequisitionDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stationery getStationery() {
        return stationery;
    }

    public void setStationery(Stationery stationery) {
        this.stationery = stationery;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getCollectedQty() {
        return collectedQty;
    }

    public void setCollectedQty(int collectedQty) {
        this.collectedQty = collectedQty;
    }

    public DeptRequisition getDeptRequisition() {
        return deptRequisition;
    }

    public void setDeptRequisition(DeptRequisition deptRequisition) {
        this.deptRequisition = deptRequisition;
    }

    @Override
    public String toString() {
        return "RequisitionDetail{" +
                "id=" + id +
                ", qty=" + qty +
                ", collectedQty=" + collectedQty +
                '}';
    }
}
