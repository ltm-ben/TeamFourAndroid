package iss.workshop.jsonparsingexample.Models;

public class RequisitionDetail {
    private int id;
    private int StationeryId;
    private String StationeryName;
    private int Qty;

    public RequisitionDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationeryId() {
        return StationeryId;
    }

    public void setStationeryId(int stationeryId) {
        StationeryId = stationeryId;
    }

    public String getStationeryName() {
        return StationeryName;
    }

    public void setStationeryName(String stationeryName) {
        StationeryName = stationeryName;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    @Override
    public String toString() {
        return "RequisitionDetail{" +
                "id=" + id +
                ", StationeryId=" + StationeryId +
                ", StationeryName='" + StationeryName + '\'' +
                ", Qty=" + Qty +
                '}';
    }
}
