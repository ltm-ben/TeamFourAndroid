package iss.workshop.jsonparsingexample.Models;

public class RequisitionDetail {
    private int id;
    private int StationeryId;
    private String StationeryName;
    private int Qty;
    private int StockQty;
    private int CollectedQty;
    private int DisbursedQty;

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

    public int getCollectedQty() {
        return CollectedQty;
    }

    public void setCollectedQty(int collectedQty) {
        this.CollectedQty = collectedQty;
    }

    public int getDisbursedQty() {
        return DisbursedQty;
    }

    public void setDisbursedQty(int disbursedQty) {
        this.DisbursedQty = disbursedQty;
    }

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int stockQty) {
        StockQty = stockQty;
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
