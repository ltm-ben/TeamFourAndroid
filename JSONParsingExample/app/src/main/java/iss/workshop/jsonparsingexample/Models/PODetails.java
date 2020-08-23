package iss.workshop.jsonparsingexample.Models;

import java.util.List;

public class PODetails {

    private int id;

    private int poId;

    private double unitPrice;

    private double predictionQty;

    private int Qty;

    private int stationaryId;

    private String stationaryDescription;


    private int supplierDetailsid;

    public void setId(int id) {
        this.id = id;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    public void setPredictionQty(double predictionQty) {
        this.predictionQty = predictionQty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public void setSupplierDetailsid(int supplierDetailsid) {
        this.supplierDetailsid = supplierDetailsid;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public double getPredictionQty() {
        return predictionQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getPoId() {
        return poId;
    }

    public int getQty() {
        return Qty;
    }

    public int getSupplierDetailsid() {
        return supplierDetailsid;
    }

    public void setStationaryDescription(String stationaryDescription) {
        this.stationaryDescription = stationaryDescription;
    }

    public void setStationaryId(int stationaryId) {
        this.stationaryId = stationaryId;
    }

    public String getStationaryDescription() {
        return stationaryDescription;
    }

    public int getStationaryId() {
        return stationaryId;
    }
}
